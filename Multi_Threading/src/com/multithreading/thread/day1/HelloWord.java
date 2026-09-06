package com.multithreading.thread.day1;

public class HelloWord {

	public static void main(String[] args) {
		
		System.out.println("Who is running this thread: "+Thread.currentThread().getName());
		Thread.currentThread().setName(" My Thread");
		
		System.out.println("Who is running this thread: "+Thread.currentThread().getName());
		System.out.println("HelloWord.main()");

	}

}
