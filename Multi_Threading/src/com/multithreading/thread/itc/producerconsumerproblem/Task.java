package com.multithreading.thread.itc.producerconsumerproblem;

public class Task {
	int data;
	boolean isDataAvailble = false;
	
	public synchronized void produce(int _data) throws InterruptedException {
		
		while(isDataAvailble) {
		System.out.println( Thread.currentThread().getName()+" is waiting");
			wait();
		}
		
		data = _data;
		System.out.println( Thread.currentThread().getName()+" Producing data = "+ data );
		isDataAvailble = true;
		notify();
		
		}
	public synchronized void comsume() throws InterruptedException {
		
		while(!isDataAvailble) {
			System.out.println( Thread.currentThread().getName()+" is waiting");
			wait();
		}
		System.out.println(Thread.currentThread().getName()+" Consuming data = "+ data);
		isDataAvailble = false;
		notify();
	}

}
