
public class fakeProcess {
	// to test out the class
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		fakeProcess p= new fakeProcess(2,6);
		System.out.println(p.isReady(1));
		System.out.println(p.isReady(3));
		System.out.println(p.run(2));
	}
	
	// member variables
	private int readyTime;
	private int serviceTime;
	private int remainingTime;
	
	// constructor
	public fakeProcess(int rt,int st) {
		readyTime = rt;
		serviceTime = st;
		remainingTime = st;
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
