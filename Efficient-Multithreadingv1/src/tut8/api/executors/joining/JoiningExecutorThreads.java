package tut8.api.executors.joining;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tuts.common.LoopTaskI;
import tuts.common.NamedThreadsFactory;


public class JoiningExecutorThreads {

	public static void main(String[] args) throws InterruptedException {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		ExecutorService execService = Executors.newCachedThreadPool(new NamedThreadsFactory());
		
		CountDownLatch doneSignal = new CountDownLatch(2);
		
		execService.execute(new LoopTaskI(doneSignal));
		execService.execute(new LoopTaskI(doneSignal));
		execService.execute(new LoopTaskI(doneSignal));
		execService.execute(new LoopTaskI(doneSignal));
		
		execService.shutdown();
		
		try {
			doneSignal.await();
			System.out.println("[" + currentThreadName + "] " + currentThreadName + 
					" GOT THE SIGNAL TO CONTINUE ...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
