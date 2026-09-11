package com.multithreading.thread.producerconsumerproblem;

public class Consumer extends Thread {

	Task task;
	
	public Consumer(Task task) {
		super();
		this.task = task;
	}

	@Override
	public void run()
	{
		for(int i = 0; i< 10; i++) {
		try {
			
			task.consumer();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		}
		
	}
}
