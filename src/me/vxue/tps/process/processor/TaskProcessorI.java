package me.vxue.tps.process.processor;

/***
 * The processor of task.
 * @author Vincent Xue
 *
 */

import me.vxue.tps.task.AbstractTask;

public interface TaskProcessorI {
	
	/***
	 * 
	 * @return processor name.
	 */
	public String getProcessorName();
	

	/***
	 * Check whether the given task can be processed.
	 * @param task
	 * @return whether can process the task.
	 */
	public boolean canProcess(AbstractTask task);
	
	
	/***
	 * process the given task.
	 * @param task
	 * @return
	 */
	public boolean process(AbstractTask task);
}
