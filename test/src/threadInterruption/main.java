package threadInterruption;

import java.util.Timer;
import java.util.Date;;

public class main {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {

		long startTime = new Date().getTime();
		
		Processor processer = new Processor("myProcesser");
		Thread t = new Thread(processer);
		t.getName();
//		map Thread.getAllStackTraces();
		t.start();
		
		for (long i=0;i<999999999;i++) {}

		t.interrupt();
		
		long startTime2 = new Date().getTime();
		for (long i=0;i<999;i++) {try {
			Thread.sleep(6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

		processer.setI(500);
		long endTime2 = new Date().getTime();

		long timeElapsed2 = endTime2 - startTime2;
		System.out.println("Interrupted time in milliseconds: " + timeElapsed2);
//		(t.getClass()).setI();
		t.interrupt();		

		
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long endTime = new Date().getTime();

		long timeElapsed = endTime - startTime;

		
		System.out.println("Total execution time in milliseconds: " + timeElapsed);
	}

	private static void getAllStackTraces() {
		// TODO Auto-generated method stub
		
	}

}
