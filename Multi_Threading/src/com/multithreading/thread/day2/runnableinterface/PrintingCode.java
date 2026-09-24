package com.multithreading.thread.day2.runnableinterface;


class TaskDo implements Runnable{

	@Override       
	public void run() {
  for(int i = 0; i<10; i++) {
	  
	  if(i == 5) {
	  try {
		System.out.println("thread is going to sleep");
		Thread.sleep(3000);
		
		System.out.println("Sleeping time is over... it woke up .. started work again");
	  } catch (InterruptedException e) {
		
		e.printStackTrace();
	  }
  }
	  System.out.println("Printing number: " + i);
  }
		
		
	}

}
public class PrintingCode {
 public static void main(String[] args) throws InterruptedException {
	
	 System.out.println("Driver1.main()....Start");
		TaskDo task = new TaskDo();
		Thread t1 = new Thread(task);
		t1.setName("Thread1");
		t1.start();
		t1.join();
		System.out.println("Driver1.main().....End");

}
 
}
