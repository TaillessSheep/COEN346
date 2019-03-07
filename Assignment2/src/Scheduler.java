

import java.util.Timer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;;

public class Scheduler implements Runnable{
	
	private final boolean debug = false;
	
	private Processor processor;
	private Thread processorThread;
	private int quantum;
	private int userCount;
	private LinkedList<String> userNames;
	private LinkedList<Process[]> userProcesses;
	private Integer[] readyUserIndex;
	private LinkedList<Integer[]> readyQueue;
	int readyUserCount;
	// each number represents time share of each processes for the corresponding readyUserName
	private int[] schedule; 
	
	private String outputData;
	
	
//	private final String of_name = "output.txt";
	
//	private long startTimer;
//	private long currentTime
	private int cTime; // current time
	

	public void setProcessor(Processor processor,Thread processorThread) {
		this.processor = processor;
		this.processorThread = processorThread;
	}
	
	
	@Override
	public void run() {
//		processorThread.start();
		while(!processorThread.isAlive()) {}
		// keep running till all processes are done
		// each iteration is one cycle
		Process temp[] = null;
		while(!allProcessesDone()) { 
			// display test
			if(debug) {
				System.out.println("\n-------------------------------------");
				for(int i=0;i<userCount;i++) {
					System.out.println(userNames.get(i)+":");
					temp = userProcesses.get(i);
					for(int j=0;j<temp.length;j++) {temp[j].print();}
				}
				System.out.println("~~~~~~~~~~~~~~~~~~~");
			}
			
			//create the ready queue
			makeReadyQueue();
			//display the ready queue
			if(debug) {
				Integer[] temp_int;
				for(int i=0;i<readyUserIndex.length;i++) {
					System.out.print(userNames.get(readyUserIndex[i]));
					temp_int = readyQueue.get(i);
					for(int j=0;j<temp_int.length;j++) System.out.print(String.format("%d ", temp_int[j]));
				}
				System.out.println("");
			}
			
			//create the schedule
			makeSchedule();
			if(debug) {
				for(int i=0;i<readyUserIndex.length;i++) {
					System.out.println(String.format( userNames.get(readyUserIndex[i])+": %d",schedule[i]));
				}
			}
			runCycle();
		}
		processorThread.interrupt();
	}
	
	public Scheduler(int quantum, LinkedList<String> userNames, LinkedList<Process[]>userProcesse) {
//		processor = new Processor("MyProcessor");
//		processorThread = new Thread(processor); 
		this.quantum = quantum;
		this.userCount = userNames.size();
		this.userNames = userNames;
		this.userProcesses = userProcesse;
		cTime = 1;
		outputData = "";
	}
	
	private void runCycle() {
		int processTimeShare;
		Process[] cUserProcesses; 
		Integer[] cUserReadyProcessesIndex;
		Process cProcess;
		
		String msg_user;
		String msg;
		// run for each ready user
		for(int user=0;user<readyUserCount;user++) {
			// extract the info for this user
			processTimeShare = schedule[user]; // time share for each process
			cUserProcesses = userProcesses.get(readyUserIndex[user]); // all processes of this user
			cUserReadyProcessesIndex = readyQueue.get(user);
			msg_user = "User "+userNames.get(readyUserIndex[user]);
			// run 
			for(int p=0;p<cUserReadyProcessesIndex.length;p++) {
				cProcess = cUserProcesses[cUserReadyProcessesIndex[p]];
				msg = String.format("%s, Process %d", msg_user,cUserReadyProcessesIndex[p]);
				// display the "output.txt" msg
				if(cProcess.getRemainingTime() == cProcess.getServiceTime()) {
					System.out.println(String.format("Time %d, %s, Started", cTime, msg));
					outputData += String.format("Time %d, %s, Started", cTime, msg) + "\r\n";
				}
				
				System.out.println(String.format("Time %d, %s, Resumed", cTime, msg));
				outputData += String.format("Time %d, %s, Resumed", cTime, msg) + "\r\n";
				
				// loading process into processor
				while(processor.isRunning()) {}
				processor.loadProcess(cProcess);
				while(!processor.isReadyToRun()) {
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
//				System.out.println("About to run the process");
				// run the process till time up
				runProcess(processTimeShare);
				
				// save the data back to the memory
				cProcess = processor.getProcess();
				cUserProcesses[cUserReadyProcessesIndex[p]] = cProcess;
				
				
				// display the "output.txt" msg
//				if(debug)
					System.out.println(String.format("Time %d, %s, Paused: %d", cTime, msg,cProcess.getRemainingTime()));
//				else {	
//					System.out.println(String.format("Time %d, %s, Paused", cTime, msg));
//					outputData += String.format("Time %d, %s, Paused", cTime, msg) + "\r\n";
//				}
				if(cProcess.isDone()) {
					System.out.println(String.format("Time %d, %s, Finished", cTime, msg));
					outputData += String.format("Time %d, %s, Finished", cTime, msg) + "\r\n";
				}
			}
			
			userProcesses.set(readyUserIndex[user], cUserProcesses);
//			updateMemory(cUserProcesses,readyUserIndex[user]);
			
		}
//		System.out.println("End of cycle.");
	}

	
	private void runProcess(int timeShare) {
		boolean aheadOfSchedule = false;
		
//		System.out.println(processor.isRunning());
		processorThread.interrupt(); // starts the process
		processor.setRanTime(timeShare);
//		processorThread.
//		while(!processor.isRunning()) {
//			try {
////				System.out.println(processor.isRunning());
////				processorThread.interrupt();
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		System.out.println("process started");
		long startTime = new Date().getTime(); // starts the timer
		long endTime = startTime;
		
		// let the process run till timer reaches the timeShare
		while (endTime - startTime < (long)(timeShare) * 1000) {
			
			// sleep a bit
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) { // finished ahead of schedule
				// TODO Auto-generated catch block
//				e.printStackTrace();
				endTime = new Date().getTime();
				aheadOfSchedule = true;
				break;
			}
			endTime = new Date().getTime();
		}
		
		// stop the processor
		if(!aheadOfSchedule)
			processorThread.interrupt();
		
		// update the cTime
//		System.out.println(String.format("Scheduler: %d", (endTime - startTime)/1000));
		cTime += (int)((endTime - startTime)/1000);
		
		//wait till the processor actually stopped
		while(processor.isRunning()) {}
	}
	
	private void makeSchedule() {
		readyUserCount = readyUserIndex.length;
		int userTimeShare = quantum/readyUserCount;
		int cUserProcessCount;
		
		schedule = new int[readyUserCount];
		
		for(int i=0;i<readyUserCount;i++) {
			// amount of processes in the ready queue for this user
			cUserProcessCount = readyQueue.get(i).length; 
			// calculate the time share 
			schedule[i] = userTimeShare/cUserProcessCount;
		}
	}
	
	private void makeReadyQueue() {
		LinkedList<Integer> readyUserIndex_LL = new LinkedList<>();
		LinkedList<Integer> cUserReadyQueue = null;
		
		boolean hasNewUser = false;
		Process[] cUserProcesses = null;
		
		readyQueue = new LinkedList<>();
		
		// check for each user
		for(int user=0;user<userCount;user++) {
			// take the processes for this user
			cUserProcesses = userProcesses.get(user);
			// check for each processes
			for(int p=0; p<cUserProcesses.length;p++) {

				if(!cUserProcesses[p].isDone()                         // if the process is not done
						& cTime >= cUserProcesses[p].getReadyTime()) { // and if the process is ready
					
					// add the user in queue if not already in queue
					if(readyUserIndex_LL.isEmpty() || readyUserIndex_LL.getLast() != user) {
						readyUserIndex_LL.add(user);
						cUserReadyQueue = new LinkedList<>();// reset the cUserQueue
						hasNewUser = true;
					}
					
					// add the process in queue
					cUserReadyQueue.add(p);
				}
			}
			
			if(hasNewUser) {// if there is a new user added into the queue
				readyQueue.add((cUserReadyQueue.toArray(new Integer[0])));
				hasNewUser = false;
			}
		}
		
		readyUserIndex = readyUserIndex_LL.toArray(new Integer[0]);
	}
	
	private boolean allProcessesDone() {
		Process[] cUserProcesses = null;
		for (int user=0;user<userCount;user++) {
			cUserProcesses = userProcesses.get(user);
			for (int p=0;p<cUserProcesses.length;p++) {
				if (!cUserProcesses[p].isDone()) return false; // if any process is not done
			}
		}
		return true; // if all processes are done
	}
	
	
	public String getOutputData() {return outputData;}
	
	
//	private static void writeFile(String of_name, String data) {
//
//		// writing result
//		try {
//			// Assume default encoding.
//			FileWriter fileWriter =
//					new FileWriter(of_name);
//
//			// Always wrap FileWriter in BufferedWriter.
//			BufferedWriter bufferedWriter =
//					new BufferedWriter(fileWriter);
//
//			// Note that write() does not automatically
//			// append a newline character.
//			//		            bufferedWriter.write("Hello there,");
//			//		            bufferedWriter.write(" here is some text.\n");
//			//		            bufferedWriter.newLine();
//			//		            bufferedWriter.write("We are writing");
//			bufferedWriter.write(data);
//
//			// Always close files
//			bufferedWriter.close();
//			fileWriter.close();
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
