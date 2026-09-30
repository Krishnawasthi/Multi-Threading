package com.multithreading.thread.executableservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Task extends Thread{
       int taskId;
	
	public Task(int taskId) {
	super();
	this.taskId = taskId;
}

	@Override
	public void run() {
		System.out.println("Number "+ taskId + " executed by thread ["+ Thread.currentThread().getName() +"]");
		
	}
	
	
}


public class ExecuteDriver {

	public static void main(String[] args) {
		
	  ExecutorService executorService   =   Executors.newFixedThreadPool(2);
	  
	  for(int i = 0; i<=20; i++) {
		  
		  Task task = new Task(i);
		  
		  executorService.execute(task);
	  }
	  
	  executorService.shutdown();
	  

	}

}
