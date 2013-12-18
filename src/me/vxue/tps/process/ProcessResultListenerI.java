package me.vxue.tps.process;

import me.vxue.tps.task.AbstractTask;

/***
 * The adaptor deal with processing result.
 * @author Vincent Xue
 *
 */
public interface ProcessResultListenerI {
	
	/***
	 * @param task
	 */
	void onProcessCompleted(AbstractTask task);
}