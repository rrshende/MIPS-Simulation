package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import InstructionClasses.ADDD;
import InstructionClasses.AND;
import InstructionClasses.ANDI;
import InstructionClasses.BEQ;
import InstructionClasses.BNE;
import InstructionClasses.DADD;
import InstructionClasses.DADDI;
import InstructionClasses.DIVD;
import InstructionClasses.DSUB;
import InstructionClasses.DSUBI;
import InstructionClasses.HLT;
import InstructionClasses.Instruction;
import InstructionClasses.J;
import InstructionClasses.LD;
import InstructionClasses.LW;
import InstructionClasses.MULD;
import InstructionClasses.OR;
import InstructionClasses.ORI;
import InstructionClasses.SD;
import InstructionClasses.SUBD;
import InstructionClasses.SW;

public class Utility {
	static ArrayList<Instruction> list = new ArrayList<>();

	public static void writeOutputFile(int end, int begin){

		while(end>begin){
			Instruction i=null;
			Instruction inew = ReadFile.instructionList.get(begin);
			list.add(inew);
			switch(inew.getName()){
			case "LW" : 
				i = new LW("LW",inew.getArg1(),inew.getrName());
				break;
			case "L.D" : 	
				i = new LD("L.D",inew.getArg1(),inew.getrName());
				break;
			case "SW" : 
				i = new SW("SW",inew.getArg1(),inew.getrName());
				break;
			case "S.D" : 	
				i = new SD("S.D",inew.getArg1(),inew.getrName());
				break;
			case "ADD.D" : 
				i = new ADDD("ADD.D",inew.getArg1(),inew.getrName());
				break;
			case "SUB.D" : 	
				i = new SUBD("SUB.D",inew.getArg1(),inew.getrName());
				break;
			case "MUL.D" : 
				i = new MULD("MUL.D",inew.getArg1(),inew.getrName());
				break;
			case "DIV.D" : 	
				i = new DIVD("DIV.D",inew.getArg1(),inew.getrName());
				break;
			case "DADD" : 
				i = new DADD("DADD",inew.getArg1(),inew.getrName());
				break;
			case "DADDI" : 	
				i = new DADDI("DADDI",inew.getArg1(),inew.getrName());
				break;
			case "DSUB" : 
				i = new DSUB("DSUB",inew.getArg1(),inew.getrName());
				break;
			case "DSUBI" : 	
				i = new DSUBI("DSUBI",inew.getArg1(),inew.getrName());
				break;
			case "AND" : 
				i = new AND("AND",inew.getArg1(),inew.getrName());
				break;
			case "ANDI" : 	
				i = new ANDI("ANDI",inew.getArg1(),inew.getrName());
				break;
			case "OR" : 
				i = new OR("OR",inew.getArg1(),inew.getrName());
				break;
			case "ORI" : 	
				i = new ORI("ORI",inew.getArg1(),inew.getrName());
				break;
			case "BEQ" : 
				i = new BEQ("BEQ",inew.getArg1(),inew.getrName());
				break;
			case "BNE" : 	
				i = new BNE("BNE",inew.getArg1(),inew.getrName());
				break;
			case "HLT" : 
				i = new HLT("HLT",inew.getArg1(),inew.getrName());
				break;
			case "J" : 	
				i = new J("J",inew.getArg1(),inew.getrName());
				break;
			default://TODO throw exception
				break;

			}
			//System.out.println("Begin: "+begin);
			//System.out.println("Arraylist size"+ReadFile.instructionList.size());
			ReadFile.instructionList.add(begin, i);
			ReadFile.instructionList.remove(begin+1);
			begin++;
		}

		/*try {

			File file = new File("C:\\Users\\Rohit Shende\\Desktop\\Output.txt");
			String s ="";
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				s = "Instruction      \tFT\tID\tEX\tWB\tRAW\tWAR\tWAW\tStruct";
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);

			//System.out.println("End "+end+" "+begin);
			while(end>begin){
				bw.append(s);
				bw.newLine();

				s="";
				s=ReadFile.instructionList.get(begin).rName;
				if(s.length()<18){
					for(int i=s.length()-1;i<19;i++)
						s=s.concat(" ");

				}
				for(int i=0;i<4;i++)
					s = s+"\t"+ReadFile.instructionList.get(begin).clckCyclePosition[i];

				s = s+"\t"+MipsSimulator.d.raw[begin]+"\t"+"0"+"\t"+MipsSimulator.d.waw[begin]+"\t"+
						MipsSimulator.d.struct[begin];

				begin++;
			}
			bw.append(s);
			bw.close();

			//System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}
	public static void saveToFile() {
		try {

			//File file = new File("C:\\Users\\Rohit Shende\\Desktop\\Output.txt");
			File file = new File("result.txt");
			String s ="";
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
				//s = "Instruction           \t\tFT\tID\tEX\tWB\tRAW\tWAR\tWAW\tStruct";
			}
			s = "Instruction           \t\tFT\tID\tEX\tWB\tRAW\tWAR\tWAW\tStruct";
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),false);
			BufferedWriter bw = new BufferedWriter(fw);


			for(int x = 0;x<list.size();x++){
				bw.append(s);
				bw.newLine();

				s="";
				s=list.get(x).getrName();
				if(s.length()<28){
					for(int i=s.length()-1;i<29;i++)
						s=s.concat(" ");

				}
				for(int i=0;i<4;i++)
					s = s+"\t"+list.get(x).getClckCyclePosition()[i];

				s = s+"\t"+list.get(x).getHazard()[0]+"\t"+list.get(x).getHazard()[1]+"\t"+list.get(x).getHazard()[2]+"\t"+
						list.get(x).getHazard()[3];


			}
			bw.append(s);

			//ADD HIT AND MISS
			s="";
			s = s+"\nTotal number of requests to instruction cache "+(MipsSimulator.hitCnt+MipsSimulator.missCnt)+"\n";
			s = s+"Total number of instruction cache hit "+MipsSimulator.hitCnt+"\n";
			s = s+"Total number of requests to data cache "+(MipsSimulator.m.hitCnt+MipsSimulator.m.misstCnt)+"\n";
			s = s+"Total number of data cache hit "+(MipsSimulator.m.hitCnt);
			bw.append(s);
			bw.close();



		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void writeException(String string) {
		try {

			//File file = new File("C:\\Users\\Rohit Shende\\Desktop\\Output.txt");
			File file = new File("result.txt");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();

			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.append(string);


			bw.close();



		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}