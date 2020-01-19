package tut7.api.threads.exceptionHandling;

import tuts.common.ExceptionLeakingTask;
import tuts.common.ThreadExceptionHandler;


public class HandlingUncaughtExceptionsDifferentlyPerThread {

	public static void main(String[] args) {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		Thread t1 = new Thread(new ExceptionLeakingTask(), "MyThread-1");
		t1.setUncaughtExceptionHandler(new ThreadExceptionHandler());
		
		Thread t2 = new Thread(new ExceptionLeakingTask(), "MyThread-2");
		t2.setUncaughtExceptionHandler(new ThreadExceptionHandler());
		
		Thread t3 = new Thread(new ExceptionLeakingTask(), "MyThread-3");
		t3.setUncaughtExceptionHandler(new ThreadExceptionHandler());
		
		Thread t4 = new Thread(new ExceptionLeakingTask(), "MyThread-4");
		t4.setUncaughtExceptionHandler(new ThreadExceptionHandler());
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
