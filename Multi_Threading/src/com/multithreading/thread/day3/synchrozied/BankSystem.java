package com.multithreading.thread.day3.synchrozied;

public class BankSystem {

	public static void main(String[] args) throws InterruptedException {
		BankAccount bankAcc1 = new BankAccount();  //lock the object if any thread will be inside 
		BankAccount bankAcc2 = new BankAccount();
	
		Tranferthread1 phonePay = new Tranferthread1(bankAcc1,bankAcc2);
		Tranferthread1 UPI = new Tranferthread1(bankAcc1,bankAcc2);
		phonePay.setName("phonePay");
		UPI.setName("UPI");
		Tranferthread2 gPay = new Tranferthread2(bankAcc1,bankAcc2);
		gPay.setName("gPay");
		UPI.start();
		phonePay.start();
	    gPay.start();
	    
	    gPay.join();
	    System.out.println("Bank Account 1: " + bankAcc1.getBalance());
		System.out.println("Bank Account 2: " + bankAcc2.getBalance());
		
		 
	}

}
