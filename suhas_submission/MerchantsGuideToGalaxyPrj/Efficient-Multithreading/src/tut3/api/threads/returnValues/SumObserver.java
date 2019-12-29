package tut3.api.threads.returnValues;

import tuts.common.ResultListener;

public class SumObserver implements ResultListener<Integer> {

	private String taskId;
	
	public SumObserver(String taskId) {
		this.taskId = taskId;
	}
	
	
	@Override
	public void notifyResult(Integer result) {
		System.out.println("Result for " + taskId + " = " + result);
	}

}
