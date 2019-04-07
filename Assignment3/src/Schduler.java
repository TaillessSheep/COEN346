import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

public class Schduler {
	public static int processSize;
	
	public static Process[] processes = null;
	public static Thread[] processThreads = null;
	public static int clock = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Hi World!");
		
		readCommands();
		
		readProcesses();
		
		processThreads[0].start();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		processThreads[1].start();
		try {
			processThreads[0].join();
			processThreads[1].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
//            	Process.setCommands(index, splited[0], ID, var);
            	
            	
//            	Process.commands..print();
            	
            }
            Command temp = null;
            for(int i=0;i<Process.commands.size();i++) {
            	temp = Process.commands.remove();
            	temp.print();
            	Process.commands.add(temp);
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
            	processes[index].print();
            	
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
	

}
