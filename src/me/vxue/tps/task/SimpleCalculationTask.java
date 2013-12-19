package me.vxue.tps.task;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A very simple calculation task, for demo
 * @author Vincent Xue
 *
 */
public class SimpleCalculationTask extends AbstractTask {

	private static final long serialVersionUID = 3532497548137349607L;
	public static final String OPERATOR_KEY = "operator";
	public static final String FIRST_OPERAND_KEY = "operand_1";
	public static final String SECOND_OPERAND_KEY = "operand_2";
	public static final String RESULT_KEY = "result";
	
	private static final String DEFAULT_TASK_NAME = "SimpleCalculationTask";
	private static final String DEFAULT_PROCESSOR_NAME = "SimpleCalculationTaskProcessor";
	
	public enum OPERATOR {ADD, MINUS, MULTIPLY, DIVISION}
	
	private HashMap<String, Object> data = new LinkedHashMap<String, Object>();
	
	public SimpleCalculationTask() {
		super(DEFAULT_TASK_NAME, DEFAULT_PROCESSOR_NAME);
	}
	
	public SimpleCalculationTask(String name) {
		super(name, "SimpleCalculationTaskProcessor");
	}
	
	public SimpleCalculationTask(String name, String processorName) {
		super(name, processorName);
	}
	
	public SimpleCalculationTask(String name, String processorName, PRIORITY priority) {
		super(name, processorName, priority);
	}
	
	public double getOperandByKey(String key) {
		Object value = data.get(key);
		
		if (value instanceof Double) {
			return (Double)value;
		}
		
		// default value.
		return 0.0d;
	}
	
	public OPERATOR getOperatorByKey(String key) {
		Object operator = data.get(key);
		
		if (operator instanceof OPERATOR) {
			return (OPERATOR)operator;
		}
		
		// default value.
		return OPERATOR.ADD;
	}
	
	public void setCalculationResult(double result) {
		data.put(RESULT_KEY, new Double(result));
	}
	
	public void setOperandWithKey(String key, double value) {
		data.put(key, new Double(value));
	}
	
	public void SetOperantor(OPERATOR operantor) {
		data.put(OPERATOR_KEY, operantor);
	}
	
	private void descriptSelf() {
		getLogger().info("Finished task "  + toString() + "----- " 
							+ getOperandByKey(FIRST_OPERAND_KEY) + " "  
							+ getOperatorByKey(OPERATOR_KEY) + " "
							+ getOperandByKey(SECOND_OPERAND_KEY) + " is "
							+ getOperandByKey(RESULT_KEY));
							 
				
	}
	/* 
	 * The handle of task result.
	 * @see me.vxue.tps.task.AbstractTask#OnResult()
	 */
	@Override
	public void OnResult() {
		descriptSelf();
	}

}
