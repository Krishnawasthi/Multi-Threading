package com.multithreading.thread.day3.Reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class  BankAccount {

	private  int balance = 1000;
	ReentrantLock  reentrantLock = new ReentrantLock();   // it implements the manual locking 
	public void withdraw(int amount) throws InterruptedException {
		
		System.out.println(Thread.currentThread().getName() + " some other 50 lines code.........");  ///slow down the performance 
		  
	reentrantLock.tryLock(2000, TimeUnit.MILLISECONDS);
	
		if (balance >= amount) {

			System.out.println(Thread.currentThread().getName() + " is Withdrawing: " + amount);

			// delay
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
			      e.printStackTrace();
			}

			balance = balance - amount;
			System.out.println(Thread.currentThread().getName() + " Completed Withdrawal Remaining Balance: " + balance);

		}

		else {

			System.out.println("Hey! "+Thread.currentThread().getName() + " You have Insufficient Balance");
	}
	  reentrantLock.unlock();
		
}
	

	public int getBalance() {

		return balance;

	}

}

class Customer extends Thread
{
	private BankAccount account;
	int amount;

	public Customer(BankAccount account, String name, int amount) {
		super(name);
		this.account = account;
		this.amount = amount;
	}
	
	public void run(){
		
		try {
			account.withdraw(amount);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

public class RaceConditionDemo {

	public static void main(String[] args) throws Exception {
		BankAccount account = new BankAccount();
		System.out.println("Initial Balance: "+ account.getBalance());
		
		
		Customer t1 = new Customer(account, "Krishna", 700 );
		Customer t2 = new Customer(account, "Rohan", 800);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("Final Balance: "+ account.getBalance());
		
		
	}
	}
