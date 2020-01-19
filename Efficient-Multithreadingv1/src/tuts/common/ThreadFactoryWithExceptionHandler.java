package tuts.common;

public class ThreadFactoryWithExceptionHandler extends NamedThreadsFactory {

	public Thread newThread(Runnable r) {
		Thread t = super.newThread(r);
		t.setUncaughtExceptionHandler(new ThreadExceptionHandler());
		
		return t;
	}
}
