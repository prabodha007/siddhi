package org.wso2.siddhi.core.gpu.query.input;

import org.apache.log4j.Logger;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.event.ComplexEventChunk;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.event.stream.StreamEvent;
import org.wso2.siddhi.core.event.stream.converter.ConversionStreamEventChunk;
import org.wso2.siddhi.core.gpu.event.stream.GpuEvent;
import org.wso2.siddhi.core.gpu.event.stream.GpuEventPool;
import org.wso2.siddhi.core.gpu.event.stream.GpuMetaStreamEvent;
import org.wso2.siddhi.core.gpu.event.stream.GpuMetaStreamEvent.GpuEventAttribute;
import org.wso2.siddhi.core.gpu.event.stream.converter.ConversionGpuEventChunk;
import org.wso2.siddhi.core.gpu.query.processor.GpuQueryProcessor;
import org.wso2.siddhi.core.gpu.util.ByteBufferWriter;
import org.wso2.siddhi.core.query.input.ProcessStreamReceiver;

public class GpuProcessStreamReceiver extends ProcessStreamReceiver {

    private static final Logger log = Logger.getLogger(GpuProcessStreamReceiver.class);
    private GpuQueryProcessor gpuQueryProcessor;
    private GpuMetaStreamEvent gpuMetaEvent;
    private ConversionGpuEventChunk gpuEventChunk;
    private int streamIndex;
    private ByteBufferWriter eventBufferWriter;
    private float currentEventCount;
    
    private long startTime = 0;
    private long endTime = 0;
    private long duration = 0;
    
    public GpuProcessStreamReceiver(String streamId) {
        super(streamId);
        this.gpuQueryProcessor = null;
        this.gpuMetaEvent = null;
        this.gpuEventChunk = null;
        this.eventBufferWriter = null;
        this.currentEventCount = 0;
    }

    public GpuProcessStreamReceiver clone(String key) {
        GpuProcessStreamReceiver clonedProcessStreamReceiver = new GpuProcessStreamReceiver(streamId + key);
        clonedProcessStreamReceiver.setMetaStreamEvent(metaStreamEvent);
        clonedProcessStreamReceiver.setGpuQueryProcessor(gpuQueryProcessor.clone());
        return clonedProcessStreamReceiver;
    }
    
    @Override
    public void receive(Event event, boolean endOfBatch) {
        
        log.debug("[receive] Event=" + event.toString() + " endOfBatch="+ endOfBatch);
        
        ComplexEvent.Type type = event.isExpired() ? StreamEvent.Type.EXPIRED : StreamEvent.Type.CURRENT;
        eventBufferWriter.writeShort((short)type.ordinal());
        eventBufferWriter.writeLong(gpuQueryProcessor.getNextSequenceNumber());
        eventBufferWriter.writeLong(event.getTimestamp());
        
        Object [] data = event.getData();
        
        int index = 0;
        for (GpuEventAttribute attrib : gpuMetaEvent.getAttributes()) {
            log.debug("[receive] writing attribute index=" + index + " attrib=" + attrib.toString() + " val=" + data[index] + 
                    " BufferIndex=" + eventBufferWriter.getBufferIndex() + " BufferPosition=" + eventBufferWriter.getBufferPosition());
            switch(attrib.type) {
            case BOOL:
                eventBufferWriter.writeBool(((Boolean) data[index++]).booleanValue());
                break;
            case INT:
                eventBufferWriter.writeInt(((Integer) data[index++]).intValue());
                break;
            case LONG:
                eventBufferWriter.writeLong(((Long) data[index++]).longValue());
                break;
            case FLOAT:
                eventBufferWriter.writeFloat(((Float) data[index++]).floatValue());
                break;
            case DOUBLE:
                eventBufferWriter.writeDouble(((Double) data[index++]).doubleValue());
                break;
            case STRING: 
                eventBufferWriter.writeString((String) data[index++], attrib.length);
                break;
            }
        }
        currentEventCount++;
        
        if (endOfBatch) {

            startTime = System.nanoTime();
            
            gpuQueryProcessor.process(streamIndex, (int)currentEventCount);
            
            endTime = System.nanoTime();
            
            duration = endTime - startTime;
            double average = (currentEventCount / (double)duration);
            log.debug("Batch Throughput : [" + currentEventCount + "] " + average + " epns");
            
            currentEventCount = 0;
        }
    }

    protected void processAndClearGpu(ComplexEventChunk<GpuEvent> streamEventChunk) {
        startTime = System.nanoTime();
        currentEventCount = streamEventChunk.getCurrentEventCount();
        if (stateProcessorsSize != 0) {
            stateProcessors.get(0).updateState();
        }
        gpuQueryProcessor.process(streamIndex, (int)currentEventCount);
        endTime = System.nanoTime();
        streamEventChunk.clear();
        
        duration = endTime - startTime;
        double average = (currentEventCount / (double)duration);
        log.debug("Batch Throughput : [" + currentEventCount + "] " + average + " epns");
    }
    
    public void init() {
        
        log.info("[GpuProcessStreamReceiver] Initializing " + streamId );
        
//        streamEventChunk = new ConversionStreamEventChunk(metaStreamEvent, streamEventPool);
        gpuEventChunk = new ConversionGpuEventChunk(metaStreamEvent, streamEventPool, gpuMetaEvent);
        
        gpuQueryProcessor.configure();
        streamIndex = gpuQueryProcessor.getStreamIndex(getStreamId());
        log.debug("[GpuProcessStreamReceiver] Set eventBufferWriter : StreamId=" + getStreamId() + " StreamIndex=" + streamIndex);
        eventBufferWriter = gpuQueryProcessor.getStreamInputEventBuffer(streamIndex);
        
        gpuQueryProcessor.setComplexEventChunk(streamIndex, gpuEventChunk);
    }

    public void setGpuQueryProcessor(GpuQueryProcessor gpuQueryProcessor) {
        this.gpuQueryProcessor = gpuQueryProcessor;
    }
    
    public GpuQueryProcessor getGpuQueryProcessor() {
        return gpuQueryProcessor;
    }

    public GpuMetaStreamEvent getGpuMetaEvent() {
        return gpuMetaEvent;
    }

    public void setGpuMetaEvent(GpuMetaStreamEvent gpuMetaEvent) {
        this.gpuMetaEvent = gpuMetaEvent;
    }
}
