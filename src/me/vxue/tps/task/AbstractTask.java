package me.vxue.tps.task;

/***
 * The base task.
 * 
 * @author Vincent Xue
 *
 */

import java.io.Serializable;

public abstract class AbstractTask implements Comparable<AbstractTask>, Serializable  {
	
	/***
	 * The priority of Task. 
	 */
	public enum PRIORITY {HIGH, MEDIUM, LOW};
	
	private static final long serialVersionUID = -5378414384076557040L;
	/***
	 * priority of task.
	 */
	private PRIORITY priority = PRIORITY.MEDIUM;
	/***
	 * task name
	 */
	private String name = "Abstract Task";
	/***
	 * Required processor name
	 */
	private String processorName;

	
	public abstract int compareTo(AbstractTask t);

	@Override
	public String toString() {
		return "Task - Name: " + name + ", Priority: " + priority.toString();
	}

	public PRIORITY getPriority() {
		return priority;
	}

	public void setPriority(PRIORITY priority) {
		this.priority = priority;
	}

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
	
	
}
