import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class HelloWorld {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("Hi World!");
		
		
		
		
		
		
		
	}
	
	
	private static void readCommands() {
		final String commendFileName = "commands.txt";
		
		String line = null;
		FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        
        
        try{
            // FileReader reads text files in the default encoding.
            fileReader = new FileReader(commendFileName);

            // Always wrap FileReader in BufferedReader.
            bufferedReader = new BufferedReader(fileReader);

            int size = 0;
            // read the quantum
//            line = bufferedReader.readLine();
            while((line = bufferedReader.readLine()) != null) {size++;}
            bufferedReader.reset();
            
            
            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                commendFileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + commendFileName + "'");                  
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
	}
	

}
