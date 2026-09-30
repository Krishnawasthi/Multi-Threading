package com.multithreading.thread.callableInterface.executors.emailsender;

public class EmailSender {

	String email;
	String body;
	public EmailSender(String email, String body) {
		super();
		this.email = email;
		this.body = body;
	}
	
	public boolean sendEmail(String _email, String _body) {
		
		System.out.println("Sending email to "+ _email);
		return true;
		
		
	}
}
