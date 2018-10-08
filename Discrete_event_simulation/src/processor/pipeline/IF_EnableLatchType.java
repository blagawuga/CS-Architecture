package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable;
	boolean IF_busy;
	
	public IF_EnableLatchType()
	{
		IF_enable = true;
	}

	public boolean isIF_enable() {
		return IF_enable;
	}

	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
	
	public void setIF_busy(boolean a) {
		this.IF_busy = a;
	}
	
	public boolean isIF_busy() {
		return this.IF_busy;
	}

}
