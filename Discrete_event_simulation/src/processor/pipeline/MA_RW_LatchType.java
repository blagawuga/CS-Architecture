package processor.pipeline;

public class MA_RW_LatchType {
	int ldResult;
	boolean RW_enable;
	private int rs1;
	private int rs2;
	private int rd;
	private int imm;
	private int alu_Result;
	private String opcode;
	int end_PC;
	public void setend_PC(int end_PC) {
		this.end_PC=end_PC;
	}
	public int getend_PC() {
		return end_PC;
	}
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}
	public void clearrd() {
		this.rd=0;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}
	public void setldResult(int ldResult) {
		this.ldResult=ldResult;
	}
	
	public void set_op(String opcode) {
		this.opcode = opcode;
	}
	
	public String getOpType() {
		return opcode;
	}
	
	public void set_rs1(int rs1) {
		this.rs1 = rs1;
	}
	
	public void set_rs2(int rs2) {
		this.rs2 = rs2;
	}
	
	public void set_rd(int rd) {
		this.rd = rd;
	}
	
	public void set_imm(int imm) {
		this.imm = imm;
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
	
	public void setalu_Result(int alu_Result) {
		this.alu_Result=alu_Result;
	}
	public int getalu_Result() {
		return alu_Result;
	}
	
	
	
}
