/**
 * 
 */
package me.vxue.tps.process.processor;

import me.vxue.tps.task.AbstractTask;
import me.vxue.tps.task.SimpleCalculationTask;

/**
 * @author Vincetn Xue
 *
 */
public class SimpleCalculationProcessor extends AbstractProcessor {

	private static final String DEFAULT_PROCESSOR_NAME = "SimpleCalculationTaskProcessor";
	
	public SimpleCalculationProcessor() {
		super(DEFAULT_PROCESSOR_NAME);
	}
	
	public SimpleCalculationProcessor(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see me.vxue.tps.process.processor.AbstractProcessor#canProcess(me.vxue.tps.task.AbstractTask)
	 */
	@Override
	public boolean canProcess(AbstractTask task) {
		return task instanceof SimpleCalculationTask;
	}

	/* (non-Javadoc)
	 * @see me.vxue.tps.process.processor.AbstractProcessor#doProcess(me.vxue.tps.task.AbstractTask)
	 */
	@Override
	public void doProcess(AbstractTask task) {
		if (canProcess(task)) {
			SimpleCalculationTask simpleCalcTask = (SimpleCalculationTask)task;
			double operand1 = simpleCalcTask.getOperandByKey(SimpleCalculationTask.FIRST_OPERAND_KEY);
			double operand2 = simpleCalcTask.getOperandByKey(SimpleCalculationTask.SECOND_OPERAND_KEY);
			SimpleCalculationTask.OPERATOR op = simpleCalcTask.getOperatorByKey(SimpleCalculationTask.OPERATOR_KEY);
			
			double result = 0.0d;
			switch(op) {
			case ADD:
				result = operand1 + operand2;
				break;
			case MINUS:
				result = operand1 - operand2;
				break;
			case MULTIPLY:
				result = operand1 * operand2;
				break;
			case DIVISION:
				if (operand2 != 0.0) {
					result = operand1 + operand2;
				}
				break;
			default:
				getLogger().warn("Unknown operator type.");
				break;
			}
			
			simpleCalcTask.setCalculationResult(result);
			
		} else {
			getLogger().warn("Skipped unsupported task - ", task);
		}
	}

}
