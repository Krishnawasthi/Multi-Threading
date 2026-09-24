package com.multithreading.thread.day3.synchrozied;

public class BankAccount {
	
	private int balance = 1000;
	
	public synchronized void fundTransfer(BankAccount reciever, int amount) {
		
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
	
	public int getBalance() {
		
		return balance;
	}

}
