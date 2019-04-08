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
		
		// if this is the first process created
		// read the commands from the "commands.txt"
		if(commands == null)
			readCommands();
		
		// the "runtime" of the first API call
		Random rand = new Random();
		int waitTime;
		waitTime = rand.nextInt(999)+1;
		
		// initial values
		ArriveTime = a; // the starting time of this process
		BurstTime = b; // the running (for how long this process would live)
		FinishTime = a+b; // the time that this process would end
		ReadyForNextTime = r + waitTime;  // the time that this process would be ready to run the next command 
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
		Random rand = new Random();
		int waitTime;
		
		// prints the starting clock time of this process
		int clock = Schduler.clock;
		System.out.println(String.format("Clock: %d, Process %s, Started.", clock,processName));
		Schduler.output += String.format("Clock: %d, Process %s, Started.", clock,processName)+"\r\n";
		
		// run till the clock reaches the finish time
		while(FinishTime > Schduler.clock) {
			// runs when there is a command to run
			if(cCommand != null) {
				
				// the waiting time for the next command
				waitTime = rand.nextInt(999)+1;
				
				// running the command
				// check out what command it is and call the VMM accordingly
				switch (cCommand.ServiceName) {
				case "Store":
					// API call
					VMM.Store(cCommand.ID, cCommand.Value);
					// print to console and store to output string
					System.out.println (String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,cCommand.Value));
					Schduler.output += String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,cCommand.Value) +"\r\n";
					
					break;
					
					
				case "Release":
					// API call
					boolean success = VMM.Release(cCommand.ID);
					// print to console and store to output string
					// according to if the VMM found the variable or not 
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
					// API call and return the lookup result 
					System.out.println (String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,VMM.Lookup(cCommand.ID)));
					Schduler.output += String.format("Clock: %d, Process %s, %s: Variable %s, Value: %d",
							Schduler.clock, processName, cCommand.ServiceName,cCommand.ID,VMM.Lookup(cCommand.ID)) +"\r\n";
					
					break;
					
				}
				
				// cleans the cCommand for the next run
				cCommand = null;
				ReadyForNextTime = Schduler.clock+waitTime;

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
		
		// prints the finishing clock time of this process
		int clock2 = Schduler.clock;
		System.out.println(String.format("Clock: %d, Process %s, Finished.", clock2,processName));
		Schduler.output += String.format("Clock: %d, Process %s, Finished.", clock2,processName) +"\r\n";
	}

	
	public void getNextCommand() {
		// fetch the next command as long as we haven't run out of them
		if(commands.isEmpty()) {
			cCommand = null;
			return;
		}
		
		// just to prevent the scheduler to read the old ReadyForNextTime
		// and assigns the process a new command
		// this not the real wait time 
		ReadyForNextTime += 1000;
		
		// fetch the next command and removing them from the queue
		cCommand = commands.remove();
	}
	
	private static void readCommands() {
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
