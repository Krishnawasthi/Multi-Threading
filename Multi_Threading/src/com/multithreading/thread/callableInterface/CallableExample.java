package com.multithreading.thread.callableInterface;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class sendEmail implements Callable<String>{

	//  callable used to return the something from thread inside call method and throws an exception 
	public String call() throws Exception {
		System.out.println(Thread.currentThread().getName());
		return "Failed....";
	}
	
	
	
}

public class CallableExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//used to create the multiple thread at once
		ExecutorService es = Executors.newFixedThreadPool(5);
		
		sendEmail task = new sendEmail();
		
		
		
	//used to store the response from the thread 
    for(int i = 0; i<5 ; i++) {
	Future<String> response = es.submit(task);

	System.out.println("response from the call() method is: "+ response.get());
	
	}
    es.shutdown();
    
	}
  
}
