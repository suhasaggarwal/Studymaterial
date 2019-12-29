package tuts.common;

import java.util.concurrent.TimeUnit;

public class LoopTaskD implements Runnable {

	private static int count = 0;
	private int instanceNumber;
	private String taskId;
	
	private long sleepTime;
	
	@Override
	public void run() {
		boolean isRunningInDaemonThread = Thread.currentThread().isDaemon();
		String threadType = isRunningInDaemonThread ? "DAEMON" : "USER";
		
		String currentThreadName = Thread.currentThread().getName();
		
		System.out.println("##### [" + currentThreadName + ", " + threadType + "] <" + taskId + "> STARTING #####");
		
		for (int i=10; i>0; i--) {
			System.out.println("[" + currentThreadName + ", " + threadType + "] <" + taskId + ">TICK TICK " + i);
			
			try {
				TimeUnit.MILLISECONDS.sleep(sleepTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("***** [" + currentThreadName + ", " + threadType + "] <" + taskId + "> DONE ******");
	}
	
	
	public LoopTaskD(long sleepTime) {
		this.sleepTime = sleepTime;
		
		this.instanceNumber = ++count;
		this.taskId = "LoopTaskD" + instanceNumber;
	}
}
