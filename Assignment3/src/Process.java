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
		waitTime = rand.nextInt(999)+1;
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
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		
		int clock = Schduler.clock;
		
		System.out.println(String.format("Clock: %d, Process %s, Started.", clock,processName));
		Schduler.output += String.format("Clock: %d, Process %s, Started.", clock,processName)+"\r\n";
		
		while(FinishTime > Schduler.clock) {
			if(cCommand != null) {
				
				// wait till getting a command
				waitTime = rand.nextInt(999)+1;
				
				
				// running the command
				switch (cCommand.ServiceName) {
				case "Store":
//					System.out.println("store");
					VMM.Store(cCommand.ID, cCommand.Value);
					System.out.println (String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,cCommand.Value));
					Schduler.output += String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,cCommand.Value) +"\r\n";
					
					break;
					
					
				case "Release":
//					System.out.println("release");
					VMM.Release(cCommand.ID);
					System.out.println (String.format("Clock: %d, Process %s, %s: Variable %s",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID));
					Schduler.output += String.format("Clock: %d, Process %s, %s: Variable %s",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID) +"\r\n";
					
					break;
					
				case "Lookup":
//					System.out.println("lookup");
					System.out.println (String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,VMM.Lookup(cCommand.ID)));
					Schduler.output += String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,VMM.Lookup(cCommand.ID)) +"\r\n";
					
					break;
					
				}
				
				
//				System.out.print (String.format("Clock: %d, Process %s, ",Schduler.clock, processName));
//				cCommand.print();

				
				
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
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		int clock2 = Schduler.clock;
		System.out.println(String.format("Clock: %d, Process %s, Finished.", clock2,processName));
		Schduler.output += String.format("Clock: %d, Process %s, Finished.", clock2,processName) +"\r\n";
		
		
	}
	
	public void getNextCommand() {
		if(commands.isEmpty()) {
			cCommand = null;
			return;
		}
		
		ReadyForNextTime += 1000;
		cCommand = commands.remove();
//		System.out.print(processName);
//		cCommand.print();
	}
	


	
}
