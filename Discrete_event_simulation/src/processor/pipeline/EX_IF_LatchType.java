package processor.pipeline;

public class EX_IF_LatchType {
	

	private boolean isBranchTake;
	private int branchP;
	
	public EX_IF_LatchType()
	{
		isBranchTake = false;
		branchP = 0;
	}
	
	public void set_IsEXIF_Enable(boolean p) {
		this.isBranchTake = p;
	}
	
	public boolean isEX_IF() {
		return isBranchTake;
	}
	
	public void set_branchP(int p) {
		this.branchP = p;
	}
	
	public int get_branchP() {
		return branchP;
	}

}
