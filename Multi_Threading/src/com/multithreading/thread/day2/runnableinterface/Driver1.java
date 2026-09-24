package com.multithreading.thread.day2.runnableinterface;

//creating thread using runnable interface 

class Task implements Runnable{

	@Override       
	public void run() {

   System.out.println("Task.run() "+Thread.currentThread().getName());
		
	}
	
	
}

public class Driver1 {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Driver1.main()....Start");
		Task task = new Task();
		Thread t1 = new Thread(task);
		t1.setName("Thread1");
		t1.start();
		
		t1.join();
		
		Thread t2 = new Thread(task);
		t2.setName("Thread2");
		t2.start();
		
		t2.join();
		
		System.out.println("Driver1.main().....End");

	}

}
