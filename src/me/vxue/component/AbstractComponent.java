package me.vxue.component;

/***
 * The basic unit, Component
 * @author Vincent Xue
 *
 */
public class AbstractComponent {

	public enum STATE {UNKNOWN, STARTED, STOPPED};
	
	private STATE state = STATE.UNKNOWN;

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}
	
	public boolean isServing(){
		return state == STATE.STARTED;
	}
	
	public boolean isStopped(){
		return state == STATE.STOPPED;
	}
}
