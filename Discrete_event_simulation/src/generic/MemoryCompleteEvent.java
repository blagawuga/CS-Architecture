package generic;

import generic.Event.EventType;

public class MemoryCompleteEvent extends Event {

	int value;
	boolean success;
	
	public MemoryCompleteEvent(long eventTime, Element requestingElement, Element processingElement, boolean success) {
		super(eventTime, EventType.MemoryComplete, requestingElement, processingElement);
		this.success = success;
	}

	public int getSuccess() {
		return value;
	}

	public void setSuccess(int value) {
		this.value = value;
	}

}
