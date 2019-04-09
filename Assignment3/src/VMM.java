import java.awt.TexturePaint;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VMM {
	
static private int PageSize;
static int ValueMemory[] = null;
static String VariableMemory[] = null;

static int LastAccessTime[]= null;

static boolean empty[] = null;



public static void print() {
	System.out.println("haha");
	for (int i=0;i<PageSize;i++) 
	System.out.println(String.format("%s: %d - %b", VariableMemory[i],ValueMemory[i],empty[i]));

}
//testing method for VMM class stand alone
public VMM() {
	readSize();
}

//To get the size of main memory
public static void readSize() {
	String fileName = "memconfig.txt";
	String size;
	
	try {
		FileReader fileReader =  new FileReader(fileName);
		BufferedReader bufferedReader =  new BufferedReader(fileReader);
		size = bufferedReader.readLine();
		PageSize = Integer.parseInt(size);
		bufferedReader.close();
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
//		System.out.println(e.getMessage());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getStackTrace()[0]);
		System.out.println(e.getStackTrace()[1]);
		
	}
	//initialize the memory
	ValueMemory = new int[PageSize];
	VariableMemory = new String[PageSize];
	LastAccessTime = new int[PageSize];
	empty = new boolean[PageSize];
	for(int i=0;i<PageSize;i++)
		empty[i] = true;
		
}

//Implementation of API services

public static  void Store(String id, int value) {
	//To store a variable to memory
	int index = checkFreeSpace();
		if (index != -1 ) {
			ValueMemory[index] = value;
			VariableMemory[index] = id;
			LastAccessTime[index] = Schduler.clock;
			empty[index] = false;
		}
		//when there is no space in main memory, write it at the end of disk
		else {
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter("vm.txt",true);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				bufferedWriter.write( id + " " + String.valueOf(value)+"\n");
				// Always close files
				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
				System.out.println(e.getStackTrace()[0]);
				System.out.println(e.getStackTrace()[1]);
			}
			
		}
		
	}

public  static boolean Release(String id) {
	// first check if it's in main memory
	boolean found = false;
	
	for (int i = 0; i < PageSize; i++) {
		if (id == VariableMemory[i]) {
			empty[i] = true;
			found = true;
			return found;
			}
	}
	
	//when it's not in memory, check disk and release it
	String line;
	String[] splited;
	String name;
	int value;
	String tempString="";
	try {
		FileReader fileReader =  new FileReader("vm.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		// check each line in "vm.txt"
		if(fileReader!=null) {
			while((line = bufferedReader.readLine()) != null) {
            	splited = line.split(" ");
            	
            	name = splited[0];
            	value = Integer.parseInt(splited[1]);
            	if(!id.equals(name)) {
            		tempString += line +"\r\n";//when this is not the id , write this line back into vm.txt
            	}
            	else {
            		found = true;//find it in disk, not writing it back to vm.txt
            	}
			}
			bufferedReader.close();
			fileReader.close();
		}
		
		
	} catch (Exception e) {
		// TODO: handle exception
	}
	//write back to vm.txt
	writeVM(tempString);
	
	return found;
		
}


public static int Lookup(String id) {
	//to check if it's in Memory
	for(int i=0;i<PageSize;i++) {
		if(VariableMemory[i].equals(id)) {
			LastAccessTime[i] = Schduler.clock;
			return ValueMemory[i];
			}
	}
	
	
	// Check if it's in disk
	String line;
	String[] splited;
	String name;
	int value;
	String tempString="";
	int lineNum =0;
	int freeIndex= checkFreeSpace();
	int foundValue = -1; // the value found in the disk
	
	try {
		FileReader fileReader = new FileReader("vm.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		if(fileReader!=null) {
			while((line = bufferedReader.readLine()) != null) {
				splited = line.split(" ");
//				lineNum++;
				
            	name = splited[0];
            	value = Integer.parseInt(splited[1]);
            	if(!id.equals(name)) {
            		tempString += line +"\r\n";
            	}else {// when we find it in disk, then first
            		
            		if (freeIndex != -1) { //check if there's a free space in Memory, if so we put it in Memory
						ValueMemory[freeIndex] = value;
						VariableMemory[freeIndex] = id;
						LastAccessTime[freeIndex] = Schduler.clock;
						empty[freeIndex] = false;
						
						foundValue = ValueMemory[freeIndex];
						
					}else {//if no free space, we swap with  smallest Last access time item
						int smallestLATIndex = 0;
						for(int i=0; i<PageSize-1;i++) {
							if(LastAccessTime[i] < LastAccessTime[i+1])
								smallestLATIndex = i;
							else
								smallestLATIndex = i+1;
						}
						System.out.println(String.format("Memory Manager, SWAP: Variable %s with Variable %s",
								id,VariableMemory[smallestLATIndex]));
						Schduler.output += String.format("Memory Manager, SWAP: Variable %s with Variable %s",
								id,VariableMemory[smallestLATIndex]) +"\r\n";
						tempString += VariableMemory[smallestLATIndex]+ " " + String.valueOf(ValueMemory[smallestLATIndex])+"\r\n";
						
						
						ValueMemory[smallestLATIndex] = value;
						VariableMemory[smallestLATIndex] = id;
						LastAccessTime[smallestLATIndex] = Schduler.clock;
						empty[smallestLATIndex] = false;
						
						foundValue = ValueMemory[smallestLATIndex];						
					}
            	}
			}
			bufferedReader.close();
			fileReader.close();
			}
	} catch (Exception e) {
		// TODO: handle exception
		System.out.println(e.getStackTrace()[0]);
		System.out.println(e.getStackTrace()[1]);
	}
	
	
	if(foundValue != -1)
		writeVM(tempString);
	
	return foundValue;
	
	

}
//write back to vm.txt
private static void writeVM(String tempString) {
	FileWriter fileWriter;
	try {
		fileWriter = new FileWriter("vm.txt");
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(tempString);
		// Always close files
		bufferedWriter.close();
		fileWriter.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		System.out.println(e.getStackTrace()[0]);
		System.out.println(e.getStackTrace()[1]);
	}
}


// to check if main memory has a free space
public static  int checkFreeSpace(){
		for(int i =0 ;i<PageSize;i++) {
			if(empty[i])return i;
		}
		return -1;
}

}
