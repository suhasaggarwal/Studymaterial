package tuts.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class ScheduledTaskA extends TimerTask {

	private long sleepTime;
	
	private static int count = 0;
	private int instanceNumber;
	private String taskId;
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
	
	public ScheduledTaskA(long sleepTime) {
		this.sleepTime = sleepTime;
		
		this.instanceNumber = ++count;
		this.taskId = "SchedTaskA-" + instanceNumber;
	}
	
	
	@Override
	public void run() {
		Date startTime = new Date();
		Date scheduledForRunningTime = new Date(super.scheduledExecutionTime());
		
		String currentThreadName = Thread.currentThread().getName();
		
		System.out.println("##### [" + currentThreadName + "] <" + taskId + "> SCHEDULED TO RUN AT : " +
				dateFormatter.format(scheduledForRunningTime) + ", ACTUALLY STARTED AT : " +
				dateFormatter.format(startTime) + " #####");
		
		try {
			TimeUnit.MILLISECONDS.sleep(sleepTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("****** [" + currentThreadName + "] <" + taskId + "> FINISHED AT : " +
				dateFormatter.format(new Date()) + " ******\n");
	}
	
}
