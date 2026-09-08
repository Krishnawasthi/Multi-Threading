package com.multithreading.thread.day3.synchrozied;

class BankAccount {

	private int balance = 1000;

	public synchronized void withdraw(int amount) {
		if (balance >= amount) {

			System.out.println(Thread.currentThread().getName() + " is Withdrawing: " + amount);

			// delay
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			balance = balance - amount;
			System.out.println(Thread.currentThread().getName() + " Completed Withdrawal Remaining Balance: " + balance);

		}

		else {

			System.out.println("Hey "+Thread.currentThread().getName() + " Insufficient Balance");
		}

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
		
		account.withdraw(amount);
		
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
