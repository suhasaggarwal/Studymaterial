package tut1.api.threads.running;

import java.util.concurrent.TimeUnit;

public class FourthWay {
	
	public static void main(String[] args) {
		System.out.println("Main thread starts here...");
		
		new Thread(new FourthTask()).start();
		
		Thread t = new Thread(new FourthTask());
		t.start();
		
		System.out.println("Main thread ends here...");
	}
}


class FourthTask implements Runnable {
	
	private static int count = 0;
	private int id;
	
	@Override
	public void run() {
		for (int i=10; i>0; i--) {
			System.out.println("<" + id + ">TICK TICK " + i);
			
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public FourthTask() {
		this.id = ++count;
	}
}