package FunctionalUnit;

import InstructionClasses.Instruction;

public abstract class FunctionalUnit {
	protected boolean isPipelined;
	protected int noOfCycles;
	public boolean isBusy;
	protected int unit[];
	protected int exitAfter=0;
	protected Instruction instruction=null;
	protected Instruction removed=null;
	protected boolean readyToWrite;
	protected boolean stall;

	public FunctionalUnit() {
		isPipelined=false;
		noOfCycles =0;
		isBusy=false;
		readyToWrite=false;
		stall=false;
	}

	public boolean isPipelined() {
		return isPipelined;
	}

	public void setPipelined(boolean isPipelined) {
		this.isPipelined = isPipelined;
	}

	public int getNoOfCycles() {
		return noOfCycles;
	}

	public void setNoOfCycles(int noOfCycles) {
		this.noOfCycles = noOfCycles;
	}

	public int[] getUnit() {
		return unit;
	}

	public void setUnit(int[] unit) {
		this.unit = unit;
	}

	public int getExitAfter() {
		return exitAfter;
	}

	public void setExitAfter(int exitAfter) {
		this.exitAfter = exitAfter;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public Instruction getRemoved() {
		return removed;
	}

	public void setRemoved(Instruction removed) {
		this.removed = removed;
	}

	public boolean isReadyToWrite() {
		return readyToWrite;
	}

	public void setReadyToWrite(boolean readyToWrite) {
		this.readyToWrite = readyToWrite;
	}

	public boolean isStall() {
		return stall;
	}

	public void setStall(boolean stall) {
		this.stall = stall;
	}

	public FunctionalUnit(boolean isPipelined,int noOfCycles, boolean isBusy) {
		this.isPipelined = isPipelined;
		this.noOfCycles = noOfCycles;
		this.isBusy = isBusy;
		exitAfter = noOfCycles;
		if(isPipelined)
			this.unit = new int[noOfCycles];
		else
			this.unit = new int[1];

	}

	public abstract void ProcessByFunctionalUnit(int currentCycleNumber);
}
