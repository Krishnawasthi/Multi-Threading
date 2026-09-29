package com.multithreading.thread.day3.Reentrant2;

import java.util.concurrent.locks.ReentrantLock;

public  class Task {
     int num = 0;
     
     //in case of synchronized the there will be a situation where the thread can go to the dead lock.
     // thats the problem to solve this problem java introduced reentrant lock that is a manual lock.
     
     ReentrantLock lock = new ReentrantLock();
    
	public void doSomething() {
	
		lock.tryLock();
		int i = 0;
	while( i<=10) {
		
		num = i*2;
		i++;
		
		System.out.println("Getting number: "+ num +" with the thread ["+Thread.currentThread().getName()+"]");
		
	}
	//  lock.unlock();
	//what is someone forget to unlock for the there is lock called trylock
			
			
			
		
	}
}
