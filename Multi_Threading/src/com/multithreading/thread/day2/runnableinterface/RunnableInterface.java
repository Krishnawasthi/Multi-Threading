package com.multithreading.thread.day2.runnableinterface;


///creating thread through runnable interface

class MyThread implements Runnable{

	@Override
	public void run()  // running
	{
		
		System.out.println("Mythread.run()...... attending classes --- started at 9am" );
		System.out.println("Mythread.run()......" );
		System.out.println("Mythread.run()......");
		System.out.println("Mythread.run()......" );
		//T1(student) can go to Sleep/pause state (30 min) --> Waiting state - 
		//once waiting is over , t1 will move to runnable --> CPu gives time to t1 --> running state
		System.out.println("Mythread.run()......" );
		System.out.println("Mythread.run()......" );
		System.out.println("Mythread.run()......" );
		
		System.out.println("Mythread.run()...... attending classes --- till end at 6pm" );
	}
	
	
}

public class RunnableInterface {

	public static void main(String[] args) {
		
		MyThread myThread =  new MyThread();
		
		Thread t1 = new Thread(myThread); //new born (thread object created)
		
		
		 t1.start();  //  thread moved from new born to runnbale
		 
		 t1.start();  //??? no need this otherwise you will get exception
		 
		 
	}

}
