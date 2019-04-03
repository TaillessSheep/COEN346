import java.util.LinkedList; 
import java.util.Queue;
import java.io.*;

public class Process {

	private int ArriveTime;
	private int BurstTime;
	
	
	
	public void setAT(int AT) {this.ArriveTime = AT;}
	public int getAT() {return ArriveTime;}
	
	public void setBT(int BT) {this.BurstTime = BT;}
	public int getBT() {return BurstTime;}
	
	static Command[] commands = null;
	
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
	
	
}
