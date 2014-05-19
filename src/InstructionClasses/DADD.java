package InstructionClasses;

import Main.MipsSimulator;
import Main.ReadFile;

public class DADD extends Instruction {

	public DADD(String opcode, String[] operands,String r) {
		super(opcode, operands,r);
		getRegisters();
	}

	@Override
	public void executeInstruction() {
		result = sValue1+sValue2;
	}

	@Override
	public void unlockDestination() {
		MipsSimulator.isFreeRegister[0][destinationRegister] = true;
	}

	@Override
	public void getRegisters() {
		destinationRegister = Integer.parseInt(this.arg1[0].substring(1));
		//System.out.println("Destination register is R"+destinationRegister);
		sourceRegister1 = Integer.parseInt(this.arg1[1].substring(1));
		sourceRegister2 = Integer.parseInt(this.arg1[2].substring(1));
		//System.out.println("So the source register is R: "+sourceRegister1+"and "+sourceRegister2);
		cDestinationRegister = 0;

	}

	@Override
	public void lockDestination() {
		MipsSimulator.isFreeRegister[0][destinationRegister] = false;
	}

	@Override
	public void storeResult() {
		ReadFile.ArrayOfRegister[destinationRegister] = result;
	}

	@Override
	public void extractValuesFromRegisterInDecode() {
		sValue1 = ReadFile.ArrayOfRegister[sourceRegister1];
		sValue2 = ReadFile.ArrayOfRegister[sourceRegister2];		
	}

}
