//This is a class to simulate the running a processes, it communicates
//with Scheduler class to get the 'rules'(scheule) how to run the processes
// and performs running of a pro

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
			
			waitForStart();
			
			// run the process
			task();
		}
	}

	
	private void task() {
		// flag "running" up 
		running = true;
		
		// for time check
		long endTime;
		int temp_ranTime;
		
		// timer started
		startTime = new Date().getTime();
		
		// main body of process running
		try {
			// constantly check for time and see if the process is finished 
			do {
				endTime = new Date().getTime();
				temp_ranTime = (int)((endTime  - startTime)/1000);
				Thread.sleep(10);
			}while(temp_ranTime < cProcess.getRemainingTime());

			// the loop stopped before getting an interrupt
			// means the process finished
			// thus send a interrupt back to scheduler to notify it
			schedulerThread.interrupt();
			ranTime = temp_ranTime;
		}
		catch(InterruptedException e) {
			// no need to do anything
		}
		catch(Exception e) {
			System.out.println(e);
		}
		finally {
			// saving the progress, no matter 
			cProcess.setRemainingTime(cProcess.getRemainingTime()-ranTime);
			running = false;
		}
		
	}
	
	// return true(means all done) if interrupted before loaded
	// return false (means still need to go on) if interrupted after loaded
	private boolean allDone(){
		while(!loaded) {
			try {
				Thread.sleep(20);
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
	
	
	public void setRanTime(int ranTime) {this.ranTime = ranTime;}	
	// wait till the next interrupt to start the process
	private void waitForStart() {
		while(true) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// about to start the process
					break;
				}
		}
	}
}





