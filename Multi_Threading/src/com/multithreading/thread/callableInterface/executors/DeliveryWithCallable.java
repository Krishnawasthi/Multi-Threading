package com.multithreading.thread.callableInterface.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class NotifyMeDelivery implements Callable<String>{   //it returns the response after executing thread
	
	public String call(){
		
		System.out.println("NotifyDelivery.call()......Start "+ Thread.currentThread().getName());
		ConfirmDelivery cnf = new ConfirmDelivery();
		cnf.confirmDelivery();
		System.out.println("NotifyDelivery.call()......Start "+ Thread.currentThread().getName());
		return "SUCCESS";
		
		
	}
	
	
	
}
public class DeliveryWithCallable {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		
		ExecutorService es = Executors.newSingleThreadExecutor();
		
		for(int i = 0; i<=10; i++) {
		NotifyMeDelivery task = new NotifyMeDelivery();
		Future<String> response = es.submit(task);  //future is used to store the result after getting execured by callable interface inside the call method 
		System.out.println(response.get());
		System.out.println();
		
		}
		
		

	}

}
