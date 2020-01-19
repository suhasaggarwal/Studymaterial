package tut6.api.threads.terminating;

import java.util.concurrent.TimeUnit;

import tuts.common.LoopTaskF;


public class TerminatingThreadsSecondWay {

	public static void main(String[] args) throws InterruptedException {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		LoopTaskF task1 = new LoopTaskF();
		LoopTaskF task2 = new LoopTaskF();
		LoopTaskF task3 = new LoopTaskF();
		
		Thread t1 = new Thread(task1, "MyThread-1");
		t1.start();
		
		Thread t2 = new Thread(task2, "MyThread-2");
		t2.start();
		
		Thread t3 = new Thread(task3, "MyThread-3");
		t3.start();
		
		TimeUnit.MILLISECONDS.sleep(3000);
		
		System.out.println("[" + currentThreadName + "] Interrupting " + t1.getName() + "....");
		t1.interrupt();
		
		System.out.println("[" + currentThreadName + "] Interrupting " + t2.getName() + "....");
		t2.interrupt();
		
		System.out.println("[" + currentThreadName + "] Interrupting " + t3.getName() + "....");
		t3.interrupt();
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
