package com.multithreading.thread.itc.producerconsumerproblem;

public class ThreadDriver {

	public static void main(String[] args) {
		
		Task task = new Task();
		
		Producer p = new Producer(task);
		p.setName("Producer ");
		p.start();
		
		Consumer c = new Consumer(task);
		c.setName("Consumer ");
		c.start();
	}

}
