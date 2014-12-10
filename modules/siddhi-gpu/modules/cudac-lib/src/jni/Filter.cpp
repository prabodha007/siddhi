/*
 * FIlter.cpp
 *
 *  Created on: Oct 23, 2014
 *      Author: prabodha
 */

#include "Filter.h"
#include <stdlib.h>
#include <string.h>

namespace SiddhiGpu
{

VariableValue::VariableValue()
{
	e_Type = DataType::None;
	i_AttributePosition = -1;
}

VariableValue::VariableValue(DataType::Value _eType, int _iPos)
{
	e_Type = _eType;
	i_AttributePosition = _iPos;
}

void VariableValue::Print(FILE * _fp)
{
	fprintf(_fp, "(%s-POS-%d)", DataType::GetTypeName(e_Type), i_AttributePosition);
}

// ============================================================================================================

ConstValue::ConstValue()
{
	e_Type = DataType::None;
}

ConstValue & ConstValue::SetBool(bool _bVal)
{
	e_Type = DataType::Boolean;
	m_Value.b_BoolVal = _bVal;
	return *this;
}

ConstValue & ConstValue::SetInt(int _iVal)
{
	e_Type = DataType::Int;
	m_Value.i_IntVal = _iVal;
	return *this;
}

ConstValue & ConstValue::SetLong(int64_t _lVal)
{
	e_Type = DataType::Long;
	m_Value.l_LongVal = _lVal;
	return *this;
}

ConstValue & ConstValue::SetFloat(float _fval)
{
	e_Type = DataType::Float;
	m_Value.f_FloatVal = _fval;
	return *this;
}

ConstValue & ConstValue::SetDouble(double _dVal)
{
	e_Type = DataType::Double;
	m_Value.d_DoubleVal = _dVal;
	return *this;
}

ConstValue & ConstValue::SetString(const char * _zVal, int _iLen)
{
	if(_iLen > (int)sizeof(uint64_t))
	{
		e_Type = DataType::StringExt;
		m_Value.z_ExtString = new char[_iLen + 1];
		strncpy(m_Value.z_ExtString, _zVal, _iLen);
	}
	else
	{
		e_Type = DataType::StringIn;
		strncpy(m_Value.z_StringVal, _zVal, _iLen);
	}
	return *this;
}

void ConstValue::Print(FILE * _fp)
{
	fprintf(_fp, "(%s-", DataType::GetTypeName(e_Type));
	switch(e_Type)
	{
	case DataType::Int: //     = 0,
		fprintf(_fp, "%d)", m_Value.i_IntVal);
		break;
	case DataType::Long: //    = 1,
		fprintf(_fp, "%" PRIi64 ")", m_Value.l_LongVal);
		break;
	case DataType::Boolean: //    = 1,
		fprintf(_fp, "%d)", m_Value.b_BoolVal);
		break;
	case DataType::Float: //   = 2,
		fprintf(_fp, "%f)", m_Value.f_FloatVal);
		break;
	case DataType::Double://  = 3,
		fprintf(_fp, "%f)", m_Value.d_DoubleVal);
		break;
	case DataType::StringIn: //  = 4,
		fprintf(_fp, "%s)", m_Value.z_StringVal);
		break;
	case DataType::StringExt: //  = 4,
		fprintf(_fp, "%s)", m_Value.z_ExtString);
		break;
	case DataType::None: //    = 5
		fprintf(_fp, "NONE)");
		break;
	default:
		break;
	}
}

// ==========================================================================================================

ExecutorNode::ExecutorNode()
{
	e_NodeType = EXECUTOR_NODE_TYPE_COUNT;
	e_ConditionType = EXECUTOR_INVALID;
	e_ExpressionType = EXPRESSION_INVALID;
}

ExecutorNode & ExecutorNode::SetNodeType(ExecutorNodeType _eNodeType)
{
	e_NodeType = _eNodeType;
	return *this;
}

ExecutorNode & ExecutorNode::SetConditionType(ConditionType _eCondType)
{
	e_ConditionType = _eCondType;
	return *this;
}

ExecutorNode & ExecutorNode::SetExpressionType(ExpressionType _eExprType)
{
	e_ExpressionType = _eExprType;
	return *this;
}

ExecutorNode & ExecutorNode::SetConstValue(ConstValue _mConstVal)
{
	m_ConstValue.e_Type = _mConstVal.e_Type;
	m_ConstValue.m_Value = _mConstVal.m_Value;
	return *this;
}

ExecutorNode & ExecutorNode::SetVariableValue(VariableValue _mVarValue)
{
	m_VarValue.e_Type = _mVarValue.e_Type;
	m_VarValue.i_AttributePosition = _mVarValue.i_AttributePosition;
	return *this;
}

void ExecutorNode::Print(FILE * _fp)
{
	fprintf(_fp, "%s=", GetNodeTypeName(e_NodeType));
	switch(e_NodeType)
	{
	case EXECUTOR_NODE_CONDITION:
	{
		fprintf(_fp, "%s ", GetConditionName(e_ConditionType));
	}
	break;
	case EXECUTOR_NODE_EXPRESSION:
	{
		fprintf(_fp, "%s ", GetExpressionTypeName(e_ExpressionType));

		switch(e_ExpressionType)
		{
		case EXPRESSION_CONST:
		{
			m_ConstValue.Print(_fp);
		}
		break;
		case EXPRESSION_VARIABLE:
		{
			m_VarValue.Print(_fp);
		}
		break;
		default:
			break;
		}
	}
	break;
	default:
		break;
	}
}


// ==========================================================================================================

Filter::Filter(int _iFilterId, int _iNodeCount) :
	i_FilterId(_iFilterId),
	i_NodeCount(_iNodeCount)
{
	ap_ExecutorNodes = new ExecutorNode[i_NodeCount];
}

//Filter::~Filter()
//{
//	delete [] ap_ExecutorNodes;
//}

void Filter::Destroy()
{
	delete [] ap_ExecutorNodes;
}

void Filter::AddExecutorNode(int _iPos, ExecutorNode & _pNode)
{
	ap_ExecutorNodes[_iPos].e_NodeType = _pNode.e_NodeType;
	ap_ExecutorNodes[_iPos].e_ConditionType = _pNode.e_ConditionType;
	ap_ExecutorNodes[_iPos].e_ExpressionType = _pNode.e_ExpressionType;
	ap_ExecutorNodes[_iPos].m_ConstValue.e_Type = _pNode.m_ConstValue.e_Type;
	ap_ExecutorNodes[_iPos].m_ConstValue.m_Value = _pNode.m_ConstValue.m_Value;
	ap_ExecutorNodes[_iPos].m_VarValue.e_Type = _pNode.m_VarValue.e_Type;
	ap_ExecutorNodes[_iPos].m_VarValue.i_AttributePosition = _pNode.m_VarValue.i_AttributePosition;
}

Filter * Filter::Clone()
{
	Filter * f = new Filter(i_FilterId, i_NodeCount);

	memcpy(f->ap_ExecutorNodes, ap_ExecutorNodes, sizeof(ExecutorNode) * i_NodeCount);

	return f;
}

void Filter::Print(FILE * _fp)
{
	fprintf(_fp, "AddFilter : FilterId=%d NodeCount=%d {", i_FilterId, i_NodeCount);
	for(int i=0; i<i_NodeCount; ++i)
	{
		ap_ExecutorNodes[i].Print(_fp);
		fprintf(_fp, "|");
	}
	fprintf(_fp, "}\n");
	fflush(_fp);
}

};