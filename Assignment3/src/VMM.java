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


static public void main(String[] args) {
	System.out.println("wifwaebvisbdvhbasoduchusdhvkudvkuawvduqw");
	
	readSize();
	
	Store("vef",19);
	Store("vaev",12);
	Store("asfb",1459);
	Store("vefnhd",456);
	Release("vef");
	Release("asfb");
	
	
	for (int i=0;i<PageSize;i++) {
		System.out.println(String.format("%s: %d - %b", VariableMemory[i],ValueMemory[i],empty[i]));
	}
	
}


public VMM() {
	readSize();
}
static void readSize() {
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
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
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
				e.printStackTrace();
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
			e.printStackTrace();
		}
		
}
public static int Lookup(String id) {
	boolean inMemory = false;
	for(int i=0;i<PageSize;i++) {
		if(VariableMemory[i].equals(id)) {inMemory = true;}
	}
	if(inMemory)

}

public static  int checkFreeSpace(){
		for(int i =0 ;i<PageSize;i++) {
			if(empty[i])return i;
		}
		return -1;
}

}
