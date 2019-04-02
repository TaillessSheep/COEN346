import java.util.LinkedList; 
import java.util.Queue;

public class Process implements Runnable{

	private int ArriveTime;
	private int BurstTime;
	
	
	
	public void setAT(int AT) {this.ArriveTime = AT;}
	public int getAT() {return ArriveTime;}
	
	public void setBT(int BT) {this.BurstTime = BT;}
	public int getBT() {return BurstTime;}
	
	public static Command commands[];
	
	void readCommands() {}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
//	public static void defineSize(int size) {commands = new Command[size];}
//	public static void setCommands(int index,String name, String ID,int var) {
////		System.out.println(commands.length);
//		commands[index].setServiceName(name); 
//		commands[index].setPAR1(ID);
//		commands[index].setPAR2(var);
//	}
//	
	
}
