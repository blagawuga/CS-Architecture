package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;
	OF_EX_LatchType OF_EX_Latch;
	IF_OF_LatchType IF_OF_Latch;
	EX_MA_LatchType EX_MA_Latch;
	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch, OF_EX_LatchType oF_EX_Latch, IF_OF_LatchType iF_OF_Latch,EX_MA_LatchType eX_MA_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
	}
	
	public void performRW()
	{
		int rd,alu_Result;
		int a=0;
		if(MA_RW_Latch.isRW_enable())
		{	
			
			if(EX_MA_Latch.isMA_busy())
			{
				System.out.println("-----------RW STAGE MEIN MA BUSY HAI--------------");
				return;
			}
			
			System.out.println("-----------RW STAGE AAYA--------------");
			
			
			//TODO
			rd=MA_RW_Latch.get_rd();
			alu_Result=MA_RW_Latch.getalu_Result();
			// if instruction being processed is an end instruction, remember to call Simulator.setSimulationComplete(true);
			operation_functions op = new operation_functions();
			String opcode = MA_RW_Latch.getOpType();
			String operation = op.getOperation(opcode);
			if(operation.matches("end")){
				IF_EnableLatch.setIF_enable(false);
				IF_OF_Latch.setOF_enable(false);
				OF_EX_Latch.setEX_enable(false);
				EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(false);
				containingProcessor.getRegisterFile().setProgramCounter(MA_RW_Latch.getend_PC()+1);
				Simulator.setSimulationComplete(true);
				a=1;
				Simulator.time=(int) processor.Clock.getCurrentTime();
				System.out.println("Simulation end");//failsafe end
			}
			else if(!operation.equals("store")){
				System.out.println("alu res in rw = "+alu_Result);
				containingProcessor.getRegisterFile().setValue(rd, alu_Result);
				//containingProcessor.getRegisterFile().getContentsAsString();
			}
		}
//		System.out.println(containingProcessor.getRegisterFile().getContentsAsString());
//		System.out.println("--------------------------------------------");
		MA_RW_Latch.setRW_enable(false);
		if(a!=1) {
			if(containingProcessor.getRegisterFile().getWaitCounter() > 0) {
				System.out.println("WAITING!!!!"+ "  "+ containingProcessor.getRegisterFile().getWaitCounter());
				containingProcessor.getRegisterFile().setWaitCounter(containingProcessor.getRegisterFile().getWaitCounter()-1);
			}
			else if(containingProcessor.getRegisterFile().getWaitCounter() == 0){
				
				IF_EnableLatch.setIF_enable(true);
				IF_OF_Latch.setOF_enable(true);		
				EX_MA_Latch.set_rd(0);
				MA_RW_Latch.set_rd(0);
				OF_EX_Latch.setEX_enable(true);
				containingProcessor.getRegisterFile().setWaitCounter(containingProcessor.getRegisterFile().getWaitCounter()-1);		
			}
			else {
				IF_EnableLatch.setIF_enable(true);
			}
		}
		System.out.println("-----------RW STAGE GAYA--------------");
			
	}
}
