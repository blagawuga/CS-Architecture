package processor.pipeline;
import configuration.Configuration;
import generic.MemoryReadEvent;
import generic.Simulator;
import processor.Clock;
import processor.Processor;
import generic.*;

public class InstructionFetch implements Element {
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	EX_IF_LatchType EX_IF_Latch;
	
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_EnableLatch = iF_EnableLatch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performIF()
	{

		if(IF_EnableLatch.isIF_enable())
		{
			
			if(IF_EnableLatch.isIF_busy())
			{
				System.out.println("<<<<<IF is busy>>>>");
				return;
			}
			else {
				System.out.println("<<<<<<<<<<<<IF not busy>>>>>>>>>>>");
			}
			
			Simulator.getEventQueue().addEvent(
				new MemoryReadEvent(
						Clock.getCurrentTime() + Configuration.mainMemoryLatency,
						this, 
						containingProcessor.getMainMemory(),
						containingProcessor.getRegisterFile().getProgramCounter()
						)
				);
			IF_EnableLatch.setIF_busy(true);
			IF_EnableLatch.setIF_enable(false);
		}
		
	}	
	
	@Override
	public void handleEvent(Event e)
	{
		System.out.println("<<<<<Inside IF handle>>>>>>>>");
		if(IF_OF_Latch.isOF_busy())
		{
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			IF_EnableLatch.setIF_busy(false);
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			operation_functions op = new operation_functions();
			
			int newInstruction = event.getValue();
			String temp = String.format("%"+Integer.toString(32)+"s",Integer.toBinaryString(newInstruction)).replace(" ","0");
			
			String opcode = temp.substring(0, 5);
			
			// Determine the type of operation (R3, R2I, RI)
			String operation = op.getOperation(opcode);
			System.out.println("Instruction-> "+operation+"  "+temp);
			
			IF_OF_Latch.setInstruction(event.getValue());
			containingProcessor.getRegisterFile().setProgramCounter(containingProcessor.getRegisterFile().getProgramCounter()+1);
			
			//IF_EnableLatch.setIF_enable(false);
			if(containingProcessor.getRegisterFile().getWaitCounter()>0) { // If there's order to wait, we'll disable EX
				IF_OF_Latch.setOF_enable(false);
			}
			else {
				IF_OF_Latch.setOF_enable(true);
			}
			

		}
		System.out.println("-----------IF STAGE handle  gya--------------");
	}

}

