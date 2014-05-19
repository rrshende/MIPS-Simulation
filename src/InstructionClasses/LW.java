package InstructionClasses;

import Main.MipsSimulator;
import Main.ReadFile;



public class LW extends Instruction{
	
	public int offset;
	public LW(String opcode, String[] operands,String r) {
		super(opcode, operands,r);
		offset = 0;
		getRegisters();
	}


	@Override
	public void executeInstruction() {
		
		//result = sValue1;
		result =ReadFile.ArrayOfData[sValue1];
	}

	@Override
	public void getRegisters() {
		destinationRegister = Integer.parseInt(this.arg1[0].substring(1));
		//System.out.println("So the destination register is R"+destinationRegister);
		offset = Integer.parseInt(this.arg1[1].substring(0, this.arg1[1].lastIndexOf('(')));
		sourceRegister1 = Integer.parseInt(this.arg1[1].substring(this.arg1[1].lastIndexOf('(')+2,this.arg1[1].lastIndexOf(')')));
		//System.out.println("So the source register is R"+sourceRegister1);
		//System.out.println("So the offset is "+offset);
		cDestinationRegister = 0;//For R type instrucitons
		sourceRegister2=-1;
	}


	@Override
	public void lockDestination() {
		MipsSimulator.isFreeRegister[0][destinationRegister] = false;
		
	}


	@Override
	public void unlockDestination() {
		MipsSimulator.isFreeRegister[0][destinationRegister] = true;
		
	}


	@Override
	public void storeResult() {
		ReadFile.ArrayOfRegister[destinationRegister] = result;
	}


	@Override
	public void extractValuesFromRegisterInDecode() {
		sValue1 = /*ReadFile.ArrayOfData[*/ReadFile.ArrayOfRegister[sourceRegister1]
				+offset-256/*]*/;
		sValue2= -1;
	}
}
