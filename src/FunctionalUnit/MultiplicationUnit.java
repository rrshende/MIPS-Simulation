package FunctionalUnit;

import java.util.LinkedList;

import InstructionClasses.Instruction;
import Main.MipsSimulator;

public class MultiplicationUnit extends FunctionalUnit{
	LinkedList<Instruction> list = new LinkedList<Instruction>();

	public MultiplicationUnit(boolean isPipelined, int noOfCycles,boolean isBusy) {
		super(isPipelined, noOfCycles, isBusy);
		// TODO MUto-generated constructor stub
	}

	public MultiplicationUnit() {
		super();
	}
	public boolean isEmptyList(){
		for(int i=0;i<list.size();i++){
			if(list.get(i)!=null){
				return false;
			}
		}
		return true;
	}
	@Override
	public void ProcessByFunctionalUnit(int currentCycleNumber) {

		if(isPipelined){
			if(!stall){
				list.add(instruction);

				if(list.size() >=noOfCycles){
					//remove first entered
					Instruction removed = list.remove();
					if(removed!=null){
						readyToWrite = true;
						/*	ADD TO ARRAY LIST	*/
						MipsSimulator.e.list.add(removed);


						removed.clckCyclePosition[2] = currentCycleNumber;
						MipsSimulator.w.setOccupied(true);
						removed.executeInstruction();
						isBusy = false;//always false
						MipsSimulator.e.setInstruction(removed);
						MipsSimulator.e.passToNextStage(currentCycleNumber);
					}
					instruction = null;

				}
			}
			else{
				MipsSimulator.e.list.add(removed);
				removed = null;
				stall = false;
			}
		}
		else{
			if(instruction!=null){
				isBusy = true;
				//Allow noOfCycles to exec
				if(currentCycleNumber - instruction.clckCyclePosition[1] >=0){
					//check if WB can accept
					if(MipsSimulator.w.isOccupied()){
						//System.out.println("MU->WB cannot be done");
						isBusy = true;
					}
					else{
						MipsSimulator.w.setOccupied(true);

						/*	ADD TO ARRAY LIST	*/
						MipsSimulator.e.list.add(instruction);

						instruction.executeInstruction();
						instruction.clckCyclePosition[2] = currentCycleNumber;
						//System.out.println("MU->WB");
						isBusy = false;
						MipsSimulator.e.setInstruction(instruction);
						MipsSimulator.e.passToNextStage(currentCycleNumber);
						instruction = null;

					}
				}

			}

		}

	}
}
