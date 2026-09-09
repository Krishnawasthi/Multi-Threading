package com.multithreading.thread.day1;



class Number extends Thread{
	
	@Override
	public synchronized void run() {
	
		for(int i = 0; i<10; i++) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Print Number: " + i +  "   " + Thread.currentThread().getName());
		} 
	
	}
}
public class PrintNumber {

	public static void main(String[] args) {
	
		Number t0 = new Number();
		t0.start();
		// you can not predict the order of execution.
		Number t1 = new Number();
		t1.start();
		
		

	}

}
