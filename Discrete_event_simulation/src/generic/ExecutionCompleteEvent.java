package generic;

public class ExecutionCompleteEvent extends Event {
	
	int rs1;
	int rs2;
	int rd;
	int imm;
	String opcode;
	int alu_Result;
	
	public ExecutionCompleteEvent(long eventTime, Element requestingElement, Element processingElement, int rs1, int rs2, int rd, int imm, int alu_Result, String opcode)
	{
		super(eventTime, EventType.ExecutionComplete, requestingElement, processingElement);
		this.rs1 = rs1;
		this.rs2 = rs2;
		this.rd = rd;
		this.imm = imm;
		this.opcode = opcode;
		this.alu_Result = alu_Result;
	}
	
	public int get_rs1() {
		return rs1;
	}

	public int get_rs2() {
		return rs2;
	}

	public int get_imm() {
		return imm;
	}
	
	public int get_rd() {
		return rd;
	}
	
	public String get_opcode() {
		return opcode;
	}
	
	public int getalu_Result() {
		return alu_Result;
	}

}
