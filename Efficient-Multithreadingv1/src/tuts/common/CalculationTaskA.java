package tuts.common;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class CalculationTaskA implements Callable<Integer> {

	private int a;
	private int b;
	private long sleepTime;
	
	private static int count = 0;
	private int instanceNumber;
	private String taskId;
	
	public CalculationTaskA(int a, int b, long sleepTime) {
		this.a = a;
		this.b = b;
		this.sleepTime = sleepTime;
		
		this.instanceNumber = ++count;
		this.taskId = "CalcTaskA-" + instanceNumber;
	}
	
	
	@Override
	public Integer call() throws Exception {
		String currentThreadName = Thread.currentThread().getName();
		
		System.out.println("##### [" + currentThreadName + "] <" + taskId + "> STARTIING #####");
		System.out.println("[" + currentThreadName + "] <" + taskId + "> Sleeping for " + sleepTime + " millis");
		
		TimeUnit.MILLISECONDS.sleep(sleepTime);
		
		System.out.println("****** [" + currentThreadName + "] <" + taskId + "> DONE ******");
		
		return a + b;
	}
	
}
