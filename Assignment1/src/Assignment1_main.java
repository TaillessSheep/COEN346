import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Assignment1_main {

	public static void main(String[] args) {
		
		String if_name = "Input.txt";
		String of_name = "Output.txt";
		
		// This will reference one line at a time
		Queue<String> raw = new LinkedList<>();
        String line = null;

        
// reading data
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(if_name);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
                raw.add(line);
            }   

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
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        
// pre-processing data
// converting the string data to int data
        int size = raw.size();
        int[] data = new int[size];
        for (int i=0; i < size;i++) {
        	data[i] = Integer.parseInt((String) raw.remove());
//        	System.out.println(data[i]);
        }
		
// quick sort		
		Thread t1 = new Thread(new tester("herbcvb"), "t1");
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("haha");

		
		/*
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
//            bufferedWriter.write("Hello there,");
//            bufferedWriter.write(" here is some text.\n");
//            bufferedWriter.newLine();
//            bufferedWriter.write("We are writing");
//            bufferedWriter.write(" the text to the file.");

            // Always close files
            bufferedWriter.close();
            fileWriter.close();
        }catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

}


class tester  implements Runnable{

	String msg;
	public tester(String a) {msg = a;}
	
	@Override
	public void run() {
		System.out.println("Thread started:::"+Thread.currentThread().getName()+msg);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Thread dead:::"+Thread.currentThread().getName());

	}

}


class QuickSort implements Runnable{

	@Override
	public void run() {
		
	}
}
