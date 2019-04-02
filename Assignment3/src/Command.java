
public class Command {
	
	public String ServiceName;
	public String PAR1;
	public int PAR2;
	
	public void print() {
		System.out.print(String.format(ServiceName + " %d %d", PAR1, PAR2) );
	}
	
	public Command() {
		ServiceName = "";
		PAR1 = "";
		PAR2 = 0;
	}
	
	public void setServiceName(String name) {ServiceName = name;}
	public void setPAR1(String par1) {PAR1 = par1;}
	public void setPAR2(int par2) {PAR2 = par2;}
	
}
