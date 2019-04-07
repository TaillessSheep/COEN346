import java.io.*;

public class Schduler {
	public static int processSize;
	
	public static Process[] processes = null;
	public static Thread[] processThreads = null;
	public static int clock = 1000;
	
	public static String output = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		System.out.println("Hi World!");
		
		readCommands();
		
		readProcesses();
		
		VMM.readSize();
		
		
//		System.out.println("---------------------------\n");
		for(int i=0;i<processSize;i++) {
			processThreads[i].start();
		}
		
		while (notFinished()) {
			for(int i=0;i<processSize;i++) {
				if(processes[i].getReadyForNextTime() <= clock && processes[i].getFT()>clock) {
					processes[i].getNextCommand();
				}
			}
			
			
			
			// update the clock
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clock++;
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0;i<processSize;i++) {
			try {
				processThreads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		System.out.println("all done");
//		
//		VMM.print();
//		System.out.println("huh");
		
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("output.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(output);
			// Always close files
			bufferedWriter.close();
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getStackTrace()[0]);
			System.out.println(e.getStackTrace()[1]);
		}
	}
	
	
	private static void readCommands() {
//		static String commendFileName = "commands.txt";
		
		String line = null;
		FileReader fileReader = null;
        BufferedReader bufferedReader = null;
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
	
	
	private static void readProcesses() {
		
		String line = null;
		FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        processSize = 0;
        String[] splited;
        int index = -1;
        int temp1, temp2;
		
		try{
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader("processes.txt");

            // Always wrap FileReader in BufferedReader.
            bufferedReader = new BufferedReader(fileReader);

            //read how many processes in total
            line = bufferedReader.readLine();
            processSize= Integer.parseInt(line);
            
            processes = new Process[processSize];
            processThreads = new Thread[processSize];
            
            while((line = bufferedReader.readLine()) != null) {
            	splited = line.split(" ");
            	
            	temp1 = Integer.parseInt(splited[0]);
            	temp2 = Integer.parseInt(splited[1]);
            	
             	processes[++index] = new Process(temp1, temp2, temp1,String.valueOf(index+1));
             	processThreads[index] = new Thread(processes[index],String.valueOf(index+1));
//            	processes[index].print();
            	
            }
            
            
            
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
	
	
	private static boolean notFinished() {
		for (int i = 0;i<processSize;i++) {
			if(processes[i].getFT() >= clock) {
				return true;
			}
		}
		return false;
	}
	
	

}
