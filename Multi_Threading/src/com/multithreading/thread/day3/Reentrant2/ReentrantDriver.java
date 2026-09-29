package com.multithreading.thread.day3.Reentrant2;

class MyThread extends Thread{
	
	Task task ;

	public MyThread(Task task) {
		super();
		this.task = task;
	}
	
	public void run() {
		
		task.doSomething();
	}
  	
	
}
public class ReentrantDriver {

	public static void main(String[] args) {
	Task t = new Task();
	
	MyThread m1 = new MyThread(t);
	MyThread m2 = new MyThread(t);
	m1.setName("M1 THREAD");
	m2.setName("M2 THREAD");
	
	m1.start();
	m2.start();
 
	}

}
