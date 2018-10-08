package generic;

import processor.pipeline.*;
import processor.memorysystem.MainMemory;

public interface Element {
	
	// IF, MA, EX will receive elements
	
	
	void handleEvent(Event event);

}
