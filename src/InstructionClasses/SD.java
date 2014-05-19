package InstructionClasses;

import Main.ReadFile;

public class SD extends Instruction {

	public int offset;
	public SD(String opcode, String[] operands,String r) {
		super(opcode, operands,r);
		getRegisters();
	}

	@Override
	public void executeInstruction() {
		// TODO Auto-generated method stub

	}



	@Override
	public void getRegisters() {
		destinationRegister = -1;//avoid waw
		sourceRegister1 = Integer.parseInt(this.arg1[0].substring(1));
		offset = Integer.parseInt(this.arg1[1].substring(0, this.arg1[1].lastIndexOf('(')));
		sourceRegister2 = Integer.parseInt(this.arg1[1].substring(this.arg1[1].lastIndexOf('(')+2,this.arg1[1].lastIndexOf(')')));
		//System.out.println("So the source register is R"+sourceRegister1);
		////System.out.println("So the offset is "+offset);
		//System.out.println("So the sourceRegister2 register is R"+sourceRegister2);
		cDestinationRegister = 0;//For F type instrucitons it is actually one but this will not be case for LD and SD
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
		sValue1=ReadFile.ArrayOfRegister[sourceRegister1];
		sValue2=ReadFile.ArrayOfRegister[sourceRegister2]+offset-256;

	}

}
