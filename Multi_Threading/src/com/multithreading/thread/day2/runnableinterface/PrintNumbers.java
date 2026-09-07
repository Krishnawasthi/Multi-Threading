package com.multithreading.thread.day2.runnableinterface;

/// Print ODD numbers 
class OddNumbers extends Thread {

	@Override
	public void run() {

		for (int i = 0; i <= 20; i++) {

			if (i % 2 != 0) {

				if (i == 9) {
					System.out.println("Going to sleeping state......" + Thread.currentThread().getName());
					try {
						Thread.currentThread().sleep(5000);

					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					System.out.println(
							"Wating time is over is now in runnable state " + Thread.currentThread().getName());

				}

				System.out.println("Numbers are: " + i + " exceuted by: " + Thread.currentThread().getName());

			}
		}

	}
}

	class EvenNumbers extends Thread {
	
		@Override
		public void run() {
		 // Print even numbers after all odd numbers
	    for (int i = 0; i <= 20; i++) {

	        if (i % 2 == 0) {
	        	
	        	if (i == 10) {
					System.out.println("Going to sleeping state......" + Thread.currentThread().getName());
					try {
						Thread.currentThread().sleep(5000);

					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					System.out.println(
							"Wating time is over is now in runnable state " + Thread.currentThread().getName());

				}

	        	
	            System.out.println(
	                "Number: " + i +
	                " executed by: " +
	                Thread.currentThread().getName()
	            );
	        }
	    }
	}
		
	}

public class PrintNumbers {

	public static void main(String[] args) {

		OddNumbers t1 = new OddNumbers();
		EvenNumbers t2 = new EvenNumbers();

		t1.setName(" Odd-Number");
		t2.setName(" Even-Number");
		
		t1.start();
		t2.start();

	}

}
