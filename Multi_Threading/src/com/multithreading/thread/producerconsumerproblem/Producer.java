package com.multithreading.thread.producerconsumerproblem;

public class Producer extends Thread
{
	
    Task task;
  
	public Producer(Task task) {
	super();
	this.task = task;
}

	@Override
	public void run() {
		for(int i = 0; i<10; i++) {
			
			try {
				
				task.producer(i);
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
			
				e.printStackTrace();
			}
		}
		
	}
}
