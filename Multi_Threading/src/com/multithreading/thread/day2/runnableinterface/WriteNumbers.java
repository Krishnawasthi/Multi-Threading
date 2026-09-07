package com.multithreading.thread.day2.runnableinterface;

class PrintNumber implements Runnable{

	@Override
	public void run() {  //  thread is on running state executing state
		
		for(int i = 0; i <= 10; i++) {
			
			System.out.println("Number is: " + i);
			if(i == 5) {
				
				System.out.println("Sending " + Thread.currentThread().getName() + " to waiting state");
				try {
					
					Thread.currentThread().sleep(5000);   //thread is on sleep state for 5 milli seconds
				}
				catch(InterruptedException e) {
					
					e.printStackTrace();
				}
				
			}
		}		
	}	
			
}

public class WriteNumbers {

	public static void main(String[] args) 
	{
	
		PrintNumber numbers = new PrintNumber(); //creating new thread Object
		
		Thread t1 = new Thread(numbers);
		
		t1.start();  // runnable
			
			
		
		
	}

}
