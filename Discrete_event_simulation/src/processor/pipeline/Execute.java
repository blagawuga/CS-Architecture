package processor.pipeline;
import configuration.Configuration;
import processor.Clock;
import processor.Processor;
import generic.*;
import processor.Processor;

public class Execute implements Element{
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	IF_OF_LatchType IF_OF_Latch;

	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch,IF_OF_LatchType iF_OF_Latch)
	{
		this.IF_OF_Latch = iF_OF_Latch;
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
	}
	
	public void performEX()
	{
		
		if(OF_EX_Latch.isEX_enable())
		{	
			if(OF_EX_Latch.isEX_busy()) {
				return;
			}
			
			Simulator.instructions_int++;
			System.out.println("-----------EX STAGE AAYA--------------");
			
			operation_functions op = new operation_functions();
			String opcode = OF_EX_Latch.getOpType();
			String operation = op.getOperation(opcode);
			System.out.println(operation);
			int rs1, rs2, rd, imm;
			rs1=OF_EX_Latch.get_rs1();
			rs2=OF_EX_Latch.get_rs2();
			imm=OF_EX_Latch.get_imm();
			rd=OF_EX_Latch.get_rd();
			System.out.println("current ops->"+operation);
			System.out.println("rs1->"+rs1);
			System.out.println("rs2->"+rs2);
			System.out.println("rd->"+rd);
			System.out.println("imm->"+imm);
			String r1;
			int branchValue;
			//boolean isBranchTake;
			//System.out.println(x);
			EX_MA_Latch.setMA_enable(true);
			int rs1val,rs2val,rdval;
			rs1val=containingProcessor.getRegisterFile().getValue(rs1);
			rs2val=containingProcessor.getRegisterFile().getValue(rs2);
			rdval=containingProcessor.getRegisterFile().getValue(rd);
			System.out.println("rs1val->"+rs1val);
			System.out.println("rs2val->"+rs2val);
			System.out.println("rdval->"+rdval);
			
			switch(operation) {
			
			case "add":
				
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rs1,
									rs2,
									rd,
									imm,
									rs1val+rs2val,//alu_Result
									opcode
								)					
				);
				//EX_MA_Latch.setalu_Result(rs1val+rs2val);
				break;
				
			case "addi":
				
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rs1,
									rs2,
									rd,
									imm,
									rs1val+imm,//alu_Result
									opcode
								)					
				);
				
				//EX_MA_Latch.setalu_Result(rs1val+imm);
				break;
				
			case "sub":
				
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rs1,
									rs2,
									rd,
									imm,
									rs1val-rs2val,//alu_Result
									opcode
								)					
				);
				//EX_MA_Latch.setalu_Result(rs1val-rs2val);
				break;
				
			case "subi":
				
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.ALU_latency,
									this,
									containingProcessor.getEXUnit(),
									rs1,
									rs2,
									rd,
									imm,
									rs1val-imm,//alu_Result
									opcode
								)					
				);
				//EX_MA_Latch.setalu_Result(rs1val-imm);
				break;
				
			case "mul":
				
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.multiplier_latency,
									this,
									containingProcessor.getEXUnit(),
									rs1,
									rs2,
									rd,
									imm,
									rs1val*rs2val,//alu_Result
									opcode
								)					
				);
				//EX_MA_Latch.setalu_Result(rs1val*rs2val);
				break;
				
			case "muli":
				
				Simulator.getEventQueue().addEvent(
						new ExecutionCompleteEvent (
									Clock.getCurrentTime()+Configuration.multiplier_latency,
									this,
									containingProcessor.getEXUnit(),
									rs1,
									rs2,
									rd,
									imm,
									rs1val*imm,//alu_Result
									opcode
								)					
				);
				//EX_MA_Latch.setalu_Result(rs1val*imm);
				break;
				
			case "div":
				EX_MA_Latch.setalu_Result(rs1val/rs2val);
				containingProcessor.getRegisterFile().setValue(31, rs1val % rs2val);				
				break;
				
			case "divi":
				EX_MA_Latch.setalu_Result(rs1val/imm);
				containingProcessor.getRegisterFile().setValue(31, rs1val % imm);
				break;
				
			case "and":
				EX_MA_Latch.setalu_Result(rs1val & rs2val);
				break;
				
			case "andi":
				EX_MA_Latch.setalu_Result(rs1val & imm);
				break;
				
			case "or":
				EX_MA_Latch.setalu_Result(rs1val | rs2val);
				break;
				
			case "ori":
				EX_MA_Latch.setalu_Result(rs1val | imm);
				break;
				
			case "xor":
				EX_MA_Latch.setalu_Result(rs1val ^ rs2val);
				break;
				
			case "xori":
				EX_MA_Latch.setalu_Result(rs1val ^ imm);
				break;
				
			case "slt":
				if(rs1val<rs2val) {
					EX_MA_Latch.setalu_Result(1);
				}
				else {
					EX_MA_Latch.setalu_Result(0);
				}
				break;
				
			case "slti":
				if(rs1val<imm) {
					EX_MA_Latch.setalu_Result(1);
				}
				else {
					EX_MA_Latch.setalu_Result(0);
				}break;
				
			case "sll":
				EX_MA_Latch.setalu_Result(rs1val << rs2val);
				break;
				
			case "slli":
				EX_MA_Latch.setalu_Result(rs1val<<imm);
				break;
				
			case "srl":
				EX_MA_Latch.setalu_Result(rs1val >> rs2val);
				break;
				
			case "srli":
				EX_MA_Latch.setalu_Result(rs1val>>imm);
				break;
				
			case "sra":
				r1=Integer.toBinaryString(rs1val);
				for(int i=0;i<rs2;i++) {
					char dum=r1.toCharArray()[0];
					r1=dum+r1.substring(0,r1.length()-1);
				}
				EX_MA_Latch.setalu_Result(Integer.parseInt(r1));
				break;
				
			case "srai":
				rs1=OF_EX_Latch.get_rs1();
				imm=OF_EX_Latch.get_imm();
				r1=Integer.toBinaryString(rs1);
				for(int i=0;i<imm;i++) {
					char dum1=r1.toCharArray()[0];
					r1=dum1+r1.substring(0,r1.length()-1);
				}
				EX_MA_Latch.setalu_Result(Integer.parseInt(r1));
				break;
				
			case "load":
				break;
				
			case "store":
				
				break;
				
			case "jmp":
				branchValue = OF_EX_Latch.get_BranchValue();
				containingProcessor.getRegisterFile().setProgramCounter(branchValue);
				EX_IF_Latch.set_branchP(branchValue);
				EX_IF_Latch.set_IsEXIF_Enable(true);
				EX_MA_Latch.setMA_enable(false);
				IF_OF_Latch.setOF_enable(false);
				System.out.println("///////////branch hua\\\\\\");
				Simulator.ch++;
				break;
				
			case "beq":
				EX_MA_Latch.setMA_enable(false);
				
				if (rs1val == rdval)
				{
					Simulator.ch++;
					branchValue = OF_EX_Latch.get_BranchValue();
					containingProcessor.getRegisterFile().setProgramCounter(branchValue);
					EX_IF_Latch.set_branchP(branchValue);
					EX_IF_Latch.set_IsEXIF_Enable(true);
					IF_OF_Latch.setOF_enable(false);// wtd in case instruction to be overwritten is held back due to wait counter and till then beq moves from ex stage
					System.out.println("///////////branch hua\\\\\\");
				}
				else 
				{
					EX_IF_Latch.set_IsEXIF_Enable(true);
				}
				break;
				
			case "bne":
				EX_MA_Latch.setMA_enable(false);
				if (rs1val != rdval)
				{
					Simulator.ch++;
					branchValue = OF_EX_Latch.get_BranchValue();
					containingProcessor.getRegisterFile().setProgramCounter(branchValue);
					EX_IF_Latch.set_branchP(branchValue);
					EX_IF_Latch.set_IsEXIF_Enable(true);
					IF_OF_Latch.setOF_enable(false);
					System.out.println("///////////branch hua\\\\\\");
				}
				else {
					EX_IF_Latch.set_IsEXIF_Enable(true);
					System.out.println("///////////branch NNN hua\\\\\\");
				}
				break;
				
			case "blt":
				EX_MA_Latch.setMA_enable(false);
				if (rs1val < rdval)
				{
					Simulator.ch++;
					branchValue = OF_EX_Latch.get_BranchValue();
					containingProcessor.getRegisterFile().setProgramCounter(branchValue);
					EX_IF_Latch.set_branchP(branchValue);
					EX_IF_Latch.set_IsEXIF_Enable(true);
					IF_OF_Latch.setOF_enable(false);
					System.out.println("///////////branch hua\\\\\\");
				}
				else {
					EX_IF_Latch.set_IsEXIF_Enable(true);
					System.out.println("///////////branch NNN hua\\\\\\");
				}
				break;
				
			case "bgt":
				EX_MA_Latch.setMA_enable(false);
				if (rs1val > rdval)
				{
					branchValue = OF_EX_Latch.get_BranchValue();
					containingProcessor.getRegisterFile().setProgramCounter(branchValue);
					EX_IF_Latch.set_branchP(branchValue);
					//System.out.println("tatti "+branchValue);
					EX_IF_Latch.set_IsEXIF_Enable(true);
					//MA LATCH FALSE ??
					IF_OF_Latch.setOF_enable(false);
					Simulator.ch++;
					System.out.println("///////////branch hua\\\\\\");
				}
				else {
					EX_IF_Latch.set_IsEXIF_Enable(true);
					System.out.println("///////////branch NNN hua\\\\\\");
				}
				break;
			}
			System.out.println(containingProcessor.getRegisterFile().getContentsAsString());
			System.out.println("aluresult->"+EX_MA_Latch.getalu_Result());
					
			
		}
		if(containingProcessor.getRegisterFile().getWaitCounter()>=0) { // If there's order to wait, we'll disable EX
			EX_IF_Latch.set_IsEXIF_Enable(false);
		}
		if(EX_IF_Latch.isEX_IF()) {
			
		}

		EX_MA_Latch.setend_PC(OF_EX_Latch.getend_PC());
		System.out.println("-----------EX STAGE GAYA--------------");
	}
	
	@Override
	public void handleEvent(Event e)
	{
		if(OF_EX_Latch.isEX_busy())
		{
			e.setEventTime(Clock.getCurrentTime() + 1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			ExecutionCompleteEvent event = (ExecutionCompleteEvent) e;
			
			OF_EX_Latch.setEX_enable(false);
			EX_MA_Latch.set_imm(event.get_imm());//transfer the values from of ex latch to ex ma latch
			EX_MA_Latch.set_op(event.getOpType());
			EX_MA_Latch.set_rd(event.get_rd());
			EX_MA_Latch.set_rs1(event.get_rs1());
			EX_MA_Latch.set_rs2(event.get_rs2());
			
		}
	}

	
}