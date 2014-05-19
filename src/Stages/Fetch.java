package Stages;

import Main.MipsSimulator;
import Main.Utility;

public class Fetch extends Stage{
	public int fCnt=0;
	public boolean halt = false;
	public boolean flagForPrinting;
	int prevJump=0;
	public Fetch(boolean flag) {
		super(flag);
		flagForPrinting = true;
	}


	@Override
	public void processInstruction(int cycleNumber) {
		//check if decode can accept
		if(instruction!=null){
			if(MipsSimulator.d.occupied){
				MipsSimulator.f.occupied = true;
			}
			else{
				fCnt++;
				MipsSimulator.f.instruction.clckCyclePosition[0] = cycleNumber;
				MipsSimulator.f.occupied = false;
				MipsSimulator.f.passToNextStage(cycleNumber);
			}
		}
	}

	@Override
	public void passToNextStage(int cycleNumber) {
		if(!MipsSimulator.d.isJump){
			if(MipsSimulator.d.firstHLT){
				if(instruction.getName().equalsIgnoreCase("HLT")){
					//System.out.println("HLT twice.");
					halt = true;
				}
			}
			else{
				MipsSimulator.d.instruction = MipsSimulator.f.instruction;
			}
		}
		else{
			if(flagForPrinting){
				Utility.writeOutputFile(fCnt,0);
				flagForPrinting = false;
				prevJump = MipsSimulator.d.whereToJump;
			}
			else{
				//System.out.println("Print from Fetch");
				Utility.writeOutputFile(fCnt,prevJump);
				prevJump = MipsSimulator.d.whereToJump;
			}
			fCnt = MipsSimulator.d.whereToJump;
			//System.out.println("Cycle number"+cycleNumber+" Jump to "+fCnt);
			MipsSimulator.d.instruction = null;
			MipsSimulator.d.isJump = false;
		}
		MipsSimulator.f.instruction = null;
	}



}
