// Targeted by JavaCPP version 0.9

package org.wso2.siddhi.gpu.jni;

import java.nio.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.annotation.*;

public class SiddhiGpu extends org.wso2.siddhi.gpu.jni.presets.SiddhiGpu {
    static { Loader.load(); }

// Parsed from <CudaEvent.h>

/*
 * CudaEvent.h
 *
 *  Created on: Oct 23, 2014
 *      Author: prabodha
 */

// #ifndef CUDAEVENT_H_
// #define CUDAEVENT_H_

// #include <string.h>
// #include <string>
// #include <stdint.h>
// #include <stdio.h>
// #define __STDC_FORMAT_MACROS
// #include <inttypes.h>

@Namespace("SiddhiGpu") public static class DataType extends Pointer {
    static { Loader.load(); }
    public DataType() { allocate(); }
    public DataType(int size) { allocateArray(size); }
    public DataType(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public DataType position(int position) {
        return (DataType)super.position(position);
    }

	/** enum SiddhiGpu::DataType::Value */
	public static final int
		Int       = 0,
		Long      = 1,
		Boolean   = 2,
		Float     = 3,
		Double    = 4,
		StringIn  = 5,
		StringExt = 6,
		None      = 7;

	public static native @Cast("const char*") BytePointer GetTypeName(@Cast("SiddhiGpu::DataType::Value") int _eType);
}

@Namespace("SiddhiGpu") public static class Values extends Pointer {
    static { Loader.load(); }
    public Values() { allocate(); }
    public Values(int size) { allocateArray(size); }
    public Values(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public Values position(int position) {
        return (Values)super.position(position);
    }

	public native @Cast("bool") boolean b_BoolVal(); public native Values b_BoolVal(boolean b_BoolVal);
	public native int i_IntVal(); public native Values i_IntVal(int i_IntVal);
	public native long l_LongVal(); public native Values l_LongVal(long l_LongVal);
	public native float f_FloatVal(); public native Values f_FloatVal(float f_FloatVal);
	public native double d_DoubleVal(); public native Values d_DoubleVal(double d_DoubleVal);
	public native @Cast("char") byte z_StringVal(int i); public native Values z_StringVal(int i, byte z_StringVal);
	@MemberGetter public native @Cast("char*") BytePointer z_StringVal(); // set this if strlen < 8
	public native @Cast("char*") BytePointer z_ExtString(); public native Values z_ExtString(BytePointer z_ExtString); // set this if strlen > 8
}

@Namespace("SiddhiGpu") public static class AttibuteValue extends Pointer {
    static { Loader.load(); }
    public AttibuteValue() { allocate(); }
    public AttibuteValue(int size) { allocateArray(size); }
    public AttibuteValue(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public AttibuteValue position(int position) {
        return (AttibuteValue)super.position(position);
    }

	public native @ByRef Values m_Value(); public native AttibuteValue m_Value(Values m_Value);
	public native @Cast("SiddhiGpu::DataType::Value") int e_Type(); public native AttibuteValue e_Type(int e_Type);
}

@Namespace("SiddhiGpu") @NoOffset public static class CudaEvent extends Pointer {
    static { Loader.load(); }
    public CudaEvent(Pointer p) { super(p); }
    public CudaEvent(int size) { allocateArray(size); }
    private native void allocateArray(int size);
    @Override public CudaEvent position(int position) {
        return (CudaEvent)super.position(position);
    }

	/** enum SiddhiGpu::CudaEvent:: */
	public static final int MAX_ATTR_NUM = 10;

	public CudaEvent() { allocate(); }
	private native void allocate();
	public CudaEvent(@Cast("uint64_t") long _uiTimeStamp) { allocate(_uiTimeStamp); }
	private native void allocate(@Cast("uint64_t") long _uiTimeStamp);
	public native void Destroy();

	public native @Cast("uint64_t") long GetTimestamp();
	public native @Cast("unsigned int") int GetNumAttributes();

	public native void AddBoolAttribute(@Cast("unsigned int") int _iPos, @Cast("bool") boolean _bValue);
	public native void AddIntAttribute(@Cast("unsigned int") int _iPos, int _iValue);
	public native void AddLongAttribute(@Cast("unsigned int") int _iPos, long _lValue);
	public native void AddFloatAttribute(@Cast("unsigned int") int _iPos, float _fValue);
	public native void AddDoubleAttribute(@Cast("unsigned int") int _iPos, double _dValue);
	public native void AddStringAttribute(@Cast("unsigned int") int _iPos, @StdString BytePointer _sValue);
	public native void AddStringAttribute(@Cast("unsigned int") int _iPos, @StdString String _sValue);

	public native @Cast("bool") boolean GetBoolAttribute(@Cast("unsigned int") int _iPos);
	public native int GetIntAttribute(@Cast("unsigned int") int _iPos);
	public native long GetLongAttribute(@Cast("unsigned int") int _iPos);
	public native float GetFloatAttribute(@Cast("unsigned int") int _iPos);
	public native double GetDoubleAttribute(@Cast("unsigned int") int _iPos);
	public native @Cast("const char*") BytePointer GetStringAttribute(@Cast("unsigned int") int _iPos);

	public native void Print();
	public native void Print(@Cast("FILE*") Pointer _fp);

	public native @Cast("uint64_t") long ui_Timestamp(); public native CudaEvent ui_Timestamp(long ui_Timestamp);
	public native @Cast("unsigned int") int ui_NumAttributes(); public native CudaEvent ui_NumAttributes(int ui_NumAttributes);
	public native @ByRef AttibuteValue a_Attributes(int i); public native CudaEvent a_Attributes(int i, AttibuteValue a_Attributes);
	@MemberGetter public native AttibuteValue a_Attributes(); // index based values, use a enum schema to access
}



// #endif /* CUDAEVENT_H_ */


// Parsed from <Filter.h>

/*
 * FIlter.h
 *
 *  Created on: Oct 23, 2014
 *      Author: prabodha
 */

// #ifndef FILTER_H_
// #define FILTER_H_

// #include "CudaEvent.h"
// #include <stdio.h>
// #include <stdlib.h>
// #include <string>
// #define __STDC_FORMAT_MACROS
// #include <inttypes.h>

/** enum SiddhiGpu::ConditionType */
public static final int
	EXECUTOR_NOOP = 0,

	EXECUTOR_AND = 1,
	EXECUTOR_OR = 2,
	EXECUTOR_NOT = 3,
	EXECUTOR_BOOL = 4,

	EXECUTOR_EQ_BOOL_BOOL = 5,
	EXECUTOR_EQ_INT_INT = 6,
	EXECUTOR_EQ_INT_LONG = 7,
	EXECUTOR_EQ_INT_FLOAT = 8,
	EXECUTOR_EQ_INT_DOUBLE = 9,
	EXECUTOR_EQ_LONG_INT = 10,
	EXECUTOR_EQ_LONG_LONG = 11,
	EXECUTOR_EQ_LONG_FLOAT = 12,
	EXECUTOR_EQ_LONG_DOUBLE = 13,
	EXECUTOR_EQ_FLOAT_INT = 14,
	EXECUTOR_EQ_FLOAT_LONG = 15,
	EXECUTOR_EQ_FLOAT_FLOAT = 16,
	EXECUTOR_EQ_FLOAT_DOUBLE = 17,
	EXECUTOR_EQ_DOUBLE_INT = 18,
	EXECUTOR_EQ_DOUBLE_LONG = 19,
	EXECUTOR_EQ_DOUBLE_FLOAT = 20,
	EXECUTOR_EQ_DOUBLE_DOUBLE = 21,
	EXECUTOR_EQ_STRING_STRING = 22,

	EXECUTOR_NE_BOOL_BOOL = 23,
	EXECUTOR_NE_INT_INT = 24,
	EXECUTOR_NE_INT_LONG = 25,
	EXECUTOR_NE_INT_FLOAT = 26,
	EXECUTOR_NE_INT_DOUBLE = 27,
	EXECUTOR_NE_LONG_INT = 28,
	EXECUTOR_NE_LONG_LONG = 29,
	EXECUTOR_NE_LONG_FLOAT = 30,
	EXECUTOR_NE_LONG_DOUBLE = 31,
	EXECUTOR_NE_FLOAT_INT = 32,
	EXECUTOR_NE_FLOAT_LONG = 33,
	EXECUTOR_NE_FLOAT_FLOAT = 34,
	EXECUTOR_NE_FLOAT_DOUBLE = 35,
	EXECUTOR_NE_DOUBLE_INT = 36,
	EXECUTOR_NE_DOUBLE_LONG = 37,
	EXECUTOR_NE_DOUBLE_FLOAT = 38,
	EXECUTOR_NE_DOUBLE_DOUBLE = 39,
	EXECUTOR_NE_STRING_STRING = 40,

	EXECUTOR_GT_INT_INT = 41,
	EXECUTOR_GT_INT_LONG = 42,
	EXECUTOR_GT_INT_FLOAT = 43,
	EXECUTOR_GT_INT_DOUBLE = 44,
	EXECUTOR_GT_LONG_INT = 45,
	EXECUTOR_GT_LONG_LONG = 46,
	EXECUTOR_GT_LONG_FLOAT = 47,
	EXECUTOR_GT_LONG_DOUBLE = 48,
	EXECUTOR_GT_FLOAT_INT = 49,
	EXECUTOR_GT_FLOAT_LONG = 50,
	EXECUTOR_GT_FLOAT_FLOAT = 51,
	EXECUTOR_GT_FLOAT_DOUBLE = 52,
	EXECUTOR_GT_DOUBLE_INT = 53,
	EXECUTOR_GT_DOUBLE_LONG = 54,
	EXECUTOR_GT_DOUBLE_FLOAT = 55,
	EXECUTOR_GT_DOUBLE_DOUBLE = 56,

	EXECUTOR_LT_INT_INT = 57,
	EXECUTOR_LT_INT_LONG = 58,
	EXECUTOR_LT_INT_FLOAT = 59,
	EXECUTOR_LT_INT_DOUBLE = 60,
	EXECUTOR_LT_LONG_INT = 61,
	EXECUTOR_LT_LONG_LONG = 62,
	EXECUTOR_LT_LONG_FLOAT = 63,
	EXECUTOR_LT_LONG_DOUBLE = 64,
	EXECUTOR_LT_FLOAT_INT = 65,
	EXECUTOR_LT_FLOAT_LONG = 66,
	EXECUTOR_LT_FLOAT_FLOAT = 67,
	EXECUTOR_LT_FLOAT_DOUBLE = 68,
	EXECUTOR_LT_DOUBLE_INT = 69,
	EXECUTOR_LT_DOUBLE_LONG = 70,
	EXECUTOR_LT_DOUBLE_FLOAT = 71,
	EXECUTOR_LT_DOUBLE_DOUBLE = 72,

	EXECUTOR_GE_INT_INT = 73,
	EXECUTOR_GE_INT_LONG = 74,
	EXECUTOR_GE_INT_FLOAT = 75,
	EXECUTOR_GE_INT_DOUBLE = 76,
	EXECUTOR_GE_LONG_INT = 77,
	EXECUTOR_GE_LONG_LONG = 78,
	EXECUTOR_GE_LONG_FLOAT = 79,
	EXECUTOR_GE_LONG_DOUBLE = 80,
	EXECUTOR_GE_FLOAT_INT = 81,
	EXECUTOR_GE_FLOAT_LONG = 82,
	EXECUTOR_GE_FLOAT_FLOAT = 83,
	EXECUTOR_GE_FLOAT_DOUBLE = 84,
	EXECUTOR_GE_DOUBLE_INT = 85,
	EXECUTOR_GE_DOUBLE_LONG = 86,
	EXECUTOR_GE_DOUBLE_FLOAT = 87,
	EXECUTOR_GE_DOUBLE_DOUBLE = 88,

	EXECUTOR_LE_INT_INT = 89,
	EXECUTOR_LE_INT_LONG = 90,
	EXECUTOR_LE_INT_FLOAT = 91,
	EXECUTOR_LE_INT_DOUBLE = 92,
	EXECUTOR_LE_LONG_INT = 93,
	EXECUTOR_LE_LONG_LONG = 94,
	EXECUTOR_LE_LONG_FLOAT = 95,
	EXECUTOR_LE_LONG_DOUBLE = 96,
	EXECUTOR_LE_FLOAT_INT = 97,
	EXECUTOR_LE_FLOAT_LONG = 98,
	EXECUTOR_LE_FLOAT_FLOAT = 99,
	EXECUTOR_LE_FLOAT_DOUBLE = 100,
	EXECUTOR_LE_DOUBLE_INT = 101,
	EXECUTOR_LE_DOUBLE_LONG = 102,
	EXECUTOR_LE_DOUBLE_FLOAT = 103,
	EXECUTOR_LE_DOUBLE_DOUBLE = 104,

	EXECUTOR_CONTAINS = 105,

	EXECUTOR_INVALID = 106, // set this for const and var nodes

	EXECUTOR_CONDITION_COUNT = 107;

@Namespace("SiddhiGpu") public static native @Cast("const char*") BytePointer GetConditionName(@Cast("SiddhiGpu::ConditionType") int _eType);

/** enum SiddhiGpu::ExpressionType */
public static final int
	EXPRESSION_CONST = 0,
	EXPRESSION_VARIABLE = 1,

	EXPRESSION_ADD_INT = 2,
	EXPRESSION_ADD_LONG = 3,
	EXPRESSION_ADD_FLOAT = 4,
	EXPRESSION_ADD_DOUBLE = 5,

	EXPRESSION_SUB_INT = 6,
	EXPRESSION_SUB_LONG = 7,
	EXPRESSION_SUB_FLOAT = 8,
	EXPRESSION_SUB_DOUBLE = 9,

	EXPRESSION_MUL_INT = 10,
	EXPRESSION_MUL_LONG = 11,
	EXPRESSION_MUL_FLOAT = 12,
	EXPRESSION_MUL_DOUBLE = 13,

	EXPRESSION_DIV_INT = 14,
	EXPRESSION_DIV_LONG = 15,
	EXPRESSION_DIV_FLOAT = 16,
	EXPRESSION_DIV_DOUBLE = 17,

	EXPRESSION_MOD_INT = 18,
	EXPRESSION_MOD_LONG = 19,
	EXPRESSION_MOD_FLOAT = 20,
	EXPRESSION_MOD_DOUBLE = 21,

	EXPRESSION_INVALID = 22,
	EXPRESSION_COUNT = 23;

@Namespace("SiddhiGpu") public static native @Cast("const char*") BytePointer GetExpressionTypeName(@Cast("SiddhiGpu::ExpressionType") int _eType);

/** enum SiddhiGpu::ExecutorNodeType */
public static final int
	EXECUTOR_NODE_CONDITION = 0,
	EXECUTOR_NODE_EXPRESSION = 1,

	EXECUTOR_NODE_TYPE_COUNT = 2;

@Namespace("SiddhiGpu") public static native @Cast("const char*") BytePointer GetNodeTypeName(@Cast("SiddhiGpu::ExecutorNodeType") int _eType);

@Namespace("SiddhiGpu") @NoOffset public static class VariableValue extends Pointer {
    static { Loader.load(); }
    public VariableValue(Pointer p) { super(p); }
    public VariableValue(int size) { allocateArray(size); }
    private native void allocateArray(int size);
    @Override public VariableValue position(int position) {
        return (VariableValue)super.position(position);
    }

	public native @Cast("SiddhiGpu::DataType::Value") int e_Type(); public native VariableValue e_Type(int e_Type);
	public native int i_AttributePosition(); public native VariableValue i_AttributePosition(int i_AttributePosition);

	public VariableValue() { allocate(); }
	private native void allocate();
	public VariableValue(@Cast("SiddhiGpu::DataType::Value") int _eType, int _iPos) { allocate(_eType, _iPos); }
	private native void allocate(@Cast("SiddhiGpu::DataType::Value") int _eType, int _iPos);

	public native void Print(@Cast("FILE*") Pointer _fp/*=stdout*/);
	public native void Print();
}

@Namespace("SiddhiGpu") @NoOffset public static class ConstValue extends Pointer {
    static { Loader.load(); }
    public ConstValue(Pointer p) { super(p); }
    public ConstValue(int size) { allocateArray(size); }
    private native void allocateArray(int size);
    @Override public ConstValue position(int position) {
        return (ConstValue)super.position(position);
    }

	public native @Cast("SiddhiGpu::DataType::Value") int e_Type(); public native ConstValue e_Type(int e_Type);
	public native @ByRef Values m_Value(); public native ConstValue m_Value(Values m_Value);

	public ConstValue() { allocate(); }
	private native void allocate();

	public native @ByRef ConstValue SetBool(@Cast("bool") boolean _bVal);
	public native @ByRef ConstValue SetInt(int _iVal);
	public native @ByRef ConstValue SetLong(long _lVal);
	public native @ByRef ConstValue SetFloat(float _fval);
	public native @ByRef ConstValue SetDouble(double _dVal);
	public native @ByRef ConstValue SetString(@Cast("const char*") BytePointer _zVal, int _iLen);
	public native @ByRef ConstValue SetString(String _zVal, int _iLen);

	public native void Print(@Cast("FILE*") Pointer _fp/*=stdout*/);
	public native void Print();
}

@Namespace("SiddhiGpu") @NoOffset public static class ExecutorNode extends Pointer {
    static { Loader.load(); }
    public ExecutorNode(Pointer p) { super(p); }
    public ExecutorNode(int size) { allocateArray(size); }
    private native void allocateArray(int size);
    @Override public ExecutorNode position(int position) {
        return (ExecutorNode)super.position(position);
    }

	public native @Cast("SiddhiGpu::ExecutorNodeType") int e_NodeType(); public native ExecutorNode e_NodeType(int e_NodeType);

	// if operator - what is the type
	public native @Cast("SiddhiGpu::ConditionType") int e_ConditionType(); public native ExecutorNode e_ConditionType(int e_ConditionType);

	// if expression
	public native @Cast("SiddhiGpu::ExpressionType") int e_ExpressionType(); public native ExecutorNode e_ExpressionType(int e_ExpressionType);

	// if const - what is the value
	public native @ByRef ConstValue m_ConstValue(); public native ExecutorNode m_ConstValue(ConstValue m_ConstValue);

	// if var - variable holder
	public native @ByRef VariableValue m_VarValue(); public native ExecutorNode m_VarValue(VariableValue m_VarValue);

	public ExecutorNode() { allocate(); }
	private native void allocate();

	public native @ByRef ExecutorNode SetNodeType(@Cast("SiddhiGpu::ExecutorNodeType") int _eNodeType);
	public native @ByRef ExecutorNode SetConditionType(@Cast("SiddhiGpu::ConditionType") int _eCondType);
	public native @ByRef ExecutorNode SetExpressionType(@Cast("SiddhiGpu::ExpressionType") int _eExprType);
	public native @ByRef ExecutorNode SetConstValue(@ByVal ConstValue _mConstVal);
	public native @ByRef ExecutorNode SetVariableValue(@ByVal VariableValue _mVarValue);

	public native void Print();
	public native void Print(@Cast("FILE*") Pointer _fp);
}

@Namespace("SiddhiGpu") @NoOffset public static class Filter extends Pointer {
    static { Loader.load(); }
    public Filter() { }
    public Filter(Pointer p) { super(p); }

	public Filter(int _iFilterId, int _iNodeCount) { allocate(_iFilterId, _iNodeCount); }
	private native void allocate(int _iFilterId, int _iNodeCount);
//	~Filter();

	public native void AddExecutorNode(int _iPos, @ByRef ExecutorNode _pNode);

	public native Filter Clone();
	public native void Destroy();

	public native void Print();
	public native void Print(@Cast("FILE*") Pointer _fp);

	public native int i_FilterId(); public native Filter i_FilterId(int i_FilterId);
	public native ExecutorNode ap_ExecutorNodes(); public native Filter ap_ExecutorNodes(ExecutorNode ap_ExecutorNodes); // nodes are stored in in-order
	public native int i_NodeCount(); public native Filter i_NodeCount(int i_NodeCount);

}



// #endif /* FILTER_H_ */


// Parsed from <GpuEventConsumer.h>

/*
 * GpuEventConsumer.h
 *
 *  Created on: Oct 23, 2014
 *      Author: prabodha
 */

// #ifndef GPUEVENTCONSUMER_H_
// #define GPUEVENTCONSUMER_H_

// #include <stdlib.h>
// #include <stdio.h>
// #include <string>
// #include <map>
// #include <vector>
// #include "Timer.h"
// #include "CudaKernelBase.h"
// #include "CudaFilterKernel.h"
// #include "CudaSingleFilterKernel.h"

/** enum SiddhiGpu::KernelType */
public static final int
	SingleFilterKernel = 0,
	MultiFilterKernel = 1;

@Namespace("SiddhiGpu") @NoOffset public static class GpuEventConsumer extends Pointer {
    static { Loader.load(); }
    public GpuEventConsumer() { }
    public GpuEventConsumer(Pointer p) { super(p); }

	public GpuEventConsumer(@Cast("SiddhiGpu::KernelType") int _eKernelType, int _iMaxBufferSize, int _iEventsPerBlock) { allocate(_eKernelType, _iMaxBufferSize, _iEventsPerBlock); }
	private native void allocate(@Cast("SiddhiGpu::KernelType") int _eKernelType, int _iMaxBufferSize, int _iEventsPerBlock);

	public native void Initialize();
	public native void OnEvents(@Cast("SiddhiGpu::CudaEvent**") PointerPointer _apEvents, int _iEventCount);
	public native void OnEvents(@ByPtrPtr CudaEvent _apEvents, int _iEventCount);

	public native void AddFilter(Filter _pFilter);
	public native void ConfigureFilters();

	public native void OnCudaEventMatch(int _iEventPos, int _iFilterId);
	public native void OnEventMatch(CudaEvent _pEvent, int _iFilterId);

	public native @StdVector IntPointer GetMatchingEvents();

	public native void PrintAverageStats();

	public native int GetMaxBufferSize();
}



// #endif /* GPUEVENTCONSUMER_H_ */


// Parsed from <CudaKernelBase.h>

/*
 * CudaKernelBase.h
 *
 *  Created on: Nov 10, 2014
 *      Author: prabodha
 */

// #ifndef CUDAKERNELBASE_H_
// #define CUDAKERNELBASE_H_

// #include <stdlib.h>
// #include <stdio.h>
// #include <string.h>
// #include "CudaEvent.h"
// #include "Filter.h"

@Namespace("SiddhiGpu") @NoOffset public static class CudaKernelBase extends Pointer {
    static { Loader.load(); }
    public CudaKernelBase() { }
    public CudaKernelBase(Pointer p) { super(p); }


	public native void Initialize();

	public native void ProcessEvents();

	public native void AddEvent(@Const CudaEvent _pEvent);
	public native void AddAndProcessEvents(@Cast("SiddhiGpu::CudaEvent**") PointerPointer _apEvent, int _iEventCount);
	public native void AddAndProcessEvents(@ByPtrPtr CudaEvent _apEvent, int _iEventCount);

	public native void AddFilterToDevice(Filter _pFilter);
	public native void CopyFiltersToDevice();

	public native float GetElapsedTimeAverage();
}



// #endif /* CUDAKERNELBASE_H_ */


// Parsed from <CudaFilterKernel.h>

/*
 * CudaFilterKernel.h
 *
 *  Created on: Oct 18, 2014
 *      Author: prabodha
 */

// #ifndef CUDAFILTERKERNEL_H_
// #define CUDAFILTERKERNEL_H_

// #include <stdlib.h>
// #include <stdio.h>
// #include <string.h>
// #include <list>
// #include "CudaEvent.h"
// #include "Filter.h"
// #include "CudaKernelBase.h"

// #define __STDC_FORMAT_MACROS
// #include <inttypes.h>

@Opaque public static class StopWatchInterface extends Pointer {
    public StopWatchInterface() { }
    public StopWatchInterface(Pointer p) { super(p); }
}

// -------------------------------------------------------------------------------------------------------------------

@Namespace("SiddhiGpu") public static class MultipleFilterKernelInput extends Pointer {
    static { Loader.load(); }
    public MultipleFilterKernelInput() { allocate(); }
    public MultipleFilterKernelInput(int size) { allocateArray(size); }
    public MultipleFilterKernelInput(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public MultipleFilterKernelInput position(int position) {
        return (MultipleFilterKernelInput)super.position(position);
    }

	public native CudaEvent ap_EventBuffer(); public native MultipleFilterKernelInput ap_EventBuffer(CudaEvent ap_EventBuffer);       // input events in device memory
	public native int i_EventCount(); public native MultipleFilterKernelInput i_EventCount(int i_EventCount);         // number of events in buffer
	public native Filter ap_Filters(); public native MultipleFilterKernelInput ap_Filters(Filter ap_Filters);           // Filters buffer - pre-copied at initialization
	public native int i_FilterCount(); public native MultipleFilterKernelInput i_FilterCount(int i_FilterCount);        // number of filters in buffer
	public native int i_MaxEventCount(); public native MultipleFilterKernelInput i_MaxEventCount(int i_MaxEventCount);      // used for setting results array
}

// -------------------------------------------------------------------------------------------------------------------

@Namespace("SiddhiGpu") @NoOffset public static class CudaFilterKernel extends CudaKernelBase {
    static { Loader.load(); }
    public CudaFilterKernel() { }
    public CudaFilterKernel(Pointer p) { super(p); }

	public CudaFilterKernel(int _iMaxBufferSize, GpuEventConsumer _pConsumer, @Cast("FILE*") Pointer _fpLog) { allocate(_iMaxBufferSize, _pConsumer, _fpLog); }
	private native void allocate(int _iMaxBufferSize, GpuEventConsumer _pConsumer, @Cast("FILE*") Pointer _fpLog);

	public native void Initialize();

	public native void ProcessEvents();

	public native void AddEvent(@Const CudaEvent _pEvent);
	public native void AddAndProcessEvents(@Cast("SiddhiGpu::CudaEvent**") PointerPointer _apEvent, int _iEventCount);
	public native void AddAndProcessEvents(@ByPtrPtr CudaEvent _apEvent, int _iEventCount);

	public native void AddFilterToDevice(Filter _pFilter);
	public native void CopyFiltersToDevice();

	public native float GetElapsedTimeAverage();

	public static native void OnExit();
}



// #endif /* CUDAFILTERKERNEL_H_ */


// Parsed from <CudaSingleFilterKernel.h>

/*
 * CudaSingleFilterKernel.h
 *
 *  Created on: Nov 9, 2014
 *      Author: prabodha
 */

// #ifndef CUDASINGLEFILTERKERNEL_H_
// #define CUDASINGLEFILTERKERNEL_H_

// #include "CudaKernelBase.h"

// -------------------------------------------------------------------------------------------------------------------

@Namespace("SiddhiGpu") public static class SingleFilterKernelInput extends Pointer {
    static { Loader.load(); }
    public SingleFilterKernelInput() { allocate(); }
    public SingleFilterKernelInput(int size) { allocateArray(size); }
    public SingleFilterKernelInput(Pointer p) { super(p); }
    private native void allocate();
    private native void allocateArray(int size);
    @Override public SingleFilterKernelInput position(int position) {
        return (SingleFilterKernelInput)super.position(position);
    }

	public native CudaEvent ap_EventBuffer(); public native SingleFilterKernelInput ap_EventBuffer(CudaEvent ap_EventBuffer);       // input events in device memory
	public native int i_EventCount(); public native SingleFilterKernelInput i_EventCount(int i_EventCount);         // number of events in buffer
	public native Filter ap_Filter(); public native SingleFilterKernelInput ap_Filter(Filter ap_Filter);            // Filters buffer - pre-copied at initialization
	public native int i_EventsPerBlock(); public native SingleFilterKernelInput i_EventsPerBlock(int i_EventsPerBlock);     // number of events allocated per block
	public native int i_MaxEventCount(); public native SingleFilterKernelInput i_MaxEventCount(int i_MaxEventCount);      // used for setting results array
}

// -------------------------------------------------------------------------------------------------------------------

@Namespace("SiddhiGpu") @NoOffset public static class CudaSingleFilterKernel extends CudaKernelBase {
    static { Loader.load(); }
    public CudaSingleFilterKernel() { }
    public CudaSingleFilterKernel(Pointer p) { super(p); }

	public CudaSingleFilterKernel(int _iMaxBufferSize, GpuEventConsumer _pConsumer, @Cast("FILE*") Pointer _fpLog) { allocate(_iMaxBufferSize, _pConsumer, _fpLog); }
	private native void allocate(int _iMaxBufferSize, GpuEventConsumer _pConsumer, @Cast("FILE*") Pointer _fpLog);
	public CudaSingleFilterKernel(int _iMaxBufferSize, int _iEventsPerBlock, GpuEventConsumer _pConsumer, @Cast("FILE*") Pointer _fpLog) { allocate(_iMaxBufferSize, _iEventsPerBlock, _pConsumer, _fpLog); }
	private native void allocate(int _iMaxBufferSize, int _iEventsPerBlock, GpuEventConsumer _pConsumer, @Cast("FILE*") Pointer _fpLog);

	public native void Initialize();
	public native void ProcessEvents();

	public native void AddEvent(@Const CudaEvent _pEvent);
	public native void AddAndProcessEvents(@Cast("SiddhiGpu::CudaEvent**") PointerPointer _apEvent, int _iEventCount);
	public native void AddAndProcessEvents(@ByPtrPtr CudaEvent _apEvent, int _iEventCount);

	public native void AddFilterToDevice(Filter _pFilter);
	public native void CopyFiltersToDevice();

	public native float GetElapsedTimeAverage();
}



// #endif /* CUDASINGLEFILTERKERNEL_H_ */


}