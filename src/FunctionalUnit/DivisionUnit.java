package FunctionalUnit;

import java.util.LinkedList;

import InstructionClasses.Instruction;
import Main.MipsSimulator;

public class DivisionUnit extends FunctionalUnit{
	LinkedList<Instruction> list = new LinkedList<Instruction>();
	
	public DivisionUnit(boolean isPipelined, int noOfCycles, boolean isBusy) {
		super(isPipelined, noOfCycles, isBusy);
		// TODO Auto-generated constructor stub
	}

	public DivisionUnit() {
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
						//TODO
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
				if(currentCycleNumber - instruction.clckCyclePosition[1] >=noOfCycles){
					//check if WB can accept
					if(MipsSimulator.w.isOccupied()){
						////System.out.println("DU->WB cannot be done");
						isBusy = true;
					}
					else{
						MipsSimulator.w.setOccupied(true);
						/*	ADD TO ARRAY LIST	*/
						MipsSimulator.e.list.add(instruction);

						instruction.executeInstruction();
						instruction.clckCyclePosition[2] = currentCycleNumber;
						//System.out.println("DU->WB");
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


