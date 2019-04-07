import java.util.LinkedList; 
import java.util.Queue;
import java.util.Random;
import java.io.*;

public class Process implements Runnable{
//	public static Command commands[];
	public static Queue<Command> commands = new LinkedList<>(); 
	
	private int ArriveTime;
	private int BurstTime;
	private int FinishTime;
	private int ReadyForNextTime;
	
	private Command cCommand;
	
	private String processName;
	
	
	
	public Process(int a,int b,int r,String name) {
		Random rand = new Random();
		int waitTime;
		waitTime = rand.nextInt(1000);
		// TODO Auto-generated constructor stub
		ArriveTime = a;
		BurstTime = b;
		FinishTime = a+b;
		ReadyForNextTime = r + waitTime;
		processName = name;
	}
	
	public void print() {
		System.out.println(String.format("%d %d", ArriveTime, BurstTime));
	}
	
	public int getAT() {return ArriveTime;}
	
	public int getBT() {return BurstTime;}
	
	public int getFT() {return FinishTime;}
	
	public int getReadyForNextTime() {
		return ReadyForNextTime;
	}


	@Override
	public void run() {
		
//		getNextCommand();
		Random rand = new Random();
		int waitTime;
		
		while(ArriveTime>Schduler.clock) {try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
		System.out.println(String.format("Clock: %d, Process %s, Started.", Schduler.clock,processName));
		
		while(FinishTime > Schduler.clock) {
			if(cCommand != null) {
				
				// wait till getting a command
				waitTime = rand.nextInt(1000);
				
				System.out.print (String.format("Clock: %d, Process %s, ",Schduler.clock, processName));
				cCommand.print();
	//			System.out.println (String.format("%d",waitTime));
				cCommand = null;
				ReadyForNextTime = Schduler.clock+waitTime;
				
	//			try {
	//				Thread.sleep(waitTime);
	//			} catch (InterruptedException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			getNextCommand();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println(String.format("Clock: %d, Process %s, Finished.", Schduler.clock,processName));
		
		
	}
	
	public void getNextCommand() {
		if(commands.isEmpty()) {
			cCommand = null;
			return;
		}
		
		ReadyForNextTime += 1000;
		cCommand = commands.remove();
	}
	


	
}
