package processor.pipeline;
import configuration.Configuration;
import processor.Clock;
import processor.Processor;
import generic.*;
import generic.Event.EventType;
import processor.Processor;

public class MemoryAccess implements Element {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	OF_EX_LatchType OF_EX_Latch;
	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}
	
	public void performMA()
	{
		//TODO
		int rs1,rd,imm;
		int rs1val,rdval;
		
		if(EX_MA_Latch.isMA_enable()) {
			
			if(EX_MA_Latch.isMA_busy()) {
				System.out.println("-----------MA STAGE IS BUSY--------------");
				return;
			}
			
			OF_EX_Latch.setEX_busy(false);
			
			System.out.println("-----------MA STAGE AAYA--------------");
			
			
			rs1=EX_MA_Latch.get_rs1();
			imm=EX_MA_Latch.get_imm();
			rd=EX_MA_Latch.get_rd();
			rdval=containingProcessor.getRegisterFile().getValue(rd);
			rs1val=containingProcessor.getRegisterFile().getValue(rs1);
			int alu_Result = EX_MA_Latch.getalu_Result();
			operation_functions op = new operation_functions();
			String opcode = EX_MA_Latch.getOpType();
			String operation = op.getOperation(opcode);
			
			System.out.println(operation  +  " Currently in MA");
			
			
			switch(operation) {
			case "load":
//				int ldres=containingProcessor.getMainMemory().getWord(rs1val+imm);
//				System.out.println("loadres "+ldres);
//				MA_RW_Latch.setalu_Result(ldres);//to be written at rw stage
				Simulator.getEventQueue().addEvent(
					new MemoryReadEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor.getMainMemory(),
							alu_Result
						)
				
				);
				EX_MA_Latch.set_isMA_busy(true);
				break;
				
			case "store":
//				System.out.println(imm);
//				System.out.println(rdval+" "+rd);
//				containingProcessor.getMainMemory().setWord(rdval+imm, rs1val);//no output here work finished
//				MA_RW_Latch.setRW_enable(false);
				System.out.println(alu_Result+"--------------HEYYYY IM IN STORE!!!--------------");
				Simulator.getEventQueue().addEvent(
					new MemoryWriteEvent(
							Clock.getCurrentTime() + Configuration.mainMemoryLatency,
							this,
							containingProcessor.getMainMemory(),
							alu_Result,
							rs1val
						)
				
				);
				EX_MA_Latch.set_isMA_busy(true);
				break;
			 default:
			    EX_MA_Latch.setMA_enable(false);
				MA_RW_Latch.setRW_enable(true);
				MA_RW_Latch.setalu_Result(alu_Result);//to be written at rw stage
				MA_RW_Latch.set_imm(EX_MA_Latch.get_imm());//transfer the values from of ex ma latch to ma rw latch
				MA_RW_Latch.set_op(EX_MA_Latch.getOpType());
				MA_RW_Latch.set_rd(EX_MA_Latch.get_rd());
				MA_RW_Latch.set_rs1(EX_MA_Latch.get_rs1());
				MA_RW_Latch.set_rs2(EX_MA_Latch.get_rs2());
			}
			
			
			
			
		}

		MA_RW_Latch.setend_PC(EX_MA_Latch.getend_PC());
		System.out.println("-----------MA STAGE GAYA--------------");
	}
	
	@Override
	public void handleEvent(Event e)
	{
		System.out.println("-----------MA STAGE KA HANDLE EVENT AAYA--------------");
		if(e.getEventType() == EventType.MemoryResponse)
		{	
			//EX_MA_Latch.setMA_enable(false);
			EX_MA_Latch.set_isMA_busy(false);
			EX_MA_Latch.setMA_enable(false);
			
			
			MA_RW_Latch.setRW_enable(true);
			
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			MA_RW_Latch.setalu_Result(event.getValue());//to be written at rw stage
			MA_RW_Latch.set_imm(EX_MA_Latch.get_imm());//transfer the values from of ex ma latch to ma rw latch
			MA_RW_Latch.set_op(EX_MA_Latch.getOpType());
			MA_RW_Latch.set_rd(EX_MA_Latch.get_rd());
			MA_RW_Latch.set_rs1(EX_MA_Latch.get_rs1());
			MA_RW_Latch.set_rs2(EX_MA_Latch.get_rs2());
		}
		else if(e.getEventType() == EventType.MemoryConfirm) 
		{
			EX_MA_Latch.set_isMA_busy(false);
			EX_MA_Latch.setMA_enable(false);
			MA_RW_Latch.setRW_enable(false);
			MA_RW_Latch.set_imm(EX_MA_Latch.get_imm());//transfer the values from of ex ma latch to ma rw latch
			MA_RW_Latch.set_op(EX_MA_Latch.getOpType());
			MA_RW_Latch.set_rd(EX_MA_Latch.get_rd());
			MA_RW_Latch.set_rs1(EX_MA_Latch.get_rs1());
			MA_RW_Latch.set_rs2(EX_MA_Latch.get_rs2());
			MA_RW_Latch.setalu_Result(EX_MA_Latch.getalu_Result());
		}
		
		System.out.println("-----------MA STAGE KA HANDLE EVENT GAYA--------------");
		
		
	}
}
