package FunctionalUnit;

import java.util.LinkedList;

import InstructionClasses.Instruction;
import Main.MipsSimulator;


public class AdditionUnit extends FunctionalUnit{
	LinkedList<Instruction> list = new LinkedList<Instruction>();
	
	public AdditionUnit(boolean isPipelined, int noOfCycles, boolean isBusy) {
		super(isPipelined, noOfCycles, isBusy);
	}

	public AdditionUnit() {
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
			//if i hav not passed to WB then dont add or remove
			if(!stall){
				list.add(instruction);

				if(list.size() >=noOfCycles){
					//remove first entered
					removed = list.remove();
					//list.r
					if(removed!=null){
						readyToWrite = true;
						/*	ADD TO ARRAY LIST	*/
						MipsSimulator.e.list.add(removed);
						

						/*removed.clckCyclePosition[2] = currentCycleNumber;
						MipsSimulator.w.occupied = true;

						removed.executeInstruction();
						//System.out.println("AU->WB");
						isBusy = false;//always false
						MipsSimulator.e.instruction = removed;
						MipsSimulator.e.passToNextStage(currentCycleNumber);*/
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
						//System.out.println("AU->WB cannot be done");
						isBusy = true;
					}
					else{
						MipsSimulator.w.setOccupied(true);
						/*	ADD TO ARRAY LIST	*/
						MipsSimulator.e.list.add(instruction);


						instruction.executeInstruction();
						instruction.clckCyclePosition[2] = currentCycleNumber;
						//System.out.println("AU->WB");
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

