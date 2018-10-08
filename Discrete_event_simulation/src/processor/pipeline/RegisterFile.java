package processor.pipeline;

public class RegisterFile {
	int[] registerFile;
	int programCounter;
	int wait_counter;
	//boolean end;
	int wait_end;
	public RegisterFile()
	{
		registerFile = new int[32];
		registerFile[0]=0;			//%xo is always 0 [RISC V]
		wait_counter = -1;
	}
	
	public int getValue(int registerNumber)
	{
		return registerFile[registerNumber];
	}
	
	public void setValue(int registerNumber, int value)
	{
		registerFile[registerNumber] = value;
	}

	public int getProgramCounter()
	{
		return programCounter;
	}

	public void setProgramCounter(int programCounter)
	{
		this.programCounter = programCounter;
	}
	
	public void incrementProgramCounter()
	{
		this.programCounter++;
	}
	
//	public void setend(boolean end)
//	{
//		this.end = end;
//	}
//	
//	public boolean getend()
//	{
//		return end;
//	}
	
	public void setWaitEndCounter(int count)
	{
		this.wait_end = count;
	}
	
	public int getWaitEndCounter()
	{
		return wait_end;
	}
	
	
	public void setWaitCounter(int wait_counter)
	{
		this.wait_counter = wait_counter;
	}
	
	public int getWaitCounter()
	{
		return wait_counter;
	}
	
	public String getContentsAsString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\nRegister File Contents:\n\n");
		sb.append("PC" + "\t: " + programCounter + "\n\n");
		
		sb.append("x" + 0 + "\t: " + registerFile[0]+ "\n");		//%xo is always 0 [RISC V]
		for(int i = 1; i < 32; i++)
		{
			sb.append("x" + i + "\t: " + registerFile[i] + "\n");
		}		
		sb.append("\n");
		return sb.toString();
	}
}
