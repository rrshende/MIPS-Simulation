package Main;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Cache.ICache;
import Exception.InCorrectInstructionFormat;
import Exception.InvalidConfigFile;
import Stages.*;


public class MipsSimulator {


	private  static String PATH_INSTRUCTION_FILE = "C:\\Users\\Rohit Shende\\Desktop\\inst.txt";
	private static  String PATH_CONFIG_FILE = "C:\\Users\\Rohit Shende\\Desktop\\config.txt";
	private static  String PATH_DATA_FILE = "C:\\Users\\Rohit Shende\\Desktop\\data.txt";
	private static String PATH_REGISTER_FILE = "C:\\Users\\Rohit Shende\\Desktop\\reg.txt";
	private static int  cycle=1;
	public static boolean isFreeRegister[][] = new boolean[2][32];//0 = R 1 = F
	public static Fetch f = new Fetch(false);
	public static Decode d = new Decode(false);
	public static Execute e = new Execute(false);
	public static Memory m = new Memory(false);
	public static WriteBack w = new WriteBack(false);
	public static HashMap<String, ArrayList<String>> instFU = new HashMap<String, ArrayList<String>>();
	public static ICache instCache[];
	public static int hitCnt=0;
	public static int missCnt=0;
	public static int cyclesInIcache=0;
	public static boolean busOccByIcache=false;
	public static int delayDueToDcacheWait=0;
	static boolean  flagFORCACHE=false;

	public static void main(String[] args) {

		if(args.length!=5){
			System.out.println("USAGE: simulator inst.txt data.txt reg.txt config.txt result.txt");
			Utility.writeException("USAGE: simulator inst.txt data.txt reg.txt config.txt result.txt");
			System.exit(0);
		}
		PATH_INSTRUCTION_FILE=args[0];
		PATH_CONFIG_FILE=args[3];
		PATH_DATA_FILE=args[1];
		PATH_REGISTER_FILE=args[2];
		for(int i=0;i<2;i++)
			for(int j=0;j<32;j++)
				isFreeRegister[i][j] = true;

		//SET ICACHE BLOCKS
		instCache = new ICache[4];

		instCache[0] = new ICache();
		instCache[1] = new ICache();
		instCache[2] = new ICache();
		instCache[3] = new ICache();

		ReadFile readClass = new ReadFile();

		try {

			ArrayList<String> list = new ArrayList<String>();
			list.add("ADD.D");
			list.add("SUB.D");
			instFU.put("AdditionUnit", list);
			list.removeAll(list);

			list.add("DADD");
			list.add("DADDI");
			list.add("DSUB");
			list.add("DSUBI");
			list.add("AND");
			list.add("ANDI");
			list.add("OR");
			list.add("ORI");
			list.add("LW");
			list.add("L.D");
			list.add("SW");
			list.add("S.D");
			instFU.put("IntegerUnit", list);
			list.removeAll(list);

			list.add("MUL.D");
			instFU.put("MultiplicationUnit", list);
			list.removeAll(list);

			list.add("DIV.D");
			instFU.put("DivionUnit", list);
			list.removeAll(list);

			readClass.read(PATH_CONFIG_FILE,false,2);
			readClass.read(PATH_REGISTER_FILE,false,3);
			readClass.read(PATH_DATA_FILE,true,4);
			//INSTRUCTION FILE MUST BE THE LAST ONE TO READ
			readClass.read(PATH_INSTRUCTION_FILE,false,1);
			readClass.setFunctionalUnitParameters();
			/*
			 * 1	Instruction
			 * 2	Config
			 * 3	Reg
			 * 4	Data
			 * */
			/*for (int i = 0; i < ReadFile.instructionList.size(); i++) {
				//System.out.println("Instruction "+i);
				ReadFile.instructionList.get(i).display();
			}*/

			/*if(ReadFile.iu.getClass().equals(ReadFile.iu.getClass()))
				System.out.println("YES");
			else
				System.out.println("NO");*/
			//readClass.displayAllParsedData();

			simulate();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.getMessage();
		} catch (InCorrectInstructionFormat e) {
			System.out.println(e.getmMessage());
		} catch (InvalidConfigFile e) {
			System.out.println(e.getmMessage());
		}


	}

	private static void simulate() {
		while(true){

			if(cycle>7){
				//System.out.println();
			}
			w.processInstruction(cycle);
			m.processInstruction(cycle);

			e.processInstruction(cycle);

			d.processInstruction(cycle);
			if(!f.halt)
				getNextInstruction();

			f.processInstruction(cycle);
			if(f.halt){
				//break;
				//Utility.writeOutputFile(f.fCnt,MipsSimulator.d.whereToJump);
				if(f.getInstruction()==null && d.getInstruction()==null && e.getInstruction()==null && m.getInstruction()==null &&
						w.getInstruction()==null && ReadFile.iu.getInstruction()==null &&  ReadFile.au.getInstruction()==null && 
						ReadFile.mu.isEmptyList() && ReadFile.du.isEmptyList() && ReadFile.au.isEmptyList()
						&& ReadFile.mu.getInstruction()==null &&  ReadFile.du.getInstruction()==null){
					//System.out.println("print from Mips");
					Utility.writeOutputFile(f.fCnt,MipsSimulator.d.whereToJump);
					Utility.saveToFile();
					break;
				}
			}
			cycle++;
			//displayStatus();
		}

		//display result
		for(int j=0;j<ReadFile.instructionList.size();j++){
			//System.out.println("Name "+ReadFile.instructionList.get(j).name);
			for(int i=0; i<4;i++){
				//System.out.print("\t"+ReadFile.instructionList.get(j).clckCyclePosition[i]);
			}
			//System.out.print("\t"+d.raw[j]+"\t"+d.waw[j]+"\t"+d.struct[j]);
			//System.out.println();
		}

		//
		//System.out.println(m.cacheD.hit);
		//System.out.println(m.cacheD.miss);

	}
	static boolean flag = true;
	private static int referencdeCycle=0;
	public static void getNextInstruction(){
		if(f.isOccupied()){

		}
		else{
			if(flag){
				flag = false;

				if(getFromIcache()){
					//HIT
					cyclesInIcache = ReadFile.IcacheCC;

				}
				else{
					//MISS
					cyclesInIcache = 2*(ReadFile.IcacheCC + ReadFile.memCC);

					if(m.dCacheWantsBus){
						if(!m.busOccByDcache || m.dc==cycle){
							//both started simultanoe and giving proprty to icache
							m.delayDueToBusContention = cyclesInIcache;
						}
						else{
							delayDueToDcacheWait = m.noOfCycles-2;

						}
					}

				}
				referencdeCycle = cycle;

				//logic to cnt hot and miss
				if(cyclesInIcache<=ReadFile.IcacheCC)
					hitCnt++;
				else
					missCnt++;
			}

			/*if(flag==false){
				if(m.dCacheWantsBus){
					if(!m.busOccByDcache || m.dc==cycle)
						m.delayDueToBusContention = cyclesInIcache;
					else{
						delayDueToDcacheWait++;

					}
				}
			}*/


			//if(!m.busOccByDcache || m.dc==cycle){
			if(-cyclesInIcache-delayDueToDcacheWait+cycle-referencdeCycle+1>=0){
				f.setInstruction(ReadFile.instructionList.get(f.fCnt));
				flag = true;
				cyclesInIcache=0;
				referencdeCycle=0;
				delayDueToDcacheWait=0;
				flagFORCACHE=false;
			}
		}

	}
	//	}

	private static boolean getFromIcache() {
		int block = (((f.fCnt*4) >> 4) & 3);
		int tag = (((f.fCnt*4) >> 6) & 3);
		if(instCache[block].vBit){
			if(instCache[block].tag==tag){
				return true;
			}
			else{
				instCache[block].tag = tag;
				return false;
			}
		}
		else{
			instCache[block].tag = tag;
			instCache[block].vBit = true;
			return false;
		}
	}

}
