
public class Command {
	
	public String ServiceName;
	public String ID;
	public int Value;
	
	public void print() {
		if(ID.equals("Store"))
			System.out.println(String.format( "%s %s %d",ServiceName, ID, Value) );
		else
			System.out.println(String.format( "%s %s",ServiceName, ID) );
	}
	
	public Command(String name, String ID, int Value) {
		this.ServiceName = name;
		this.ID = ID;
		this.Value = Value;
	}
	
//	public void setServiceName(String name) {ServiceName = name;}
//	public void setPAR1(String par1) {PAR1 = par1;}
//	public void setPAR2(int par2) {PAR2 = par2;}
	
}
