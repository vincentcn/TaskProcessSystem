package me.vxue.tps.process.processor;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import me.vxue.tps.task.AbstractTask;

/***
 * The base processor.
 * It will receive tasks, and process them async - A thread pool will create thread to process the tasks one by one.
 * @author Vincent Xue
 *
 */
public abstract class AbstractProcessor implements TaskProcessorI{

	// thread pool related.
	private ThreadPoolExecutor theadPoolExecutor;
	private PriorityBlockingQueue<AbstractTask> priorityBlockingQueue;
	
	public abstract String getProccessorName();
	
	@Override
	public boolean canProcess(AbstractTask task) {
		// the abstract processor can process nothing.
		return false;
	}

	public abstract boolean process(AbstractTask task);
	
	public void start(){
		
	}
	
	public void stop(){
		
	}
}

