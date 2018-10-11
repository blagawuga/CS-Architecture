package generic;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.*;
import processor.Clock;
import processor.Processor;
import processor.pipeline.*;
import java.util.*;
public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;
	public static int cycles = 0;
	public static int first_program = 0;
	public static int instructions_int = 0;
	public static int time=0;
	public static int ch=0;
	public static int dh=0;
	static EventQueue eventQueue;
	
	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
		eventQueue = new EventQueue();
	}
	
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
			
			processor.getRegisterFile().setValue(1, 65535); // Setting x1
			processor.getRegisterFile().setValue(2, 65535); // Setting x2
			
			String inputFile = assemblyProgramFile;
			String instructions = "";
			int i = 0;
			try (DataInputStream dis = new DataInputStream(new FileInputStream(inputFile))) {
			    while(dis.available() > 0) {
			        if(i==0) {
			        		
				        	int value = dis.readInt();
				        	//System.out.println(value);
				        	first_program = value; // Setting the first instruction
						    //System.out.println(first_program);
						    processor.getRegisterFile().setProgramCounter(first_program);
				    
			        	}
			        else {
			        	int value = dis.readInt();
			        	processor.getMainMemory().setWord(i-1, value);
				        String temp = String.format("%"+Integer.toString(32)+"s",Integer.toBinaryString(value)).replace(" ","0");
				        //System.out.println(temp);
				        instructions+= temp+"\n";	    
			        }
			        
			        i++;
			        
		     }

		     //System.out.println("ins->"+instructions);   
			 //System.out.println(i);
			 System.out.println("Number of instructions  = "+instructions_int);
		    
			
		}
		catch (IOException ex) {
            ex.printStackTrace();
        }
	}
	
	public static EventQueue getEventQueue() {
		return eventQueue;
	}

	
	public static void simulate()
	{
		
		while(simulationComplete == false)
		{
			
			System.out.println("---------------------------------------------------------------------------------");
			System.out.println("WHILE LOOP START----~~~~ CYCLE NUMBER = "+cycles+" \n\n");
			
			processor.getRWUnit().performRW();
			
			
			processor.getMAUnit().performMA();
			
			
			processor.getEXUnit().performEX();
			
			System.out.println("******************CONTENT OF PRIORITY QUEUE START********************************\n");
			Simulator.getEventQueue().printElements();
			System.out.println("\n******************CONTENT OF PRIORITY QUEUE END********************************\n");
			
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~CALLING PROCESS EVENTS START~~~~~~~~~~~~~~~~~~~~~~\n");
			eventQueue.processEvents();
			System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~CALLING PROCESS EVENTS END~~~~~~~~~~~~~~~~~~~~~~\n");
			
			System.out.println("******************CONTENT OF PRIORITY QUEUE START********************************\n");
			Simulator.getEventQueue().printElements();
			System.out.println("\n******************CONTENT OF PRIORITY QUEUE END********************************\n");
			
			
			processor.getOFUnit().performOF();
			
			
			
			processor.getIFUnit().performIF();
			
			
			
			Clock.incrementClock();
			cycles++;
			System.out.println("WHILE LOOP KHATAM");
			System.out.println("---------------------------------------------------------------------------------\n\n");
//			if (cycles==900)
//				break;
		}
		System.out.println("Number of cycles "+cycles);
		// TODO
		generic.Statistics.setInStat(instructions_int, time+1);
		System.out.println("data_hazard-> "+dh+"control_hazard->"+ch);
		// set statistics
	}
	
	
	
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
