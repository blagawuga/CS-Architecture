package processor.pipeline;
import generic.Simulator;
import java.math.BigInteger;

import processor.Processor;

public class OperandFetch {
	
	
	
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	
	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}
	
	public int calculate_value(String s)
	{
		int value = new BigInteger(s, 2).intValue();
		return value;
	}
	
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			System.out.println("-----------OF STAGE AAYA--------------");
			
			
			
			int instruction = IF_OF_Latch.getInstruction();
			operation_functions op = new operation_functions();
			int rs1 = 0;
			int rs2 = 0;
			int rd = 0;
			int imm = 0;
			OF_EX_Latch.set_rs1(rs1);
			OF_EX_Latch.set_rs2(rs2);
			OF_EX_Latch.set_rd(rd);
			OF_EX_Latch.set_imm(imm);
			// Obtain the original 32 bit string
			String temp = String.format("%"+Integer.toString(32)+"s",Integer.toBinaryString(instruction)).replace(" ","0");
			
			// Get OP code from the 32 bit string
			String opcode = temp.substring(0, 5);
			
			// Determine the type of operation (R3, R2I, RI)
			int operationType = op.getOperationType(opcode);
			OF_EX_Latch.set_op(opcode);
			String opera = op.getOperation(opcode);
			int branchValue = -1;
			
			System.out.println("Instruction-> " + opera);
			System.out.println("-----------------------------------");
			
			int rd_future1 = OF_EX_Latch.get_rd();
			int rd_future2 = EX_MA_Latch.get_rd();
			int rd_future3 = MA_RW_Latch.get_rd();
			
			System.out.println("rd_future1 ->"+rd_future1);
			System.out.println("rd_future2 ->"+rd_future2);
			System.out.println("rd_future3 ->"+rd_future3);
			
			
			switch(operationType) {
				case 1:
					
					rd = calculate_value(temp.substring(5,10));
					imm = calculate_value(temp.substring(10));
					if(temp.charAt(10)=='1') {
						
						String twentyone_ones = "1111111111111111111111";
						int ones_comp = imm^calculate_value(twentyone_ones);
						int twos_comp = ones_comp+1;
						//System.out.println(twos_comp);
						imm = (-1)*twos_comp;
					}
					if (opera.equals("jmp")) 
					{
						branchValue = containingProcessor.getRegisterFile().getProgramCounter() + imm + rd - 1;
						OF_EX_Latch.setBranchValue(branchValue);
					}
					
					System.out.println("rd -> "+rd);
					System.out.println("imm -> "+imm);
					
					if(rd == rd_future1 & rd!=0) {
						System.out.println("Conflict found on instruction -> "+opera+" with OF_EX");
						containingProcessor.getRegisterFile().setWaitCounter(3);
						Simulator.dh++;
						
					}
					else if (rd == rd_future2 & rd!=0) {
						System.out.println("Conflict found on instruction -> "+opera+" with EX_MA");
						containingProcessor.getRegisterFile().setWaitCounter(2);
						Simulator.dh++;
						
					}
					else if(rd == rd_future3 & rd!=0) {
						System.out.println("Conflict found on instruction -> "+opera+" with MA_RW");
						containingProcessor.getRegisterFile().setWaitCounter(1);
						Simulator.dh++;
						
					}
					
					
					
					OF_EX_Latch.set_rd(rd);
					OF_EX_Latch.set_imm(imm);
					break;
				case 2:
					rs1 = calculate_value(temp.substring(5,10));
					rd = calculate_value(temp.substring(10,15));
					imm = calculate_value(temp.substring(15));
					if(temp.charAt(15)=='1') {
						
						String seventeen_ones = "11111111111111111";
						String one = "00000000000000001";
						int ones_comp = imm^calculate_value(seventeen_ones);
						int twos_comp = ones_comp+calculate_value(one);
						//	System.out.println(twos_comp);
						imm = (-1)*twos_comp;
						
					}
					//System.out.println(temp.substring(15));
					//System.out.println("imm in of - "+imm);
					System.out.println("rs1 -> "+rs1);
					System.out.println("rd -> "+rd);
					System.out.println("imm -> "+imm);
					if(rs1 == rd_future1 & rs1!=0 | rd==rd_future1 & rd!=0) {
						System.out.println("Conflict found on instruction rs1 == rd_future1 -> "+opera+" with OF_EX");
						containingProcessor.getRegisterFile().setWaitCounter(3);
						Simulator.dh++;
						
					}
					else if (rs1 == rd_future2 & rs1!=0 | rd==rd_future2 & rd!=0) {
						System.out.println("Conflict found on instruction rs1 == rd_future2 -> "+opera+" with EX_MA");
						containingProcessor.getRegisterFile().setWaitCounter(2);
						Simulator.dh++;
						
					}
					else if(rs1 == rd_future3 & rs1!=0 | rd==rd_future3 & rd!=0) {
						System.out.println("Conflict found on instruction rs1 == rd_future3 -> "+opera+ " with MA_RW");
						containingProcessor.getRegisterFile().setWaitCounter(1);
						Simulator.dh++;
						
					}
					
					if (opera.equals("beq") | opera.equals("bne") | opera.equals("blt") | opera.equals("bgt"))
					{
						branchValue = containingProcessor.getRegisterFile().getProgramCounter() + imm - 1;
						OF_EX_Latch.setBranchValue(branchValue);
						
					}
					OF_EX_Latch.set_rs1(rs1);
					OF_EX_Latch.set_rd(rd);
					OF_EX_Latch.set_imm(imm);
					break;
				case 3:
					rs1 = calculate_value(temp.substring(5,10));
					rs2 = calculate_value(temp.substring(10,15));
					rd = calculate_value(temp.substring(15,20));
					
					System.out.println("rs1 -> "+rs1);
					System.out.println("rs2 -> "+rs2);
					System.out.println("rd -> "+rd);
					
					if(rs1 == rd_future1 & rs1!=0| rs2 == rd_future1 & rs2!=0) {
						System.out.println("Conflict found on instruction -> "+opera+" with OF_EX");
						containingProcessor.getRegisterFile().setWaitCounter(3);
						Simulator.dh++;
						
					}
					else if (rs1 == rd_future2 & rs1!=0 | rs2 == rd_future2 & rs2!=0) {
						System.out.println("Conflict found on instruction -> "+opera+" with EX_MA");
						containingProcessor.getRegisterFile().setWaitCounter(2);
						Simulator.dh++;
					}
					else if(rs1 == rd_future3 & rs1!=0 | rs2 == rd_future3 & rs2!=0) {
						System.out.println("Conflict found on instruction -> "+opera+" with MA_RW ");
						containingProcessor.getRegisterFile().setWaitCounter(1);
						Simulator.dh++;
					}
					
					
					
					OF_EX_Latch.set_rs1(rs1);
					OF_EX_Latch.set_rs2(rs2);
					OF_EX_Latch.set_rd(rd);
					break;
			}
			
			IF_OF_Latch.setOF_enable(false); // OF will be shut down
			if(containingProcessor.getRegisterFile().getWaitCounter()>0) { // If there's order to wait, we'll disable EX
				OF_EX_Latch.setEX_enable(false);
			}
			else {
				OF_EX_Latch.setEX_enable(true);
			}
			
			
		}
		OF_EX_Latch.setend_PC(IF_OF_Latch.getend_PC());
		System.out.println("-----------OF STAGE GAYA--------------");
	}

}
