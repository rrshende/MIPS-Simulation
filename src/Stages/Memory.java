package Stages;

import Cache.Dcache;
import Main.MipsSimulator;
import Main.ReadFile;

public class Memory extends Stage{
	public int noOfCycles;
	public boolean stall;
	public boolean readyToWrite;
	public Dcache cacheD;
	public boolean flag=true;
	public int hitCnt=0;
	public int misstCnt=0;
	public boolean dCacheWantsBus = false;
	public int delayDueToBusContention =0;
	public boolean busOccByDcache = false;
	public int dc=0;
	public boolean falgForLoadStore=false;
	public Memory(boolean flag) {
		super(flag);
		stall=false;
		readyToWrite = false;
		cacheD= new Dcache();
	}

	@Override
	public void processInstruction(int currentCycleNumber) {
		if(instruction!=null){
			if(flag){
				if(!instruction.isCheckedCycle()){
					if(instruction.getName().equalsIgnoreCase("L.D") || instruction.getName().equalsIgnoreCase("LW")){

						if(instruction.getName().equalsIgnoreCase("L.D")){
							//System.out.println("Value in reg "+instruction.sValue1);
							noOfCycles = cacheD.getLCycles(instruction.getsValue1()) + 
									cacheD.getLCycles(instruction.getsValue1()+4);

						}
						else{
							noOfCycles = cacheD.getLCycles(instruction.getsValue1());
						}

						//System.out.println("Returen "+noOfCycles);
						falgForLoadStore=true;
					}
					else if(instruction.getName().equalsIgnoreCase("SW") || instruction.getName().equalsIgnoreCase("S.D")){
						falgForLoadStore=true;

						if(instruction.getName().equalsIgnoreCase("S.D")){
							//System.out.println("Value in reg "+instruction.rName+" "+instruction.sValue2);

							noOfCycles = cacheD.getSCycles(instruction.getsValue2()) + 
									cacheD.getSCycles(instruction.getsValue2()+4);

						}
						else{
							//System.out.println("Value in reg "+instruction.rName+" "+instruction.sValue2);
							noOfCycles = cacheD.getSCycles(instruction.getsValue2());
						}

					}
					else{
						//changed
						noOfCycles=ReadFile.DcacheCC;
					}
					flag = false;
					if(falgForLoadStore){
						if(noOfCycles==ReadFile.DcacheCC){
							hitCnt++;
							//System.out.println(noOfCycles);
							//System.out.println("Hit "+hitCnt);
							//System.out.println("Miss "+misstCnt);
							//System.out.println(instruction.rName);

						}
						else if(noOfCycles==2*ReadFile.DcacheCC){
							hitCnt = hitCnt+2;
							//System.out.println(noOfCycles);
							//System.out.println("Hit "+hitCnt);
							//System.out.println("Miss "+misstCnt);
							//System.out.println(instruction.rName);

						}
						else{
							//if(!MipsSimulator.busOccByIcache){
							dc = currentCycleNumber;
							//misstCnt++;
							dCacheWantsBus = true;
							busOccByDcache = true;
							if(noOfCycles==2*(ReadFile.memCC+ReadFile.DcacheCC)){
								//hitCnt++;
								misstCnt++;
								//System.out.println(noOfCycles);
								//System.out.println("Hit "+hitCnt);
								//System.out.println("Miss "+misstCnt);
								//System.out.println(instruction.rName);
							}
							else if(noOfCycles==2*(ReadFile.memCC+ReadFile.DcacheCC)+ReadFile.DcacheCC){
								hitCnt++;
								misstCnt++;
							}
							else{
								misstCnt = misstCnt+2;
								//System.out.println(noOfCycles);
								//System.out.println("Hit "+hitCnt);
								//System.out.println("Miss "+misstCnt);
								//System.out.println(instruction.rName);
							}
							/*}
					else{
						flag=true;
					}*/
						}
						falgForLoadStore=false;
					}
					instruction.setCheckedCycle(true);
				}
			}
			//change
			if(noOfCycles==7 && (currentCycleNumber - (instruction.clckCyclePosition[1]+instruction.getDelay()+delayDueToBusContention) >noOfCycles)){
				//icache is using bus
			}

			if(currentCycleNumber - (instruction.clckCyclePosition[1]+instruction.getDelay()+delayDueToBusContention) >noOfCycles){
				if(MipsSimulator.w.occupied){
					////System.out.println("mem->WB cannot be done");
					this.occupied = true;
				}
				else{
					//LOGIC WHICH FU WILL WB FiRST
					if(!stall){
						readyToWrite = true;

						/*	ADD TO ARRAY LIST	*/
						MipsSimulator.e.list.add(instruction);
						flag = true;
						noOfCycles=0;
						//changed
						delayDueToBusContention=0;
						dCacheWantsBus=false;
						busOccByDcache=false;
						dc=0;
						/*MipsSimulator.w.occupied = true;
						instruction.executeInstruction();
						instruction.clckCyclePosition[2] = currentCycleNumber;
						this.occupied = false;
						MipsSimulator.e.instruction = instruction;
						MipsSimulator.e.passToNextStage(currentCycleNumber);
						//MipsSimulator.e.passToNextStage(ReadFile.iu);
						instruction = null;*/
					}
					else{
						//System.out.println("Stall in memory");

					}
				}
			}
		}
	}

	@Override
	public void passToNextStage(int cycleNumber) {
		// TODO Auto-generated method stub

	}

}
