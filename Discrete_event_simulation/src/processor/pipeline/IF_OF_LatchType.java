package processor.pipeline;

import generic.Simulator;

public class IF_OF_LatchType {
	
	boolean OF_enable;
	boolean OF_busy;
	int instruction;
	int end_PC;
	public void setend_PC(int end_PC) {
		this.end_PC=end_PC;
	}
	public int getend_PC() {
		return end_PC;
	}
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}

	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}
	
	public int getInstruction() {
		return instruction;
	}
	

	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}
	
	public void setOF_busy(boolean a) {
		this.OF_busy = a;
	}
	
	public boolean isOF_busy() {
		return this.OF_busy;
	}

}
