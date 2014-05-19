package FunctionalUnit;

import Main.MipsSimulator;
import Main.ReadFile;

public class IntegerUnit extends FunctionalUnit{

	public IntegerUnit(boolean isPipelined, int noOfCycles, boolean isBusy) {
		super(isPipelined, noOfCycles, isBusy);
	}

	public IntegerUnit() {
		super();
	}

	@Override
	public void ProcessByFunctionalUnit(int currentCycleNumber) {
		if(instruction!=null){
			isBusy = true;

			//Allow noOfCycles to exec
			if(currentCycleNumber - instruction.clckCyclePosition[1] >=1/*>=noOfCycles*/){
				if(currentCycleNumber - instruction.clckCyclePosition[1] >1){
					instruction.setDelay(instruction.getDelay() + 1);
					MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction)] = 1;
					instruction.hazard[3] = 'Y';
				}
				//check if mem can accept
				if(MipsSimulator.m.isOccupied()){
					////System.out.println("IU->MEM cannot be done");
					isBusy = true;
				}
				else{
					//LOGIC WHICH FU WILL WB FiRST

					MipsSimulator.m.setOccupied(true);
					isBusy = false;
					MipsSimulator.m.setInstruction(instruction);
					instruction = null;

				}
			}

		}
	}
}
