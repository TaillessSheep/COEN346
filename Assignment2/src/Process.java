// This Processes class onlycontains all information of a process
// But not how the processes are running 

public class Process {
	
	// member variables
	private int readyTime;
	private int serviceTime;
	private int remainingTime;
	private boolean done;
	
	public Process(){done=false;}
	
	// setters 
	public void setReadyTime(int readTime) {
		this.readyTime = readTime;
	}
	
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	
	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
		// if the remaining time is none, set flag "done" to true
		if(remainingTime<=0) {
			remainingTime = 0;
			done = true;
		}
	}
	
	// getters
	public int getReadyTime() {return readyTime;}
	public int getServiceTime() {return serviceTime;}
	public int getRemainingTime() {return remainingTime;}
	public boolean isDone() {return done;}
	
	// print info of this process
	public void print() {
		System.out.print(String.format("readyTime: %d", readyTime));
		System.out.print(String.format("  serviceTime: %d", serviceTime));
		System.out.println(String.format("  remainingTime: %d", remainingTime));
	}
	
	
	// to tell the process scheduler whether the process is ready
	// given the current time
	public boolean isReady(int curTime) {
		if(curTime>= readyTime) return true;
		else return false;
	}
	
	
}
