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
static boolean empty[] = null;


static public void main(String[] args) {
	System.out.println("wifwaebvisbdvhbasoduchusdhvkudvkuawvduqw");
	
	readSize();
	
	Store("vef",19);
	Store("vaev",12);
	Store("asfb",1459);
	Store("vefnhd",456);
	
	
	for (int i=0;i<PageSize;i++) {
		System.out.println(String.format("%s: %d", VariableMemory[i],ValueMemory[i]));
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

public static  int checkFreeSpace(){
		for(int i =0 ;i<PageSize;i++) {
			if(empty[i])return i;
		}
		return -1;
}

}
