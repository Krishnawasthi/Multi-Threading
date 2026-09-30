package com.multithreading.thread.practice;
class Addition 
{
	
	public int doAdd(int a , int b) {
		
	return a+b;	
		
	}
	
}

class MyThread extends Thread {
	
	Addition addition;
	public  MyThread(Addition addition) {
		super();
		this.addition = addition;
	}
	@Override
	public void run() {
		int result = addition.doAdd(12, 17);
		System.out.println("Addtion of two numbers: "+ result + "[" + Thread.currentThread().getName()+"]");
	}
}
			
		
public class AdditionDriver {

	public static void main(String[] args) {
		
	
	Addition add = new Addition();
	
	 MyThread t1 = new  MyThread(add);
	 t1.start();
	 MyThread t2 = new MyThread(add);
     t2.start();
	 
	
		
	}

}
