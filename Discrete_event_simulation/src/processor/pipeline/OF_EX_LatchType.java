package processor.pipeline;

public class OF_EX_LatchType {
	boolean EX_busy;
	boolean EX_enable;
	private int rs1;
	private int rs2;
	private int rd;
	private int imm;
	private String opcode;
	private int branchValue;
	int end_PC;
	public void setend_PC(int end_PC) {
		this.end_PC=end_PC;
	}
	public int getend_PC() {
		return end_PC;
	}
	public OF_EX_LatchType()
	{
		EX_enable = false;
		EX_busy=false;
	}

	public void setEX_busy(boolean a) {
		this.EX_busy = a;
	}
	
	public boolean isEX_busy() {
		return this.EX_busy;
	}
	
	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
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
	
	public void set_op(String opcode) {
		this.opcode = opcode;
	}
	
	public String getOpType() {
		return opcode;
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
	
	public void setBranchValue(int branchValue) {
		this.branchValue = branchValue;
	}
	
	public int get_BranchValue () {
		return branchValue;
	}

}