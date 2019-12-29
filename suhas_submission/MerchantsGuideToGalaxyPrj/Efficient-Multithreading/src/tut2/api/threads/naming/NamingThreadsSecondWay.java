package tut2.api.threads.naming;

import java.util.concurrent.TimeUnit;

import tuts.common.LoopTaskC;

public class NamingThreadsSecondWay {

	public static void main(String[] args) {
		String currentThreadName = Thread.currentThread().getName();
		
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		new Thread(new LoopTaskC(), "MyThread-1").start();
		
		Thread t2 = new Thread(new LoopTaskC());
//		t2.setName("MyThread-2");
		t2.start();
		
		try {
			TimeUnit.MILLISECONDS.sleep(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t2.setName("MyThread-2");
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
