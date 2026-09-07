package com.multithreading.thread.day2.waitjoinyeild;

class Cooking extends Thread {

	@Override
	public void run() {

		System.out.println("Food is ready to being prepared.....[" + Thread.currentThread().getName() + "]");

		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Food preparation is done.....[" + Thread.currentThread().getName() + "]");

	}

}

public class Hotel {

	public static void main(String[] args) {

		Thread.currentThread().setName("Waiter");
		System.out.println("Waiter will took the Order...[" + Thread.currentThread().getName() + "]");

		Cooking t1 = new Cooking();
		t1.setName("Cook");
		t1.start(); //cooking stared
		
		try {
			t1.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Waiter served the Order...[" + Thread.currentThread().getName() + "]");

	}

}
