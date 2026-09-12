package com.multithreading.thread.producerconsumerproblem;

class FirstThread extends Thread{
	
	@Override
	public void run() {
	System.out.println("Running first thread......");
	
	try {
		Thread.currentThread().sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
}

class SecondThread extends Thread{
	
	@Override
	public void run(){
		
		
	try {
		Thread.currentThread().sleep(2000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	System.out.println("Running Second thread......");	
		
	}
	
}



public class ThreadExample {

	public static void main(String[] args) {
		
		FirstThread t1 = new FirstThread();
		t1.start();
		SecondThread t2 = new SecondThread();
		t2.start();
		
	}

}
