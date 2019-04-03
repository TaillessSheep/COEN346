import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;

public class Main {
	
	public static Process[] processes = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Hi World!");
		
		readCommands();
		
		readProcesses();
		
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

            // count how many commands in total
            
            while((line = bufferedReader.readLine()) != null) {size++;}
            
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
            Process.commands = new Command[size];
//            Process process = new Process();
            while((line = bufferedReader.readLine()) != null) {
            	splited = line.split(" ");
            	
            	name = splited[0];
            	ID = splited[1];
            	var = 0;
            	
            	if (splited[0].equals("Store")) {
            		var = Integer.parseInt(splited[2]);
            	}
            	Process.commands[++index] = new Command(name,ID,var);
//            	Process.setCommands(index, splited[0], ID, var);
            	
            	
            	Process.commands[index].print();
            	
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
        int size = 0;
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
            size = Integer.parseInt(line);
            
            processes = new Process[size];
            while((line = bufferedReader.readLine()) != null) {
            	splited = line.split(" ");
            	
            	temp1 = Integer.parseInt(splited[0]);
            	temp2 = Integer.parseInt(splited[1]);
            	
            	processes[++index] = new Process(temp1, temp2, temp2);
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
