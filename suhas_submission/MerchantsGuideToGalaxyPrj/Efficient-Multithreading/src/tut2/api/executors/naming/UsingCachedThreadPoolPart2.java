package tut2.api.executors.naming;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import tuts.common.LoopTaskC;
import tuts.common.NamedThreadsFactory;

public class UsingCachedThreadPoolPart2 {
	
	public static void main(String[] args) throws InterruptedException {
		String currentThreadName = Thread.currentThread().getName();
		
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		ExecutorService execService = Executors.newCachedThreadPool(new NamedThreadsFactory());
		
		execService.execute(new LoopTaskC());	//Task-1
		execService.execute(new LoopTaskC());	//Task-2
		execService.execute(new LoopTaskC());	//Task-3
		
		TimeUnit.SECONDS.sleep(15);
		
		execService.execute(new LoopTaskC());	//Task-4
		execService.execute(new LoopTaskC());	//Task-5
		execService.execute(new LoopTaskC());	//Task-6
		execService.execute(new LoopTaskC());	//Task-7
		execService.execute(new LoopTaskC());	//Task-8
		
		execService.shutdown();
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
