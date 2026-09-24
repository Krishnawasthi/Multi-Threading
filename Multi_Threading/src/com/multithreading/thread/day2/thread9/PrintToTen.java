package com.multithreading.thread.day2.thread9;


class Task implements Runnable{
	
	public synchronized void printNumbers() {
		
		for(int i = 0; i< 10; i++) {
			System.out.println("PrintNumbers: "+ i +" ["+ Thread.currentThread().getName()+ "]");
		}
	}

	@Override
	public void run() {
		 printNumbers();
		
	}
}
class PrintThreads extends Thread {
	
     Task task;
	PrintThreads(Task task1){
		
		this.task = task1;
	}
		@Override
		public void run() {
			
			task.printNumbers();
			
		}
	}	
	

public class PrintToTen {

	public static void main(String[] args) {
		
		
		Task task = new  Task();
		
		Thread t1 = new Thread(task);
		t1.setName("Thread1");
		t1.start();
		

	   Thread t2 = new Thread(task);
		t2.setName("Thread2");
		t2.start();
	}

}
