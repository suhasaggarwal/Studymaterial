package tut6.api.executors.terminating;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import tuts.common.CalculationTaskB;
import tuts.common.CalculationTaskC;
import tuts.common.FactorialTaskB;
import tuts.common.LoopTaskA;
import tuts.common.LoopTaskF;
import tuts.common.NamedThreadsFactory;
import tuts.common.TaskResult;

public class TerminatingAllExecutorTasks {

	public static void main(String[] args) throws Exception {
		String currentThreadName = Thread.currentThread().getName();
		System.out.println("[" + currentThreadName + "] Main thread starts here...");
		
		ExecutorService execService = Executors.newCachedThreadPool(new NamedThreadsFactory());
		
		LoopTaskA task1 = new LoopTaskA();
		LoopTaskF task2 = new LoopTaskF();
		FactorialTaskB task3 = new FactorialTaskB(30, 500);
		CalculationTaskC task4 = new CalculationTaskC();
		CalculationTaskB task5 = new CalculationTaskB(2, 3, 9000);
		
		execService.execute(task1);
		execService.execute(task2);
		Future<Long> t3Future = execService.submit(task3);
		Future<Long> t4Future = execService.submit(task4);
		Future<TaskResult<String, Integer>> t5Future = execService.submit(task5);
		
		TimeUnit.MILLISECONDS.sleep(1000);
		
		execService.shutdownNow();
		
		System.out.println("[" + currentThreadName + "] ALL THREADS TERMINATED = " +
				execService.awaitTermination(5000, TimeUnit.MILLISECONDS));
		
		System.out.println("[" + currentThreadName + "] 'FactorialTaskB-1' Result = " + 
				t3Future.get());
		
		System.out.println("[" + currentThreadName + "] 'CalcTaskC-1' Result = " + 
				t4Future.get());
		
		try {
			System.out.print("[" + currentThreadName + "] 'CalcTaskB-1' Result = " + 
				t5Future.get());
		} catch (ExecutionException ee) {
			System.out.println("[" + currentThreadName + "] 'CalcTaskB-1 Result = Got ExecutionException. The cause is : \n");
			ee.getCause().printStackTrace();
		}
		
		System.out.println("[" + currentThreadName + "] Main thread ends here...");
	}
}
