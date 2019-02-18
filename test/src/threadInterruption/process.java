package threadInterruption;

import java.util.Date;

public class process implements Runnable{

	long startTime;
	boolean firstRun;
	boolean done;
	long i;
	public String threadName;

	public process(String tName) {
		threadName = tName;
		firstRun = true;
		done = false;
		i= 0;
	}
	
	public void setI(long i) {
		this.i = i;
	}

	@Override
	public void run() {
		while(true) {
			task();
			if(done) break;
			waiting();
		}
	}

	
	private void task() {
		if(firstRun) {
			startTime = new Date().getTime();
			firstRun = false;
		}
		try {
			System.out.println(i);
			for (;i<4000;i++) {Thread.sleep(1);}


			long endTime = new Date().getTime();

			long timeElapsed = endTime - startTime;

			System.out.println("Execution time in milliseconds: " + timeElapsed);
			done = true;
		}
		catch(Exception e) {
			System.out.println("Interrupted: i = " + i);
		}
	}
	
	private void waiting(){
		while(true) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// resume the task
					System.out.println("Resuing");
					break;
				}
		}
	}
}





