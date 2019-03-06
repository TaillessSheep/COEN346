

import java.util.Date;

public class Processor implements Runnable{

	public String threadName;
	
	private long startTime;
	private int ranTime;
	
	private Scheduler scheduler;
	private Thread schedulerThread;
	
	//flags
	private boolean running;
	private boolean loaded;
	private boolean readyToRun;
	
	private boolean done;
	private Process cProcess; // currently running process
//	String[] info; // info from 
	
	
	//constructor for initialization
	public Processor(String tName) {
		threadName = tName;
		done = false;
		cProcess = null;
		loaded = false;
		running = false;
		readyToRun = false;
	}
	
	// load the next process to run
	public void loadProcess(Process process) {
		if(! running) {
			this.cProcess = process;
			loaded = true;
//			System.out.println("New process loaded");
		}
		else {
//			System.out.println("Attempt to load process while processor running");
		}
	}
	
	// return the process to save it
	public Process getProcess() {
		return cProcess;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	
	public boolean isReadyToRun() {
		return readyToRun;
	}
	
	public void setScheduler(Scheduler scheduler,Thread schedulerThread) {
		this.scheduler = scheduler;
		this.schedulerThread = schedulerThread;
	}
	

	@Override
	public void run() {
		
//		System.out.println("Starting processer");
		while(true) {
			// reset the flag
			loaded = false;
			running = false;
			readyToRun = false;
			
//			System.out.println("Start of a cycle");
			// wait for the next process to be loaded
			if(allDone()) {
//				System.out.println("All processes done, terminating processor");
				break;
			}
			else {
//				System.out.println("Not done yet");
			}
			
			waitForStart();
			
			// run the process
			task();
//			System.out.println("heh");
		}
	}

	
	private void task() {
		running = true;
		
//		System.out.println("Starting");
//		
//		if (cProcess.getRemainingTime()==cProcess.getServiceTime()) {
//			System.out.println();
//		}
		
		startTime = new Date().getTime();
		
		long endTime;
		try {
			do {
				endTime = new Date().getTime();
				ranTime = (int)((endTime - startTime)/1000);
				Thread.sleep(10);
			}while(ranTime < cProcess.getRemainingTime());
			
			schedulerThread.interrupt();
//			System.out.println("Done with this process");
		}
		catch(InterruptedException e) {
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
//			System.out.println(String.format("Processor: %d", ranTime));
			cProcess.setRemainingTime(cProcess.getRemainingTime()-ranTime);
			running = false;
		}
		
	}
	
	// to prevent running without loading a process 
	// or running wrong process
	private boolean allDone(){
//		System.out.println("allDone() called");
		while(!loaded) {
			try {
				Thread.sleep(20);
//				System.out.println("Waiting for data loading");
			} catch (InterruptedException e) {
				if(!loaded)
					return true;
				else
					break;
			}
		}
		
		// set ready flag to true
		readyToRun = true;
		return false;
	}
	private void waitForStart() {
		while(true) {
				try {
//					System.out.print(readyToRun);
//					System.out.println("Waiting for start");
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// resume the processor
					break;
				}
		}
	}
}





