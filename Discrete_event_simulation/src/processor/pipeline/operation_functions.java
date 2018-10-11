package processor.pipeline;

import java.util.HashMap;
//import java.util.Map;

public class operation_functions {

	private static HashMap<String, Integer> operationType = new HashMap<String, Integer>();
	private static HashMap<String, String> operation = new HashMap<String, String>();
	
	public operation_functions() {
		
		operationType.put("00000", 3);
		operationType.put("00001", 2);
		operationType.put("00010", 3);
		operationType.put("00011", 2);
		operationType.put("00100", 3);
		operationType.put("00101", 2);
		operationType.put("00110", 3);
		operationType.put("00111", 2);
		operationType.put("01000", 3);
		operationType.put("01001", 2);
		operationType.put("01010", 3);
		operationType.put("01011", 2);
		operationType.put("01100", 3);
		operationType.put("01101", 2);
		operationType.put("01110", 3);
		operationType.put("01111", 2);
		operationType.put("10000", 3);
		operationType.put("10001", 2);
		operationType.put("10010", 3);
		operationType.put("10011", 2);
		operationType.put("10100", 3);
		operationType.put("10101", 2);
		operationType.put("10110", 2);
		operationType.put("10111", 2);
		operationType.put("11000", 1);
		operationType.put("11001", 2);
		operationType.put("11010", 2);
		operationType.put("11011", 2);
		operationType.put("11100", 2);
		operationType.put("11101", 1);
		operationType.put("99999", 3);	
		
		// ---------------------------- //
		
		operation.put("00000", "add");
		operation.put("00001", "addi");
		operation.put("00010", "sub");
		operation.put("00011", "subi");
		operation.put("00100", "mul");
		operation.put("00101", "muli");
		operation.put("00110", "div");
		operation.put("00111", "divi");
		operation.put("01000", "and");
		operation.put("01001", "andi");
		operation.put("01010", "or");
		operation.put("01011", "ori");
		operation.put("01100", "xor");
		operation.put("01101", "xori");
		operation.put("01110", "slt");
		operation.put("01111", "slti");
		operation.put("10000", "sll");
		operation.put("10001", "slli");
		operation.put("10010", "srl");
		operation.put("10011", "srli");
		operation.put("10100", "sra");
		operation.put("10101", "srai");
		operation.put("10110", "load");
		operation.put("10111", "store");
		operation.put("11000", "jmp");
		operation.put("11001", "beq");
		operation.put("11010", "bne");
		operation.put("11011", "blt");
		operation.put("11100", "bgt");
		operation.put("11101", "end");
		operation.put("99999", "bubble");
	}
	
	public int getOperationType(String opcode) {
		
		return operationType.get(opcode);
		
	}
	
	public String getOperation(String opcode) {
		
		return operation.get(opcode);
		
	}
	
}
