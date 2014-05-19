package Stages;

import InstructionClasses.Instruction;

public abstract class Stage {
	protected boolean occupied;
	protected Instruction instruction;

	public Stage(boolean flag) {
		occupied = flag;
		instruction = null;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

	public abstract void processInstruction(int cycleNumber);
	public abstract void passToNextStage(int cycleNumber);
}
