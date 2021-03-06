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


//static public void main(String[] args) {
//	System.out.println("wifwaebvisbdvhbasoduchusdhvkudvkuawvduqw");
//	
//	readSize();
//	
//	Store("vef",19);
//	Store("vaev",12);
//	
//	Store("asfb",1459);
//	Store("vefnhd",456);
//	Store("ha", 15);
//	Store("huh", 50);
//	
////	Release("vef");
////	Release("asfb");
//	
//	System.out.println(Lookup("huh"));
//	
//	
//	for (int i=0;i<PageSize;i++) {
//		System.out.println(String.format("%s: %d - %b", VariableMemory[i],ValueMemory[i],empty[i]));
//	}
//	
//}

public static void print() {
	System.out.println("haha");
	for (int i=0;i<PageSize;i++) 
	System.out.println(String.format("%s: %d - %b", VariableMemory[i],ValueMemory[i],empty[i]));

}

public VMM() {
	readSize();
}

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
	
	ValueMemory = new int[PageSize];
	VariableMemory = new String[PageSize];
	LastAccessTime = new int[PageSize];
	empty = new boolean[PageSize];
	for(int i=0;i<PageSize;i++)
		empty[i] = true;
		
}

public static  void Store(String id, int value) {
	int index = checkFreeSpace();
		if (index != -1 ) {
			ValueMemory[index] = value;
			VariableMemory[index] = id;
			LastAccessTime[index] = Schduler.clock;
			empty[index] = false;
		}
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

public  static void Release(String id) {
	
		for (int i = 0; i < PageSize; i++) {
			if (id == VariableMemory[i]) {
				empty[i] = true;
				return;
				}
		}
		String line;
		String[] splited;
		String name;
		int value;
		String tempString="";
		try {
			FileReader fileReader =  new FileReader("vm.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			if(fileReader!=null) {
				while((line = bufferedReader.readLine()) != null) {
	            	splited = line.split(" ");
	            	
	            	name = splited[0];
	            	value = Integer.parseInt(splited[1]);
	            	if(!id.equals(name)) {
	            		tempString += line +"\r\n";
	            	}
				}
				bufferedReader.close();
				fileReader.close();
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
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
	
//	if()
	
	if(foundValue != -1)
		writeVM(tempString);
	
	return foundValue;
	
	

}

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



public static  int checkFreeSpace(){
		for(int i =0 ;i<PageSize;i++) {
			if(empty[i])return i;
		}
		return -1;
}

}
