package com.multithreading.thread.day1;

class Mythread extends Thread {

	@Override
	public void run() {
		
		System.out.println("Mythread.run()......executing thr task: " + Thread.currentThread().getName());	

		Payment p = new Payment();
		p.doPay();
	}
	
	
}

public class HelloWord {

	public static void main(String[] args) {

		System.out.println("HelloWord.main()....Start" + Thread.currentThread().getName());
		

		
          //created 1st thread 
		Mythread t1 = new Mythread(); // new Thread created
		t1.setName("T1");
		t1.start();  //new thread started --> main thread + t1
		
		 //created 2nd thread 
		Mythread t2 = new Mythread(); 
		t2.setName("T2");
		t2.start();  //new thread started --> main thread + t1
		
		System.out.println("HelloWord.main()...End" + Thread.currentThread().getName());

	}

	

}

 class Payment{
	
	public void doPay() {
		
		
		System.out.println("Payment.pay().... excuted By"+ Thread.currentThread().getName());
	}
	
 }
