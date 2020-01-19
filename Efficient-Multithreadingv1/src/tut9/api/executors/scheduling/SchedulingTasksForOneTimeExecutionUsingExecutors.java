package tut9.api.executors.scheduling;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import tuts.common.CalculationTaskD;
import tuts.common.NamedThreadsFactory;
import tuts.common.ScheduledTaskB;


public class SchedulingTasksForOneTimeExecutionUsingExecutors {

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss.SSS");
	
	public static void main(String[] args) throws Exception {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		ScheduledExecutorService execService = Executors.newScheduledThreadPool(3, new NamedThreadsFactory());
		
		System.out.println("[" + currentThreadName + "] Current time : " + dateFormatter.format(new Date()));
		
		ScheduledFuture<?> schedFuture1 = execService.schedule(new ScheduledTaskB(3000), 4, TimeUnit.SECONDS);
		ScheduledFuture<Integer> schedFuture2 = execService.schedule(new CalculationTaskD(2, 3, 3000), 6, TimeUnit.SECONDS);
		execService.schedule(new ScheduledTaskB(0), 8, TimeUnit.SECONDS);
		ScheduledFuture<Integer> schedFuture4 = execService.schedule(new CalculationTaskD(3, 4, 0), 10, TimeUnit.SECONDS);
		
		execService.shutdown();
		
		schedFuture1.cancel(true);
		schedFuture2.cancel(true);
		
		System.out.println("[" + currentThreadName + "] RETRIEVING THE RESULTS NOW ...\n");
		
//		System.out.println("[" + currentThreadName + "] TASK-1 RESULT = " + schedFuture1.get() + "\n");
//		System.out.println("[" + currentThreadName + "] TASK-2 RESULT = " + schedFuture2.get() + "\n");
		System.out.println("[" + currentThreadName + "] TASK-4 RESULT = " + schedFuture4.get() + "\n");
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
