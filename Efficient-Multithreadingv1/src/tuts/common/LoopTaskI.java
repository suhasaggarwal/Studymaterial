package tuts.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LoopTaskI implements Runnable {

	private static int count = 0;
	private int instanceNumber;
	private String taskId;
	
	private CountDownLatch doneCountLatch;
	
	@Override
	public void run() {
		boolean isRunningInDaemonThread = Thread.currentThread().isDaemon();
		String threadType = isRunningInDaemonThread ? "DAEMON" : "USER";
		
		String currentThreadName = Thread.currentThread().getName();
		
		System.out.println("##### [" + currentThreadName + ", " + threadType + "] <" + taskId + "> STARTING #####");
		
		for (int i=10; i>0; i--) {
			System.out.println("[" + currentThreadName + ", " + threadType + "] <" + taskId + ">TICK TICK " + i);
			
			try {
				TimeUnit.MILLISECONDS.sleep((long)(Math.random() * 1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("***** [" + currentThreadName + ", " + threadType + "] <" + taskId + "> DONE ******");
		
		if (doneCountLatch != null) {
			doneCountLatch.countDown();
			
			System.out.println("***** [" + currentThreadName + ", " + threadType + "] <" + taskId + 
					"> LATCH COUNT = " + doneCountLatch.getCount());
		}
	}
	
	
	public LoopTaskI(CountDownLatch doneCountLatch) {
		this.doneCountLatch = doneCountLatch;
		
		this.instanceNumber = ++count;
		this.taskId = "LoopTaskI" + instanceNumber;
	}
}
