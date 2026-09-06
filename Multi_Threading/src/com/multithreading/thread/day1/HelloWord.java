package com.multithreading.thread.day1;

public class HelloWord {

	public static void main(String[] args) {
		
		System.out.println("Who is running this thread: "+Thread.currentThread().getName());
		
		System.out.println("HelloWord.main()");
		
		HelloWord hw = new HelloWord();
		hw.someMethod();

	}
	
	public void someMethod() {
		System.out.println("Who is running this thread: "+Thread.currentThread().getName());
		System.out.println("HelloWord.someMethod()");
	}

}
