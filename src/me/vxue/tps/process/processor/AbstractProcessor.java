package me.vxue.tps.process.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import me.vxue.tps.component.AbstractComponent;
import me.vxue.tps.process.ProcessResultListenerI;
import me.vxue.tps.task.AbstractTask;

/***
 * The base processor.
 * It will receive tasks, and process them async - A thread pool will create thread to process the tasks one by one.
 * @author Vincent Xue
 *
 */
public abstract class AbstractProcessor extends AbstractComponent implements TaskProcessorI{

	// thread pool related.
	private ThreadPoolExecutor threadPoolExecutor;
	private static final int DEFAULT_THREAD_COUNT = Runtime.getRuntime().availableProcessors() + 1;
	
	private int threadCount = DEFAULT_THREAD_COUNT;
	
	private ProcessResultListenerI processCompletedListener;
	
	// The thread to deal with task.
	private class ProcessorThread implements Runnable, Comparable<ProcessorThread> {
		private AbstractTask task;
		
		public ProcessorThread(AbstractTask task) {
			this.task = task;
		}
		
		public void run() {
			getLogger().info("Do processing task - " + task + " in processor ", getName());
			doProcess(task);
			
			getLogger().info("Finished processcing task - " + task);
			processCompletedListener.onProcessCompleted(task);
		}
		
		@Override
		public int compareTo(ProcessorThread thread) {
			if (getTask() == null || thread.getTask() == null) {
				return 0;
			} else {
				return getTask().compareTo(thread.getTask());
			}
		}
		public AbstractTask getTask() {
			return task;
		}
		
		
		@SuppressWarnings("unused")
		public void setTask(AbstractTask task) {
			this.task = task;
		}
	}
	
	public String getProcessorName() {
		return getName();
	}
	
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	
	public ProcessResultListenerI getProcessCompletedListener() {
		return processCompletedListener;
	}

	public void setProcessCompletedListener(
			ProcessResultListenerI processCompletedListener) {
		this.processCompletedListener = processCompletedListener;
	}

	@Override
	public boolean process(AbstractTask task) {
		if (canProcess(task)) {
			getLogger().info(getName() + " will process task " + task);
			threadPoolExecutor.execute(new ProcessorThread(task));
			
			return true;
		}
		
		getLogger().warn("Cannot process task - " + task);
		
		return false;
	}

	@Override
	public void start() {
		super.start();
		
		threadPoolExecutor = new ThreadPoolExecutor(getThreadCount(), getThreadCount(), 
								10L, TimeUnit.MINUTES, new PriorityBlockingQueue<Runnable>(),
								Executors.defaultThreadFactory(),
								new ThreadPoolExecutor.CallerRunsPolicy());
		
		
		getLogger().info("Processor " + getName() + "is started.");
	}

	@Override
	public void stop() {
		super.stop();
		
		threadPoolExecutor.shutdownNow();
		
		getLogger().info("Processor " + getName() + "is stopped.");
	}
	
	public AbstractProcessor(String name) {
		super(name);
	}
	
	public AbstractProcessor(String name, int threadCount) {
		this(name);
		
		this.threadCount = threadCount;
	}
	
	/***
	 * whether can process the task.
	 */
	public abstract boolean canProcess(AbstractTask task);
	
	/***
	 * The real logic to process the task.
	 * @param task
	 */
	public abstract void doProcess(AbstractTask task);
	
}

