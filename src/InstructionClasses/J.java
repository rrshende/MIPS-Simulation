package InstructionClasses;

import Exception.InvalidJumpException;
import Main.MipsSimulator;
import Main.ReadFile;


public class J extends Instruction {
	

	public J(String opcode, String[] operands,String r) {
		super(opcode, operands,r);
		getRegisters();
		
	}

	@Override
	public void executeInstruction() {
		//System.out.println("Jump to label"+label);
		if(!ReadFile.loopMap.containsKey(label)){
			try {
				throw new InvalidJumpException(MipsSimulator.f.fCnt);
			} catch (InvalidJumpException e) {
				System.out.println(e.getmMessage());
				System.exit(0);
			}
		}
		else{
			int programCounter = ReadFile.loopMap.get(label);
			//System.out.println("Tell me where to jump "+programCounter);
			jumpTo = programCounter;
			//CALL some function
		}
	}

	
	@Override
	public void getRegisters() {
		label = this.arg1[0];
		
	}

	@Override
	public void lockDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockDestination() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void storeResult() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void extractValuesFromRegisterInDecode() {
		// TODO Auto-generated method stub
		
	}

}
