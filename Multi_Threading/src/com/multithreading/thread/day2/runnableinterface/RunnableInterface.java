package com.multithreading.thread.day2.runnableinterface;


///creating thread through runnable interface

class MyThread implements Runnable{

	@Override
	public void run() {
		
		System.out.println("Mythread.run()......" + Thread.currentThread().getName());
		
	}
	
	
}

public class RunnableInterface {

	public static void main(String[] args) {
		
		MyThread myThread =  new MyThread();
		
		Thread t1 = new Thread(myThread);
		
		
		 t1.start();
		 
		 Thread t2 = new Thread(myThread);
		 
		 t2.start();

	}

}
