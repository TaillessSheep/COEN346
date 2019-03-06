
public class Process {
//	// to test out the class
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		Process p= new Process();
//		System.out.println(p.isReady(1));
//		System.out.println(p.isReady(3));
//		System.out.println(p.run(2));
//	}
	
	// member variables
	
	private int readyTime;
	private int serviceTime;
	private int remainingTime;
	private boolean done;
	
	public Process(){done=false;}
	
	public void setReadyTime(int readTime) {
		this.readyTime = readTime;
	}
	
	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}
	
	public void setRemainingTime(int remainingTime) {
		this.remainingTime = remainingTime;
		if(remainingTime<=0) {
			remainingTime = 0;
			done = true;
		}
	}
	
	public int getReadyTime() {return readyTime;}
	public int getServiceTime() {return serviceTime;}
	public int getRemainingTime() {return remainingTime;}
	public boolean isDone() {return done;}
	
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
	
	// to simulate the running of the process
	// given the time share of this exec
	// return the remainingTime of the process
	public int run(int timeShare) {
		remainingTime -= timeShare;
		return remainingTime;
	}
	
	
	

}
