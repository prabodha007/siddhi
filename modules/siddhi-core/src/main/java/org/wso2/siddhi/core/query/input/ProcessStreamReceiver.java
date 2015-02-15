/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org)
 * All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.siddhi.core.query.input;

import org.apache.log4j.Logger;
import org.wso2.siddhi.core.event.ComplexEvent;
import org.wso2.siddhi.core.event.ComplexEventChunk;
import org.wso2.siddhi.core.event.Event;
import org.wso2.siddhi.core.event.stream.MetaStreamEvent;
import org.wso2.siddhi.core.event.stream.StreamEvent;
import org.wso2.siddhi.core.event.stream.StreamEventPool;
import org.wso2.siddhi.core.event.stream.converter.ConversionStreamEventChunk;
import org.wso2.siddhi.core.query.input.stream.state.PreStateProcessor;
import org.wso2.siddhi.core.query.processor.Processor;
import org.wso2.siddhi.core.stream.StreamJunction;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProcessStreamReceiver implements StreamJunction.Receiver {

    private static final Logger log = Logger.getLogger(ProcessStreamReceiver.class);
    
    protected String streamId;
    protected String queryName;
    protected Processor next;
    protected MetaStreamEvent metaStreamEvent;
    protected ConversionStreamEventChunk streamEventChunk;
    protected StreamEventPool streamEventPool;
    protected List<PreStateProcessor> stateProcessors = new ArrayList<PreStateProcessor>();
    protected int stateProcessorsSize;
    
    private float currentEventCount = 0;
    private long iteration = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long duration = 0;
    private final List<Double> throughputList = new ArrayList<Double>();
    private final List<Long> durationList = new ArrayList<Long>();

    private final DecimalFormat decimalFormat = new DecimalFormat("###.##");

    public ProcessStreamReceiver(String streamId) {
        this.streamId = streamId;
        this.queryName = streamId;
    }
    
    public ProcessStreamReceiver(String streamId, String queryName) {
        this.streamId = streamId;
        this.queryName = queryName;
    }

    @Override
    public String getStreamId() {
        return streamId;
    }

    public ProcessStreamReceiver clone(String key) {
        ProcessStreamReceiver clonedProcessStreamReceiver = new ProcessStreamReceiver(streamId + key, queryName);
        clonedProcessStreamReceiver.setMetaStreamEvent(metaStreamEvent);
        clonedProcessStreamReceiver.setStreamEventPool(new StreamEventPool(metaStreamEvent, streamEventPool.getSize()));
        return clonedProcessStreamReceiver;
    }

    @Override
    public void receive(ComplexEvent complexEvent) {
        startTime = System.nanoTime();
        
        streamEventChunk.convertAndAssign(complexEvent);
        processAndClear(streamEventChunk);
        
        endTime = System.nanoTime();
        durationList.add(endTime - startTime);
        currentEventCount++;
        
        if(currentEventCount == 1000000) {
            long totalDuration = 0;
            
            for (Long tp : durationList) {
                totalDuration += tp;
            }
            
            double avgThroughput = currentEventCount * 1000000000 / totalDuration;
            log.info("<" + queryName + "> 1M Events Throughput : " + decimalFormat.format(avgThroughput) + " eps");
            durationList.clear();
            currentEventCount = 0;
        }
    }

    @Override
    public void receive(Event event) {
        startTime = System.nanoTime();
        
        streamEventChunk.convertAndAssign(event);
        processAndClear(streamEventChunk);
        
        endTime = System.nanoTime();
        durationList.add(endTime - startTime);
        currentEventCount++;
        
        if(currentEventCount == 1000000) {
            long totalDuration = 0;
            
            for (Long tp : durationList) {
                totalDuration += tp;
            }
            
            double avgThroughput = currentEventCount * 1000000000 / totalDuration;
            log.info("<" + queryName + "> 1M Events Throughput : " + decimalFormat.format(avgThroughput) + " eps");
            durationList.clear();
            currentEventCount = 0;
        }
    }

    @Override
    public void receive(Event[] events) {
        startTime = System.nanoTime();
        
        streamEventChunk.convertAndAssign(events);
        processAndClear(streamEventChunk);
        
        endTime = System.nanoTime();
        durationList.add(endTime - startTime);
        currentEventCount++;
        
        if(currentEventCount == 1000000) {
            long totalDuration = 0;
            
            for (Long tp : durationList) {
                totalDuration += tp;
            }
            
            double avgThroughput = currentEventCount * 1000000000 / totalDuration;
            log.info("<" + queryName + "> 1M Events Throughput : " + decimalFormat.format(avgThroughput) + " eps");
            durationList.clear();
            currentEventCount = 0;
        }
    }


    @Override
    public void receive(Event event, boolean endOfBatch) {
        streamEventChunk.convertAndAdd(event);
        currentEventCount++;
        if (endOfBatch) {
            startTime = System.nanoTime();
            
            processAndClear(streamEventChunk);
            
            endTime = System.nanoTime();
            
            duration = endTime - startTime;
            double average = (currentEventCount * 1000000000 / (double)duration);
            //log.info("<" + streamId + "> Batch Throughput : [" + currentEventCount + "/" + duration + "] " + decimalFormat.format(average) + " eps");
            throughputList.add(average);
            
            currentEventCount = 0;
            iteration++;
            
            if(iteration % 100000 == 0)
            {
                double totalThroughput = 0;
                
                for (Double tp : throughputList) {
                    totalThroughput += tp;
                }
                
                double avgThroughput = totalThroughput / throughputList.size();
                log.info("<" + queryName + "> Batch Throughput : " + decimalFormat.format(avgThroughput) + " eps");
                throughputList.clear();
            }
        }
    }

    @Override
    public void receive(long timeStamp, Object[] data) {
        startTime = System.nanoTime();
        
        streamEventChunk.convertAndAssign(timeStamp, data);
        processAndClear(streamEventChunk);
        
        endTime = System.nanoTime();
        durationList.add(endTime - startTime);
        currentEventCount++;
        
        if(currentEventCount == 1000000) {
            long totalDuration = 0;
            
            for (Long tp : durationList) {
                totalDuration += tp;
            }
            
            double avgThroughput = currentEventCount * 1000000000 / totalDuration;
            log.info("<" + queryName + "> 1M Events Throughput : " + decimalFormat.format(avgThroughput) + " eps");
            durationList.clear();
            currentEventCount = 0;
        }
    }
    
    protected void processAndClear(ComplexEventChunk<StreamEvent> streamEventChunk) {
        //System.out.println("ProcessStreamReceiver [" + this + " / " + Thread.currentThread().getName() + "] " + streamEventChunk.getCurrentEventCount());
        if (stateProcessorsSize != 0) {
            stateProcessors.get(0).updateState();
        }
        next.process(streamEventChunk);
        streamEventChunk.clear();
    }

    public void setMetaStreamEvent(MetaStreamEvent metaStreamEvent) {
        this.metaStreamEvent = metaStreamEvent;
    }

    public MetaStreamEvent getMetaStreamEvent() {
        return metaStreamEvent;
    }

    public void setNext(Processor next) {
        this.next = next;
    }

    public void setStreamEventPool(StreamEventPool streamEventPool) {
        this.streamEventPool = streamEventPool;
    }

    public void init() {
        streamEventChunk = new ConversionStreamEventChunk(metaStreamEvent, streamEventPool);
    }

    public void addStatefulProcessor(PreStateProcessor stateProcessor) {
        stateProcessors.add(stateProcessor);
        stateProcessorsSize = stateProcessors.size();
    }
}
