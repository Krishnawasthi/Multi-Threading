package com.multithreading.thread.yield;

class FirstThread extends Thread{
	
	@Override
	public void run() { //does not return anything and can not thrwos exception
		
		System.out.println("FirstThread.run().....START: " + Thread.currentThread().getName());
		Thread.yield(); //used to pause the current thread and give chance to other thread
		System.out.println("FirstThread.run().....END  : " + Thread.currentThread().getName());
		
		//limitations of run method
		//we(thread) will not be able to track the status of run method 
	}
}

public class ThreadYield {

	public static void main(String[] args) {
		
		FirstThread t1 = new FirstThread();
		t1.setName("Thread 1");
		t1.start();
		FirstThread t2 = new FirstThread();
		t2.setName("Thread 2");
		t2.start();
		FirstThread t3 = new FirstThread();
		t3.setName("Thread 3");
		t3.start();
		
	}

}
