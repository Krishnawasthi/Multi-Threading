package com.multithreading.thread.itc.producerconsumerproblem;

public class Consumer extends Thread {
	
	Task task;

	public Consumer(Task task) {
		super();
		this.task = task;
	}
	
	@Override
	public void run(){
		
		for(int i = 0; i<10; i++) {
			
			
			try {
				task.comsume();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
