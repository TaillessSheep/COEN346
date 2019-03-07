import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class CPU {
	
	private static int quantum;
	private static int userCount;
	private static LinkedList<String> userNames = new LinkedList<String>();
	private static LinkedList<Process[]> userProcesses = new LinkedList<>();

	public static void main(String[] args) throws Exception {
		String if_name = "input.txt";
		String of_name = "output.txt";
		// reading user process
		//System.out.println("heh?");
		readUserProcesses("input.txt");
		
		// display test
		Process temp[] = null;
		for(int i=0;i<userCount;i++) {
			System.out.println(userNames.get(i)+":");
			temp = userProcesses.get(i);
			for(int j=0;j<temp.length;j++) {temp[j].print();}
		}
		
		System.out.println("\n");
		
		// create two threads:
		Processor processor = new Processor("MyProcess");
		Scheduler scheduler = new Scheduler(quantum,userNames,userProcesses);
		Thread processorThread = new Thread(processor);		
		Thread schedulerThread = new Thread(scheduler);
		
		processor.setScheduler(scheduler, schedulerThread);
		scheduler.setProcessor(processor, processorThread);
		
		processorThread.start();
		schedulerThread.start();
		
		schedulerThread.join();
		System.out.println("heh?1");
		processorThread.join();
		System.out.println("heh?2");
		
		System.out.println("heh?3");
		String data = scheduler.getOutputData();
		
		writeFile(of_name, data);
		
		
		
		
		
//		processorThread.interrupt();
		
//		System.out.println(schedulerThread.isAlive());
//		System.out.println(processorThread.isAlive());
		
		
	}
	
	
	
	// writing data	
	private static void writeFile(String of_name, String data) {

		System.out.println("Writing data");
		// writing result
		try {
			// Assume default encoding.
			FileWriter fileWriter =
					new FileWriter(of_name);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter =
					new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			//		            bufferedWriter.write("Hello there,");
			//		            bufferedWriter.write(" here is some text.\n");
			//		            bufferedWriter.newLine();
			//		            bufferedWriter.write("We are writing");
			bufferedWriter.write(data);

			// Always close files
			bufferedWriter.close();
			fileWriter.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// reading data
	private static void readUserProcesses(String if_name) throws Exception {
		
		String line = null;
        String splited[] = new String[2];
        int currentUser_Index = 0;
        int currentUser_ProcessIndex = 0;
        int currentUser_ProcessesCount = 0;
        Process[] currentUser_Processes = null;
        
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        
        try{
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader(if_name);

            // Always wrap FileReader in BufferedReader.
            bufferedReader = new BufferedReader(fileReader);

            // read the quantum
            line = bufferedReader.readLine();
            quantum = Integer.parseInt(line);
            
            // reading the users and processes
            while((line = bufferedReader.readLine()) != null) {
            	// split two column
            	splited = line.split(" ");
            	
            	// if this line starts a new user
                if(Character.isAlphabetic(splited[0].charAt(0))) {
                	if(currentUser_ProcessIndex != currentUser_ProcessesCount) {
                		throw new Exception(String.format("In \"input.txt\": Amount of processes indicated"
                				+ " (%d) for user %s does not match the amount provided (%d)", currentUser_ProcessesCount,
                				userNames.get(currentUser_Index),currentUser_ProcessIndex));
                	}
                	userNames.add(splited[0]); // append the new user name
                	currentUser_ProcessesCount = Integer.parseInt(splited[1]); // how many processes should there be for this user
                	currentUser_Processes = new Process[currentUser_ProcessesCount]; // the array of processes for this user
                	currentUser_ProcessIndex = 0; // reset the index
                }
                // else means this line is a process
                else { 
                	currentUser_Processes[currentUser_ProcessIndex] = new Process();
                	currentUser_Processes[currentUser_ProcessIndex].setReadyTime(Integer.parseInt(splited[0]));
                	currentUser_Processes[currentUser_ProcessIndex].setServiceTime(Integer.parseInt(splited[1]));
                	currentUser_Processes[currentUser_ProcessIndex].setRemainingTime(Integer.parseInt(splited[1]));
                	currentUser_ProcessIndex++;
                }
                
                // if done with the current user, store the processes 
                if(currentUser_ProcessIndex == currentUser_ProcessesCount) {
                	userProcesses.add(currentUser_Processes);
                	currentUser_Index++;
                }
            	
            }   

            // update the amout of total users
            userCount = currentUser_Index;
            
            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                if_name + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + if_name + "'");                  
        }
        finally {
        	if(fileReader!=null) {fileReader.close();}
        	if(bufferedReader!=null) {bufferedReader.close();}
        }
      
        
	}

}
