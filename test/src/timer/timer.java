package timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class timer {

	private static boolean done;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		done = false;
		long startTime = new Date().getTime();
		
		givenUsingTimer_whenSchedulingTaskOnce_thenCorrect();
		
		while (!done) {try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
		long endTime = new Date().getTime();

		long timeElapsed = endTime - startTime;

		System.out.println("Total execution time in milliseconds: " + timeElapsed);
	}
	
	public static void givenUsingTimer_whenSchedulingTaskOnce_thenCorrect() {
	    TimerTask task = new TimerTask() {
	        public void run() {
	            System.out.println("Task performed on: " + new Date() + "n" +
	              "Thread's name: " + Thread.currentThread().getName());
	            done = true;
	            System.out.println("Set to true");
	        }
	    };
	    Timer timer = new Timer("Timer");
	     
	    long delay = 1000L;
	    timer.schedule(task, delay);
	}

}
