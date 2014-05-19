package Stages;

import java.util.ArrayList;

import FunctionalUnit.FunctionalUnit;
import InstructionClasses.Instruction;
import Main.MipsSimulator;
import Main.ReadFile;

public class Execute extends Stage{
	public ArrayList<Instruction> list = new ArrayList<Instruction>();

	public Execute(boolean flag) {
		super(flag);
	}


	@Override
	public void processInstruction(int cycleNumber) {
		
		ReadFile.mu.ProcessByFunctionalUnit(cycleNumber);

		ReadFile.au.ProcessByFunctionalUnit(cycleNumber);
		ReadFile.du.ProcessByFunctionalUnit(cycleNumber);
		// CHECK LOGIC TO WB - priority
		sendToWB(cycleNumber);
		ReadFile.iu.ProcessByFunctionalUnit(cycleNumber);
		
		
	}


	private void sendToWB(int cycleNumber) {
		if(list.size()>0){

			while(list.size()>1){
				checkPriority(list.get(0),list.get(1));
				list.remove(0);
				list.remove(0);
			}
			/*	SEND THIS INSTRUCTION TO WB		*/
			instruction = list.get(0);
			
			FunctionalUnit f1 = getFunctionalUnit(instruction);
			MipsSimulator.w.occupied = true;
			instruction.executeInstruction();
			instruction.setDelay(0);
			instruction.clckCyclePosition[2] = cycleNumber;

			if(f1.getClass().equals(ReadFile.iu.getClass())){
				//System.out.println("class match a "+cycleNumber);
				MipsSimulator.m.occupied = false;
				MipsSimulator.m.instruction = null;
				//
				MipsSimulator.m.stall=false;
				
			}
			else{
				f1.isBusy = false;
				f1.setInstruction(null);
			}
			passToNextStage(0);
			list.remove(0);


		}
		else{
			instruction = null;
		}


	}


	private void checkPriority(Instruction instruction1, Instruction instruction2) {
		boolean p1=false,p2=false;
		int n1=0,n2=0;
		boolean flag1 = false, flag2=false;
		/*	GET FUNCTIONAL UNIT		*/
		FunctionalUnit f1 = getFunctionalUnit(instruction1);
		if(f1.getClass().equals(ReadFile.iu.getClass())){
			p1 = true;
			n1 = 1/*ReadFile.memCC*/;
			flag1 = true;
		}
		else{
			p1 = f1.isPipelined();
			n1 = f1.getNoOfCycles();
		}

		FunctionalUnit f2 = getFunctionalUnit(instruction2);
		if(f2.getClass().equals(ReadFile.iu.getClass())){
			p2 = true;
			n2 = 1;//ReadFile.memCC;
			flag2  = true;
		}
		else{
			p2 = f2.isPipelined();
			n2 = f2.getNoOfCycles();
		}


		/*	CHECK FOR PIPELINE	*/
		if(!p1 && p2){
			list.add(instruction1);
			MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction2)] = 1;
			instruction2.hazard[3] = 'Y';
			if(flag2){
				//MipsSimulator.m.stall = true;
			}
			else{
				f2.setStall(true);
				f2.setRemoved(instruction2);
				
			}
			return;
		}
		else if(p1 && !p2){
			list.add(instruction2);
			MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction1)] = 1;
			instruction1.hazard[3] = 'Y';
			if(flag1){
				//MipsSimulator.m.stall = true;
			}
			else{
				f1.setStall(true);
				f1.setRemoved(instruction1);
			}
			return;
		}

		/*	CHECK FOR NO OF CYCLES	*/
		if(n1 > n2){
			list.add(instruction1);
			MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction2)] = 1;
			instruction2.hazard[3] = 'Y';
			if(flag2){
				//MipsSimulator.m.stall = true;
			}
			else{
				f2.setStall(true);
				f2.setRemoved(instruction2);
			//	MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction2)] = 1;
			}
			return;
		}
		else if(n1 < n2){
			list.add(instruction2);
			MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction1)] = 1;
			instruction1.hazard[3] = 'Y';
			if(flag1){
				//MipsSimulator.m.stall = true;
			}
			else{
				f1.setStall(true);
				f1.setRemoved(instruction1);
			}
			return;
		}
		else{
			if(instruction1.clckCyclePosition[0] > instruction2.clckCyclePosition[0]){
				list.add(instruction1);
				MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction2)] = 1;
				instruction2.hazard[3] = 'Y';
				if(flag2){
					MipsSimulator.m.stall = true;
				}
				else{
					f2.setStall(true);
					f2.setRemoved(instruction2);
				}
				return;
			}
			else{
				list.add(instruction2);
				MipsSimulator.d.struct[ReadFile.instructionList.indexOf(instruction1)] = 1;
				instruction1.hazard[3] = 'Y';
				if(flag1){
					MipsSimulator.m.stall = true;
				}
				else{
					f1.setStall(true);
					f1.setRemoved(instruction1);
				}
				return;
			}
		}

	}


	private FunctionalUnit getFunctionalUnit(Instruction instruction) {
		switch (instruction.getName()) {
		case "DADD":
		case "DADDI":
		case "DSUB":
		case "DSUBI":
		case "AND":
		case "ANDI":
		case "OR":
		case "ORI":
		case "LW":
		case "SW":
		case "L.D":
		case "S.D":
			return ReadFile.iu;

		case "ADD.D":
		case "SUB.D":
			return ReadFile.au;

		case "DIV.D":
			return ReadFile.du;

		case "MUL.D":
			return ReadFile.mu;

		default:return null;
		}

	}


	@Override
	public void passToNextStage(int cycleNumber) {
		MipsSimulator.w.instruction = MipsSimulator.e.instruction;
		if(getFunctionalUnit(instruction).getClass().equals(ReadFile.iu.getClass())){
			MipsSimulator.m.stall=false;
		}
		MipsSimulator.e.instruction = null;
		
	}


}
