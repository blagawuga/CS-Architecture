package processor.pipeline;

public class EX_MA_LatchType {
	private int alu_Result;
	boolean MA_enable;
	private int rs1;
	private int rs2;
	private int rd;
	private int imm;
	private String opcode;
	int end_PC;
	boolean MA_busy;

	public boolean isMA_busy() {
		return this.MA_busy;
	}
	
	public void set_isMA_busy(boolean a) {
		this.MA_busy = a;
	}
	
	
	public void setend_PC(int end_PC) {
		this.end_PC=end_PC;
	}
	public int getend_PC() {
		return end_PC;
	}
	public EX_MA_LatchType()
	{
		MA_busy = false;
		MA_enable = false;
	}
	public void setalu_Result(int alu_Result) {
		this.alu_Result=alu_Result;
	}
	public int getalu_Result() {
		return alu_Result;
	}
	public void clearrd() {
		this.rd=0;
	}
	
	public void set_op(String opcode) {
		this.opcode = opcode;
	}
	
	public String getOpType() {
		return opcode;
	}
	
	public boolean isMA_enable() {
		return MA_enable;
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
	
	public void setMA_enable(boolean mA_enable) {
		MA_enable = mA_enable;
	}

}
