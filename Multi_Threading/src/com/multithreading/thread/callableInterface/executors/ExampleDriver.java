package com.multithreading.thread.callableInterface.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyThread1 implements Callable<Integer>{
     int a;
     int b;
     public MyThread1(int a, int b) {
 		super();
 		this.a = a;
 		this.b = b;
 	}
	
	@Override
	public Integer call() throws Exception {
		int sum = a+b;
		return sum;
	}
	
	
	
}
public class ExampleDriver {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
	    ExecutorService es = Executors.newFixedThreadPool(3);
		
	    Future<Integer> f = es.submit(new MyThread1(10, 20));
	    
	    System.out.println(f.get() +" "+ Thread.currentThread().getName());
       
	    es.shutdown();
	}
	
	 

}
