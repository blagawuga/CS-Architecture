package processor.pipeline;
import configuration.Configuration;
import generic.MemoryReadEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;
import generic.*;

public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	public IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performIF()
	{
		System.out.println("Value of IF Enable - > "+ IF_EnableLatch.isIF_enable());
		
		if(IF_EnableLatch.isIF_enable() || EX_IF_Latch.isEX_IF())
		{
			
			if(IF_EnableLatch.isIF_busy()) {
				System.out.println("-----------IF STAGE BUSY HAI--------------");
				return;
			}
			
			System.out.println("-----------IF STAGE KA ADD EVENT AAYA--------------");
			
			Simulator.getEventQueue().addEvent(
				new MemoryReadEvent (
						Clock.getCurrentTime() + Configuration.mainMemoryLatency,
						this,
						containingProcessor.getMainMemory(),
						containingProcessor.getRegisterFile().getProgramCounter()
				)
					
			);
			
			IF_EnableLatch.setIF_busy(true);
			
			System.out.println("-----------IF STAGE GAYA--------------");
			
		}
		else {
			System.out.println("-----------IF STAGE KE BAHAR--------------");
			
		}
		
		
	}	
	




	@Override
	public void handleEvent(Event e)
	{
		
		if(OF_EX_Latch.isEX_busy())
		{
			System.out.println("-----------IF STAGE KA HANDLE EVENT BUSY HAI--------------");
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			System.out.println("-----------IF STAGE KA MANAGE EVENT AAYA--------------");
			
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			
			operation_functions op = new operation_functions();
			// Get OP code from the 32 bit string
			
		
			String temp = String.format("%"+Integer.toString(32)+"s",Integer.toBinaryString(event.getValue())).replace(" ","0");
			
			String opcode = temp.substring(0, 5);
			String operation = op.getOperation(opcode);
			
			if(operation.equals("end")) {
				System.out.println("end PC in IF\\ "+containingProcessor.getRegisterFile().getProgramCounter()+"//");
				IF_OF_Latch.setend_PC(containingProcessor.getRegisterFile().getProgramCounter());
			}
			
			
			// Determine the type of operation (R3, R2I, RI)
			System.out.println("Current Instruction -> "+ operation);
			
			IF_OF_Latch.setInstruction(event.getValue());
			int currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			
//			if(containingProcessor.getRegisterFile().getWaitCounter() == true) { // If there's order to wait, we'll disable EX
//				IF_OF_Latch.setOF_enable(false);
//			}
//			else {
//				IF_OF_Latch.setOF_enable(true);
//			}
			System.out.println("IF BUSY KO FALSE KIYA");
			IF_OF_Latch.setOF_enable(true);
			IF_EnableLatch.setIF_busy(false);
//			IF_EnableLatch.setIF_enable(false);
			System.out.println("-----------IF STAGE KE MANAGE EVENT BAHAR--------------");
			
		}
	}

}

