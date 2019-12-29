package tut3.api.executors.returningValues;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import tuts.common.CalculationTaskB;
import tuts.common.LoopTaskA;
import tuts.common.NamedThreadsFactory;
import tuts.common.TaskResult;


public class ReturningValuesUsingExecutorsSecondWay {
	
	public static void main(String[] args) {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		ExecutorService execService = Executors.newCachedThreadPool(new NamedThreadsFactory());
		
		CompletionService<TaskResult<String, Integer>> tasks = 
				new ExecutorCompletionService<TaskResult<String, Integer>>(execService);
		
		tasks.submit(new CalculationTaskB(2, 3, 2000));
		tasks.submit(new CalculationTaskB(3, 4, 1000));
		tasks.submit(new CalculationTaskB(4, 5, 500));
		
//		Future<?> result4       = execService.submit(new LoopTaskA());
		tasks.submit(new LoopTaskA(), new TaskResult<String, Integer>("LoopTaskA-1", 999));
		
		execService.shutdown();
		
		for (int i = 0; i < 4; i++) {
			try {
				System.out.println(tasks.take().get());
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
