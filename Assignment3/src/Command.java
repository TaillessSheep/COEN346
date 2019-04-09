
public class Command {
	
	public String ServiceName;
	public String ID;
	public int Value;
	
	//print the commands
	public void print() {
		if(ServiceName.equals("Store"))
			System.out.println(String.format( "%s %s %d",ServiceName, ID, Value) );
		else
			System.out.println(String.format( "%s %s",ServiceName, ID) );
	}
	
	//constructor
	public Command(String name, String ID, int Value) {
		this.ServiceName = name;
		this.ID = ID;
		this.Value = Value;
	}
		
}
