import java.util.LinkedList; 
import java.util.Queue;

public class Process {

	private int ArriveTime;
	private int BurstTime;
	
	
	
	public void setAT(int AT) {this.ArriveTime = AT;}
	public int getAT() {return ArriveTime;}
	
	public void setBT(int BT) {this.BurstTime = BT;}
	public int getBT() {return BurstTime;}
	
	static Command[] commands = null;
	
	void readCommands() {}
	
	
}
