package com.multithreading.thread.callableInterface.executors.emailsender;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Task implements Callable<Boolean>{
	EmailSender emailSender;
	
	
	public Task(EmailSender _emailSender) {
		super();
		this.emailSender = _emailSender;
	}


	@Override
	public Boolean call() throws Exception {
		System.out.println("Executing call()....");
		return emailSender.sendEmail(emailSender.email, emailSender.body);
	}
	
}

public class ThreadDriver {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
	
	ExecutorService es = Executors.newFixedThreadPool(1);
	for(int i = 12; i<19; i++) {
		
		EmailSender sendMail = new EmailSender("kmawasthi"+i, "@gmail.com"+i);

              Task  task = new Task(sendMail);
              
              Future<Boolean> future = es.submit(task);
              System.out.println("Email status is: "+ future.get());
              
	}
	

	}

}
