package com.multithreading.thread.day3.synchrozied;

public class BankAccount {
	
	private int balance = 1000;
	
	public void fundTransfer(BankAccount reciever, int amount) {
	
		System.out.println("["+ Thread.currentThread().getName()+"] start BankAccount.fundTransfer()....20lines of code");
		
		synchronized (this) {  //Synchronize block always better than synchronized method they can not touch the criticle code
			
		
		if(balance >= amount) {
			
			System.out.println("["+ Thread.currentThread().getName()+"] Checked balance: "+ balance);
			
		    try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			balance = balance - amount;
			reciever.balance = reciever.balance + amount;
			System.out.println("["+Thread.currentThread().getName() + "] transferred: " + amount);
			
		}
		else {
			
			System.out.println("["+Thread.currentThread().getName() + "] Inuffucient balance: " + balance);
		}
		
		}
		
		System.out.println("["+ Thread.currentThread().getName()+"] END BankAccount.fundTransfer()....20lines of code");
		
		}
		

	
	
	public int getBalance() {
		
		return balance;
	}

}
