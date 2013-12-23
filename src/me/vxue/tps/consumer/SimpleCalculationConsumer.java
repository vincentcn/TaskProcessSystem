/**
 * 
 */
package me.vxue.tps.consumer;

import java.util.Map;
import java.util.Properties;
import java.util.Random;

import me.vxue.tps.process.TaskProcessManager;
import me.vxue.tps.process.processor.SimpleCalculationProcessor;
import me.vxue.tps.task.SimpleCalculationTask;
import me.vxue.tps.task.TaskPriority;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Vincent Xue
 *
 */
public class SimpleCalculationConsumer {
	private static Logger logger = LogManager.getLogger(SimpleCalculationConsumer.class);
	private static final int CONSUM_TASK_COUNT = 10;
	
	private final String processorName = "SimpleCalculationProcessor";
	private TaskProcessManager manager = new TaskProcessManager();
	private SimpleCalculationProcessor processor = new SimpleCalculationProcessor(processorName);
	
	
	private void logEnvironment()
	{
		Map<String, String> env = System.getenv();
		
		logProperties("System Environment Setting", env);
		
		Properties props = System.getProperties();
		
		logProperties("System Properties", props);
	}
	
	private <S extends Object, T extends Object> void logProperties(String subject, Map<S, T> properties)
	{
		logger.info("==================================================================================");
		logger.info("==============================" + subject + "=================================");
		
		for (Map.Entry<S, T> entry : properties.entrySet())
		{
			logger.info(entry.getKey() + " = " + entry.getValue());
		}
		
		logger.info("============================== end of " + subject + "=================================");
	}
	
	public void consume() {
		logEnvironment();
		massiveTaskTest();
	}
	
	private void massiveTaskTest() {
		String taskName;
		
		logger.info("setup up environment to run simple calculation task.");
		manager.start();
		
		processor.start();
		
		manager.registProcessor(processor);
		processor.setProcessCompletedListener(manager);
		
		logger.info("beginning to run messive simple calculation tasks.");
		TaskPriority randomPriority;
		SimpleCalculationTask.OPERATOR operator;
		Random random = new Random(System.currentTimeMillis());
		SimpleCalculationTask task;
		for (int i=0; i < CONSUM_TASK_COUNT; i++) {
			taskName = "simpleCalculationTask " + i;
			randomPriority = TaskPriority.values()[(Math.abs(random.nextInt()) % TaskPriority.values().length)];
			task = new SimpleCalculationTask(taskName, processorName, randomPriority);
			operator = SimpleCalculationTask.OPERATOR.values()[(Math.abs(random.nextInt()) % SimpleCalculationTask.OPERATOR.values().length)]; 
			task.SetOperantor(operator);
			task.setOperandWithKey(SimpleCalculationTask.FIRST_OPERAND_KEY, random.nextDouble());
			task.setOperandWithKey(SimpleCalculationTask.SECOND_OPERAND_KEY, random.nextDouble());
			manager.submitTask(task);
		}
	}
	
	public static void main(String[] params) {
		new SimpleCalculationConsumer().consume();
	}
}
