package me.vxue.tps.process;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import me.vxue.component.AbstractComponent;
import me.vxue.tps.task.AbstractTask;
import me.vxue.tps.process.processor.AbstractProcessor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * The manager of tast processing.
 * @author Vincent Xue
 *
 */
public class TaskProcessManager extends AbstractComponent implements ProcessResultListenerI {
	
	static Logger log = LogManager.getLogger(TaskProcessManager.class);
	private PriorityBlockingQueue<AbstractTask> submitedTasks;
	private PriorityBlockingQueue<AbstractTask> completedTasks;
	
	private ConcurrentHashMap<String, AbstractProcessor> processes = 
			new ConcurrentHashMap<String, AbstractProcessor>();
	
	private class ProcessTaskThread implements Runnable{
		public void run(){
			while(isServing()){
				try {
					AbstractTask task = submitedTasks.take();
					log.info("Processing task:", task);
					AbstractProcessor processor = findProcessor(task.getName());
					if (processor != null){
						processor.process(task);
					} else {
						log.warn("No processor is found for task: ", task.getName());
					}
					
				} catch (InterruptedException e) {
					log.warn(e.getStackTrace());
				}
			}
		}
	}
	
	private AbstractProcessor findProcessor(String processName){
		return processes.get(processName);
	}
	
	private class ProcessTaskResultThread implements Runnable{
		public void run(){
		}
	}
	
	
	/***
	 * submit a task.
	 * @param task
	 */
	public void submitTask(AbstractTask task) {
	}
	

	@Override
	public void onProcessCompleted(AbstractTask task) {
	}

	public void start(){
	}
	
	public void stop(){
	}
}
