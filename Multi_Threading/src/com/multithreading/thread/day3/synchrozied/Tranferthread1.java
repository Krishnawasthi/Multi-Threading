package com.multithreading.thread.day3.synchrozied;

public class Tranferthread1 extends Thread {
	
	private BankAccount sender;
	private BankAccount reciever;
	
	Tranferthread1(BankAccount sender,  BankAccount reciever){
		
		this.sender = sender;
		this.reciever = reciever;
	}
	
	@Override
	public void run() {
		
		sender.fundTransfer(reciever, 800);
		
	}

}
