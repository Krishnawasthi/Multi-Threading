package com.multithreading.thread.day2.waitjoinyeild;

class Cooking extends Thread{
	
	@Override
	public void run() {
		
		System.out.println();
		
	}
	
}

public class Hotel {

	public static void main(String[] args) {
		
		Thread.currentThread().setName("Waiter");
		System.out.println("Waiter will took the Order..."+ Thread.currentThread().getName());
		
		 Cooking t1 = new Cooking();
		 t1.setName("Cook");
		 
		 System.out.println("Waiter served the Order..."+ Thread.currentThread().getName());
		 

	}

}
