package InstructionClasses;

import Main.MipsSimulator;
import Main.ReadFile;

public class LD extends Instruction {

	public int offset;
	public LD(String opcode, String[] operands,String r) {
		super(opcode, operands,r);
		offset = 0;
		getRegisters();
	}

	@Override
	public void executeInstruction() {
		// TODO Auto-generated method stub

	}



	@Override
	public void getRegisters() {
		destinationRegister = Integer.parseInt(this.arg1[0].substring(1));
		//System.out.println("So the destination register is R"+destinationRegister);
		offset = Integer.parseInt(this.arg1[1].substring(0, this.arg1[1].lastIndexOf('(')));
		sourceRegister1 = Integer.parseInt(this.arg1[1].substring(this.arg1[1].lastIndexOf('(')+2,this.arg1[1].lastIndexOf(')')));
		//System.out.println("So the source register is R"+sourceRegister1);
		//System.out.println("So the offset is "+offset);
		cDestinationRegister = 0;//For F type instrucitons it is actually one but this will not be case for LD and SD
		sourceRegister2=-1;
	}

	@Override
	public void lockDestination() {
		MipsSimulator.isFreeRegister[1][destinationRegister] = false;
	}

	@Override
	public void unlockDestination() {
		MipsSimulator.isFreeRegister[1][destinationRegister] = true;
	}

	@Override
	public void storeResult() {
		// TODO Auto-generated method stub

	}

	@Override
	public void extractValuesFromRegisterInDecode() {
		//just
		sValue1 = /*ReadFile.ArrayOfData[*/ReadFile.ArrayOfRegister[sourceRegister1]
				+offset-256/*]*/;
		////System.out.println("Offset: "+offset);
		////System.out.println("sourceRegister1"+sourceRegister1);
		//System.out.println("From LD "+sValue1);
		//sValue2= -1;
	}

}
