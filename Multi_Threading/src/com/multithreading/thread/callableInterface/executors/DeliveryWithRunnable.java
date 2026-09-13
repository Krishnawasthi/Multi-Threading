package com.multithreading.thread.callableInterface.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class NotifyDelivery implements Runnable{
	
	@Override
	public void run() {
		
		System.out.println("NotifyDelivery.run().....START"+Thread.currentThread().getName());
		ConfirmDelivery cnf = new ConfirmDelivery();
		cnf.confirmDelivery();
		
		System.out.println("NotifyDelivery.run()....END "+Thread.currentThread().getName());
	
		System.out.println();
		
		
	}
	
}

public class DeliveryWithRunnable {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
	
		
		//introduced in java 1.5 
		//used to create number of threads, helps in to reuse
   ExecutorService es = Executors.newSingleThreadExecutor(); // thread will be decided by the exe Framework		

   for( int i = 0; i<10; i++) {
   NotifyDelivery task = new NotifyDelivery();
   
       es.execute(task);// this is only for runnlabe it dones returned anything it doesn't used for runnable speacial method
  
  
   }
  
   
   
	}

}
