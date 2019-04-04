import java.util.LinkedList; 
import java.util.Queue;
import java.io.*;

public class Process implements Runnable{
//	public static Command commands[];
	public static Queue<Command> commands = new LinkedList<>(); 
	
	private int ArriveTime;
	private int BurstTime;
	private int ReadyForNextTime;
	
	private Command cCommand;
	
	
	
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
	


	@Override
	public void run() {
		
		getNextCommand();
		
		while(cCommand != null) {
			cCommand.print();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	protected void getNextCommand() {
		if(commands.isEmpty()) {
			cCommand = null;
			return;
		}
		cCommand = commands.remove();
	}
	


	
}
