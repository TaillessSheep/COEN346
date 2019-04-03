import java.util.LinkedList; 
import java.util.Queue;
import java.io.*;

public class Process implements Runnable{

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
	
	public static Command commands[];
	

	void readCommands() {
		
		String fileName = "commands.txt";
		String instructions = null;
		
		try {
			FileReader fileReader =  new FileReader(fileName);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			while((instructions = bufferedReader.readLine()) != null) {
				System.out.println(instructions);
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

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
