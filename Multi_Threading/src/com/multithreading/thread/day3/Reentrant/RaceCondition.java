package com.multithreading.thread.day3.Reentrant;

import java.util.concurrent.locks.ReentrantLock;

class  BankAccount {

	private static int balance = 1000;

	public static void withdraw(int amount) {
		
		System.out.println(Thread.currentThread().getName() + " some other 50 lines code.........");  ///slow down the performance 
		  
	ReentrantLock  reentrantLock = new ReentrantLock();
		
	reentrantLock.lock();
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

			System.out.println("Hey "+Thread.currentThread().getName() + " Insufficient Balance");
	}
		reentrantLock.unlock();;
		
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
		
		BankAccount.withdraw(amount);
		
	}
}

public class RaceCondition {

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
