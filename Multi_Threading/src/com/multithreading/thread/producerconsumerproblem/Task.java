package com.multithreading.thread.producerconsumerproblem;

public class Task
{
	int number;
	boolean isdataAvailable = false;
	

	
	//producer is producing something
	public synchronized void producer(int _num) throws InterruptedException {
		
		while(isdataAvailable){
			System.out.println("----------"+Thread.currentThread().getName() + " is waiting....");
			wait();
			
		}
	
		number = _num;
		System.out.println("Producing Data......: "+ number);
		isdataAvailable = true;
		notify();	
	}


	
	//consumer is consuming or doing its task
	public synchronized void  consumer() throws InterruptedException {
		
		while(!isdataAvailable){
			System.out.println("----------"+Thread.currentThread().getName() + " is waiting....");
			wait();
			
		}
	
		System.out.println("Comsuming Data......: "+ number);
		isdataAvailable = false;
		notify();	
	}
}





