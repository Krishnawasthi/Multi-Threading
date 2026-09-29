package com.multithreading.thread.itc.producerconsumerproblem;

public class Task {
	int data;
	boolean isDataAvailble = false;
	
	public synchronized void produce(int _data) throws InterruptedException {
		
		while(isDataAvailble) {
		System.out.println( Thread.currentThread().getName()+" is waiting.....");
			wait();
		}
		
		this.data = _data;
		System.out.println( Thread.currentThread().getName()+" Producing data = "+ data );
		isDataAvailble = true;
		System.out.println("-----------------producer is notifing consumer to consume---------------");
		notify();  //notify consumer so that consumer can coomsume this.
		System.out.println();
		}
	public synchronized void comsume() throws InterruptedException {
		
		while(!isDataAvailble) {
			System.out.println( Thread.currentThread().getName()+" is waiting.....");
			wait();
		}
		System.out.println(Thread.currentThread().getName()+" Consuming data = "+ data);
		isDataAvailble = false;
		System.out.println("--------------------consumer is notifing producer to generate more data-------------");
		notify();  //consumer is notify to the producer
		System.out.println();
	}

}
