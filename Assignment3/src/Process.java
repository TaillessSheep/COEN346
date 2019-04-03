import java.util.LinkedList; 
import java.util.Queue;

public class Process implements Runnable{
//	public static Command commands[];
	public static Queue<Command> commands = new LinkedList<>(); 
	
	private int ArriveTime;
	private int BurstTime;
	private int ReadyForNextTime;
	
	
	
	public Process(int a,int b,int r) {
		// TODO Auto-generated constructor stub
		ArriveTime = a;
		BurstTime = b;
		ReadyForNextTime = r;
	}
	
	public void print() {
		System.out.println(String.format("%d %d", ArriveTime, BurstTime));
	}
	
	
	
	public void setAT(int AT) {this.ArriveTime = AT;}
	public int getAT() {return ArriveTime;}
	
	public void setBT(int BT) {this.BurstTime = BT;}
	public int getBT() {return BurstTime;}
	

	
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
