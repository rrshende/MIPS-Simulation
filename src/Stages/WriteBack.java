package Stages;


import Main.MipsSimulator;


public class WriteBack extends Stage{
		
	public WriteBack(boolean flag){
		super(flag);
	}

	@Override
	public void processInstruction(int cycleNumber) {
		if(MipsSimulator.w.instruction!=null){
			////System.out.println("cycle "+cycleNumber);
			MipsSimulator.w.instruction.clckCyclePosition[3] = cycleNumber;
			////System.out.println(instruction.name);
			/*for(int i=0; i<4;i++){
				//System.out.print("\t"+MipsSimulator.w.instruction.clckCyclePosition[i]);
			}
			//System.out.println();*/
			MipsSimulator.w.instruction.storeResult();
			MipsSimulator.w.instruction.unlockDestination();
			occupied = false;
			instruction=null;
		}
	}

	

	@Override
	public void passToNextStage(int cycleNumber) {
		//System.out.println("Do nothing");
	}

	

}
