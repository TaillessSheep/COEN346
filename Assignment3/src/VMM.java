import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class VMM {
	
private int PageSize;
int Memory[] = null;
boolean empty[] = null;

public VMM() {
	readSize();
}
void readSize() {
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
	
	Memory = new int[PageSize];
	empty = new boolean[PageSize];
	for(int i=0;i<PageSize;i++)
		empty[i] = true;
		
}




public void Store(String id, int value) {
		if (checkFreeSpace()!= -1 ) {}
		
	}

public int checkFreeSpace(){
		for(int i =0 ;i<PageSize;i++) {
			if(empty[i])return i;
		}
		return -1;
}

}
