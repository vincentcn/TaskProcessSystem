package me.vxue.tps.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/***
 * The basic unit, Component
 * @author Vincent Xue
 *
 */
public class AbstractComponent {

	public enum STATE {UNKNOWN, STARTED, STOPPED};
	
	private STATE state = STATE.UNKNOWN;
	private String name;

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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	protected Logger getLogger(){
		return LogManager.getLogger(getClass());
	}
	
	public void start(){
		state = STATE.STARTED;
	}
	
	public void stop() {
		state = STATE.STOPPED;
	}
	
	public AbstractComponent() {
		
	}
	
	public AbstractComponent(String name) {
		this.name = name;
	}
}
