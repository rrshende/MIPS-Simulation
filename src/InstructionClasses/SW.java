package InstructionClasses;

import Main.ReadFile;

public class SW extends Instruction {
	public int offset;
	public SW(String opcode, String[] operands,String r) {
		super(opcode, operands,r);
		getRegisters();

	}

	@Override
	public void executeInstruction() {
		result = /*ReadFile.ArrayOfRegister[sValue1]*/sValue1;
	}



	@Override
	public void getRegisters() {
		destinationRegister = -1;//avoid waw
		sourceRegister1 = Integer.parseInt(this.arg1[0].substring(1));
		offset = Integer.parseInt(this.arg1[1].substring(0, this.arg1[1].lastIndexOf('(')));
		sourceRegister2 = Integer.parseInt(this.arg1[1].substring(this.arg1[1].lastIndexOf('(')+2,this.arg1[1].lastIndexOf(')')));
		//System.out.println("So the sourceRegister1 is R"+sourceRegister1);
		//System.out.println("So the offset is "+offset);
		//System.out.println("So the sourceRegister2 is R"+sourceRegister2);
		cDestinationRegister = 0;
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
		ReadFile.ArrayOfData[sValue2/*+offset-256*/] = result;
	}

	@Override
	public void extractValuesFromRegisterInDecode() {
		sValue1=ReadFile.ArrayOfRegister[sourceRegister1];
		sValue2=ReadFile.ArrayOfRegister[sourceRegister2]+offset-256;////System.out.println("From SW offset"+offset);
		////System.out.println("from SW "+sourceRegister2+"and value in that"+ReadFile.ArrayOfRegister[sourceRegister2]);
		//System.out.println("From SW: "+sValue2);
	}

}
