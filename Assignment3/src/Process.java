import java.util.LinkedList; 
import java.util.Queue;
import java.util.Random;
import java.io.*;

public class Process implements Runnable{
//	public static Command commands[];
	public static Queue<Command> commands =null; // = new LinkedList<>(); 
	
	private int ArriveTime;
	private int BurstTime;
	private int FinishTime;
	private int ReadyForNextTime;
	
	private Command cCommand;
	
	private String processName;
	
	
	
	public Process(int a,int b,int r,String name) {
		
		if(commands == null)
			readCommands();
		
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
					boolean success = VMM.Release(cCommand.ID);
					if (success) {
						System.out.println (String.format("Clock: %d, Process %s, %s: Variable %s Successed",
								Schduler.clock, processName, cCommand.ServiceName,cCommand.ID));
						Schduler.output += String.format("Clock: %d, Process %s, %s: Variable %s Successed",
								Schduler.clock, processName, cCommand.ServiceName,cCommand.ID) +"\r\n";
					}
					else {
						System.out.println (String.format("Clock: %d, Process %s, %s: Variable %s Failed",
								Schduler.clock, processName, cCommand.ServiceName,cCommand.ID));
						Schduler.output += String.format("Clock: %d, Process %s, %s: Variable %s Failed",
								Schduler.clock, processName, cCommand.ServiceName,cCommand.ID) +"\r\n";
					}
					
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
	
	private static void readCommands() {
//		static String commendFileName = "commands.txt";
		
		String line = null;
		FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        commands = new LinkedList<>(); 
        int size = 0;
        
        
        try{
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader("commands.txt");

            // Always wrap FileReader in BufferedReader.
            bufferedReader = new BufferedReader(fileReader);

            String[] splited;
            
            // read and store the commands to processes
            int index = -1;
            String name;
            String ID;
            int var;
            String keyWord = "Store";
//            Process.commands = new Command[size];
//            Process process = new Process();
            while((line = bufferedReader.readLine()) != null) {
            	splited = line.split(" ");
            	
            	name = splited[0];
            	ID = splited[1];
            	var = 0;
            	
            	if (splited[0].equals("Store")) {
            		var = Integer.parseInt(splited[2]);
            	}
            	Process.commands.add(new Command(name,ID,var));
            }
            Command temp = null;
//            for(int i=0;i<Process.commands.size();i++) {
//            	temp = Process.commands.remove();
//            	temp.print();
//            	Process.commands.add(temp);
//            }
            
            
            bufferedReader.close(); 
            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                		"commands.txt" + "'");                
        }
        catch(IOException ex) {
        	System.out.println(ex.getMessage());
            System.out.println(
                "Error reading file '" 
                + "commands.txt" + "'");                  
        }
//        catch(Exception exception) {
//        	System.out.println(exception);
//        }
        finally {
        	try {
	        	if(fileReader!=null) {fileReader.close();}
	        	if(bufferedReader!=null) {bufferedReader.close();}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }

	}


	
}
