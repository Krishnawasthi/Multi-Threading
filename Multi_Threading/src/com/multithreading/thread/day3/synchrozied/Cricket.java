package com.multithreading.thread.day3.synchrozied;


class OversCount
{
	public synchronized void overThrown(boolean even) {
		
	for( int overs = 0; overs <= 10; overs++ ) {
		
		if(even && overs % 2 == 0) {
			
			System.out.println("Counting Even Overs: "+ overs +  "  " +  Thread.currentThread().getName());
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(!even && overs % 2 !=0) {
			
			System.out.println("Counting Odd Overs: "+ overs +  "  " +  Thread.currentThread().getName());
			try {
				Thread.currentThread().sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
		
		
	     }
	}


class OversThread extends Thread{
	
	OversCount over;
	boolean even;
	
	public  OversThread(OversCount over, boolean even ) {
		
		this.over = over;
		this.even = even;
		
	}
	@Override
	public void run() {
		
		over.overThrown(even);
		
	}
		
}

public class Cricket {

	public static void main(String[] args)
	{
		OversCount over = new OversCount();
		
		OversThread thread1 = new OversThread(over, false);
	    thread1.setName("CountedByUmpire1");
	    thread1.start();
		
		
		OversThread thread2 = new OversThread(over, true);
		thread2.setName("CountedByUmpire2");
	    thread2.start();
		
	}

}
