package com.multithreading.thread.day2.waitjoinyeild;

class Task {
	
	

	public synchronized void printEvenNumber() {

		
		for (int i = 0; i <= 6; i++) {
			
			if (i % 2 == 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(" Even Numbers: " + i + " " + Thread.currentThread().getName());

			}
		}

	}

	public synchronized void printOddNumber() {

		for (int i = 0; i <= 6; i++) {
             
			if (i % 2 != 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(" Odd Numbers: " + i + " " + Thread.currentThread().getName());

			}
		}

	}
}

class EvenThread extends Thread {
	Task task;

	public EvenThread(Task task) {

		this.task = task;
	}

	@Override
	public void run() {

		task.printEvenNumber();

	}

}
class OddThread extends Thread {
	Task task;

	public OddThread(Task task) {

		this.task = task;
	}

	@Override
	public void run() {

		task.printOddNumber();

	}

}



public class PrintNumbers {

	public static void main(String[] args) {

		Task task = new Task();

		OddThread oddThread = new OddThread(task);
		oddThread.start();

		EvenThread evenThread = new EvenThread(task);

		evenThread.start();

	}

}
