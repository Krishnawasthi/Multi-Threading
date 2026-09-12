package com.multithreading.thread.producerconsumerproblem;

class AThread extends Thread{
	
@Override
public void run() { //does not return anything and can not thrwos exception
	
	
	 boolean status = sendEmail();
	 if(status) {
		 System.out.println("AThread.run()....Task Successful "+ Thread.currentThread().getName());
	 }
	 else {
		 
		 System.out.println("AThread.run()....Task failed "+  Thread.currentThread().getName());
	 }
	//limitations of run method
	//we(thread) will not be able to track the status of run method 
}

  public boolean sendEmail() {
	  
	  return false;
  }
}



public class Limitaions {

	public static void main(String[] args) {
		
		AThread t1 = new AThread();
		t1.setName("Thread 1");
		t1.start();
		AThread t2 = new AThread();
		t2.setName("Thread 2");
		t2.start();
		

	}

}
