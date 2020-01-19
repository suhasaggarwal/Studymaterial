package tuts.common;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadExceptionHandler implements UncaughtExceptionHandler {

	private String handlerId;
	
	public ThreadExceptionHandler(String handlerId) {
		this.handlerId = handlerId;
	}
	
	public ThreadExceptionHandler() {
		//Do nothing
	}
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println(this + " caught Exception in Thread - \"" +
				t.getName() + "\" => " + e);
	}
	
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + this.hashCode() +
				(handlerId == null || "".equals(handlerId) ? "" :
					"(\"" + handlerId + "\")");
	}
}
