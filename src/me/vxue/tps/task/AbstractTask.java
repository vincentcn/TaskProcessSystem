package me.vxue.tps.task;

/***
 * The base task.
 * 
 * @author Vincent Xue
 *
 */

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractTask implements Comparable<AbstractTask>, Serializable  {
	
	/***
	 * The priority of Task. 
	 */
	
	private static final long serialVersionUID = -5378414384076557040L;
	/***
	 * priority of task.
	 */
	private TaskPriority priority = TaskPriority.MEDIUM;
	/***
	 * task name
	 */
	private String name = "Abstract Task";
	/***
	 * Required processor name
	 */
	private String processorName;

	@Override
	public String toString() {
		return "Task(Name: " + name + ", Priority: " + priority.toString() + ", ProcessorName: " + processorName + ")";
	}

	public TaskPriority getPriority() {
		return priority;
	}

	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}
	
//  SKip the mapping, enfore only 3 priority.
//	public void setPriority(int priority) {
//		if (priority < PRIORITY.HIGH.ordinal() ) {
//			this.priority = PRIORITY.HIGH;
//		} else if (priority > PRIORITY.LOW.ordinal()) {
//			this.priority = PRIORITY.LOW;
//		} else {
//			this.priority = PRIORITY.values()[priority];
//		}
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessorName() {
		return processorName;
	}

	public void setProcessorName(String processorName) {
		this.processorName = processorName;
	}
	
	public int compareTo(AbstractTask t) {
		return getPriority().ordinal() - t.getPriority().ordinal();
	}
	
	public AbstractTask() {
	}
	
	public AbstractTask(String name, String processorName) {
		this.name = name;
		this.processorName = processorName;
	}
	
	public AbstractTask(String name, String processorName, TaskPriority priority) {
		this(name, processorName);
		
		this.priority = priority;
	}
	
	protected Logger getLogger() {
		return LogManager.getLogger(getClass());
	}
	
	/***
	 * The callback of on process finished.
	 */
	public abstract void OnResult();
}
