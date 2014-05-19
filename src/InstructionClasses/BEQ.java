package InstructionClasses;

import Exception.InvalidJumpException;
import Main.MipsSimulator;
import Main.ReadFile;

public class BEQ extends Instruction {
	
	public BEQ(String opcode, String[] operands,String r) {
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
			if(sValue1==sValue2){
				jumpTo = ReadFile.loopMap.get(label);
				//System.out.println("Tell me where to jump "+jumpTo);
				//CALL some function
			}
			else{
				jumpTo=-1;
				//Continue execution
				return;
			}
		}

	}

	@Override
	public void getRegisters() {
		//HAVE CONSIDERED BOTH AS SOURCE
		//what about d?? -> have setDest = -1
		destinationRegister = -1;
		sourceRegister1 = Integer.parseInt(this.arg1[0].substring(1));
		sourceRegister2 = Integer.parseInt(this.arg1[1].substring(1));
		label = this.arg1[2];
		cDestinationRegister = 0;
	}

	@Override
	public void lockDestination() {
		//not reuired
	}

	@Override
	public void unlockDestination() {
		//not reuired
	}

	@Override
	public void storeResult() {
		//not reuired
	}

	@Override
	public void extractValuesFromRegisterInDecode() {
		sValue1 = ReadFile.ArrayOfRegister[sourceRegister1];
		sValue2 = ReadFile.ArrayOfRegister[sourceRegister2];
	}

}
