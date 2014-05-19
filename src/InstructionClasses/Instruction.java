package InstructionClasses;


public abstract class Instruction {
	protected String name;
	protected String arg1[];
	public int clckCyclePosition[];
	public char hazard[];
	protected int result;
	protected int destinationRegister,sourceRegister1,sourceRegister2;
	protected char cDestinationRegister;
	protected int dValue,sValue1,sValue2;
	protected int immediateValue;
	protected String label;
	protected int jumpTo;
	protected int delay;
	protected String rName;
	protected boolean checkedCycle;

	public Instruction(String opcode, String[] operands,String r) {
		name =opcode;
		arg1  = operands;
		clckCyclePosition = new int[6];
		hazard = new char[]{'N','N','N','N'};
		result= dValue= sValue1 =sValue2 = immediateValue =delay=0;
		label="";
		rName=r;
		checkedCycle=false;
		jumpTo=-1;
	}

	protected void display(){
		System.out.println("Instruction Name: "+name);
		for(int i=0;i<arg1.length;i++){
			System.out.println("Instruction Arg: "+arg1[i]);
		}
	}

	public abstract void executeInstruction();
	public abstract void lockDestination();
	public abstract void unlockDestination();
	public abstract void getRegisters();
	public abstract void storeResult();
	public abstract void extractValuesFromRegisterInDecode();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getArg1() {
		return arg1;
	}

	public void setArg1(String[] arg1) {
		this.arg1 = arg1;
	}

	public int[] getClckCyclePosition() {
		return clckCyclePosition;
	}

	public void setClckCyclePosition(int[] clckCyclePosition) {
		this.clckCyclePosition = clckCyclePosition;
	}

	public char[] getHazard() {
		return hazard;
	}

	public void setHazard(char[] hazard) {
		this.hazard = hazard;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public int getDestinationRegister() {
		return destinationRegister;
	}

	public void setDestinationRegister(int destinationRegister) {
		this.destinationRegister = destinationRegister;
	}

	public int getSourceRegister1() {
		return sourceRegister1;
	}

	public void setSourceRegister1(int sourceRegister1) {
		this.sourceRegister1 = sourceRegister1;
	}

	public int getSourceRegister2() {
		return sourceRegister2;
	}

	public void setSourceRegister2(int sourceRegister2) {
		this.sourceRegister2 = sourceRegister2;
	}

	public char getcDestinationRegister() {
		return cDestinationRegister;
	}

	public void setcDestinationRegister(char cDestinationRegister) {
		this.cDestinationRegister = cDestinationRegister;
	}

	public int getdValue() {
		return dValue;
	}

	public void setdValue(int dValue) {
		this.dValue = dValue;
	}

	public int getsValue1() {
		return sValue1;
	}

	public void setsValue1(int sValue1) {
		this.sValue1 = sValue1;
	}

	public int getsValue2() {
		return sValue2;
	}

	public void setsValue2(int sValue2) {
		this.sValue2 = sValue2;
	}

	public int getImmediateValue() {
		return immediateValue;
	}

	public void setImmediateValue(int immediateValue) {
		this.immediateValue = immediateValue;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getJumpTo() {
		return jumpTo;
	}

	public void setJumpTo(int jumpTo) {
		this.jumpTo = jumpTo;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public String getrName() {
		return rName;
	}

	public void setrName(String rName) {
		this.rName = rName;
	}

	public boolean isCheckedCycle() {
		return checkedCycle;
	}

	public void setCheckedCycle(boolean checkedCycle) {
		this.checkedCycle = checkedCycle;
	}
}
