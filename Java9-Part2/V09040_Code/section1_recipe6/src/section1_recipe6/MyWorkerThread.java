package section1_recipe6;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;

public class MyWorkerThread extends ForkJoinWorkerThread {
	//In educative example, it is not being used as a global counter because it is localised per thread and is garbage collected when thread finishes its run
	private final static ThreadLocal<Integer> taskCounter=new ThreadLocal<>();

	protected MyWorkerThread(ForkJoinPool pool) {
		super(pool);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		System.out.printf("MyWorkerThread %d: Initializing task counter.\n",getId());
		taskCounter.set(0);
	}
	
	@Override
	protected void onTermination(Throwable exception) {
		//Task counter is local per thread, how many recursive tasks were executed by each thread
		System.out.printf("MyWorkerThread %d: %d\n",getId(),taskCounter.get());
		super.onTermination(exception);
	}
	
	public void addTask() {
		taskCounter.set(taskCounter.get() + 1);;
	}

}
