package me.vxue.tps.process;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

import me.vxue.tps.task.AbstractTask;
import me.vxue.tps.component.AbstractComponent;
import me.vxue.tps.process.processor.TaskProcessorI;

/***
 * The manager of tast processing.
 * @author Vincent Xue
 *
 */
public class TaskProcessManager extends AbstractComponent implements ProcessResultListenerI {
	
	private PriorityBlockingQueue<AbstractTask> submitedTasks = new PriorityBlockingQueue<AbstractTask>();
	private PriorityBlockingQueue<AbstractTask> completedTasks = new PriorityBlockingQueue<AbstractTask>();
	private ConcurrentHashMap<String, TaskProcessorI> processors = 
			new ConcurrentHashMap<String, TaskProcessorI>();
	
	/***
	 * processing task thread.
	 *
	 */
	private class DistributeTaskThread implements Runnable{
		public void run(){
			while(isServing()){
				try {
					AbstractTask task = submitedTasks.take();
					getLogger().info("Find processor and distribute task: " + task.toString());
					TaskProcessorI processor = findProcessor(task.getProcessorName());
					if (processor != null){
						processor.process(task);
					} else {
						getLogger().warn("No processor is found for task: " + task.getName());
					}
					
				} catch (InterruptedException e) {
					getLogger().warn("Distribute task thread is interrupted.");
					getLogger().warn(e.getStackTrace());
				}
			}
		}
	}
	
	/***
	 * Processing task result thread.
	 *
	 */
	private class DistributeTaskResultThread implements Runnable{
		public void run(){
			while(isServing()){
				try {
					AbstractTask task = completedTasks.take();
					getLogger().info("Distribute the result for task: " + task);
					task.OnResult();
				} catch(InterruptedException e) {
					getLogger().warn("Distribute result thread is interrupted.");
					getLogger().warn(e.getStackTrace());
				}
				
			}
		}
	}
	
	public TaskProcessManager() {
		super("Task processor Manager");
	}
	
	/***
	 * Find the processor according to the name.
	 * @param processName
	 * @return TaskProcessorI
	 */
	private TaskProcessorI findProcessor(String processName){
		return processors.get(processName);
	}

	@Override
	public void onProcessCompleted(AbstractTask task) {
		try {
			completedTasks.offer(task);
		} catch (ClassCastException cce) {
			getLogger().error(cce.getStackTrace());
		} catch (NullPointerException npe) {
			getLogger().error(npe.getStackTrace());
		}
	}
	
	/***
	 * submit a task.
	 * @param task
	 */
	public void submitTask(AbstractTask task) {
		try {
			submitedTasks.offer(task);
		} catch (ClassCastException cce) {
			getLogger().error(cce.getStackTrace());
		} catch (NullPointerException npe) {
			getLogger().error(npe.getStackTrace());
		}
	}
	
	/***
	 * register a processor with the given name.
	 * @param name
	 * @param processor
	 */
	public void registProcessor(String name, TaskProcessorI processor) {
		processors.putIfAbsent(name, processor);
	}
	
	/***
	 * register a processor
	 * @param processor
	 */
	public void registProcessor(TaskProcessorI processor) {
		registProcessor(processor.getProcessorName(), processor);
	}
	
	@Override
	public void start() {
		super.start();		
		Thread processTaskThread =  new Thread(new DistributeTaskThread());
		processTaskThread.setName("Distribute Task Thread");
		processTaskThread.start();
		
		Thread processResultThread = new Thread(new DistributeTaskResultThread());
		processResultThread.setName("Distribute Task Result Thread");
		processResultThread.start();
		
		getLogger().info("Task Process Manager is started.");
	}

	@Override
	public void stop() {
		super.stop();
		
		submitedTasks.clear();
		completedTasks.clear();
		
		getLogger().info("Task process Manager is stopped.");
	}
}
