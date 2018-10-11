package generic;

import generic.Event.EventType;

public class MemoryConfirmEvent extends Event {

	boolean confirm;
	
	public MemoryConfirmEvent(long eventTime, Element requestingElement, Element processingElement, boolean confirm) {
		super(eventTime, EventType.MemoryConfirm, requestingElement, processingElement);
		this.confirm = confirm;
	}

	public boolean isConfirm() {
		return this.confirm;
	}

	public void setConfirm(boolean value) {
		this.confirm = value;
	}

}
