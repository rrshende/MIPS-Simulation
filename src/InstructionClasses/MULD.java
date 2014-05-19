package InstructionClasses;

import Main.MipsSimulator;

public class MULD extends Instruction {


	public MULD(String opcode, String[] operands,String r) {
		super(opcode, operands,r);
		getRegisters();
	}

	@Override
	public void executeInstruction() {
		// TODO Auto-generated method stub
		
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
	public void getRegisters() {
		destinationRegister = Integer.parseInt(this.arg1[0].substring(1));
		//System.out.println("Destination register is R"+destinationRegister);
		sourceRegister1 = Integer.parseInt(this.arg1[1].substring(1));
		sourceRegister2 = Integer.parseInt(this.arg1[2].substring(1));
		//System.out.println("So the source register is R: "+sourceRegister1+"and "+sourceRegister2);
		
		cDestinationRegister = 1;//For F type instrucitons
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
