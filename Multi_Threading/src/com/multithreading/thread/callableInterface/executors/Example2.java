package com.multithreading.thread.callableInterface.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class MyThread2 implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		
		return 20;
	}
	
	
	
}
public class Example2 {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
	ExecutorService es = Executors.newFixedThreadPool(2);
	 Future<Integer> f = es.submit(new MyThread2());
	 System.out.println(f.get());
	}

}
