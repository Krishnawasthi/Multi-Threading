package com.multithreading.thread.producerconsumerproblem;

public class Driver {

	public static void main(String[] args) throws InterruptedException {
		 Task t = new Task();
		 
		Producer p = new Producer(t);
		p.setName("Producer ");
	    p.start();
	
	   
		Consumer c = new Consumer(t);
		c.setName("Consumer ");
		c.start();
		
		
	}

}
