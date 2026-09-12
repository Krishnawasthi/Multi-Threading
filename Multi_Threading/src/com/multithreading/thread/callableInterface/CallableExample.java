package com.multithreading.thread.callableInterface;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class sendEmail implements Callable<Boolean>{

	//  callable used to return the something from thread inside call method and throws an exception 
	public Boolean call() throws Exception {
		System.out.println("sendEmail.call()..."+ Thread.currentThread().getName());
		return true;
	}
	
	
	
}

public class CallableExample {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//used to create the multiple thread at once
		ExecutorService es = Executors.newFixedThreadPool(1);
		
		sendEmail task = new sendEmail();
		es.submit(task);
		
	//used to store the response from the thread 
	Future<Boolean> response = es.submit(task);
	System.out.println("response from the thread....: "+ response.get());
	}

}
