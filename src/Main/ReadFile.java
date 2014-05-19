package Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Exception.InCorrectInstructionFormat;
import Exception.InvalidConfigFile;
import FunctionalUnit.AdditionUnit;
import FunctionalUnit.DivisionUnit;
import FunctionalUnit.IntegerUnit;
import FunctionalUnit.MultiplicationUnit;
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


public class ReadFile {
	/*	DATA FILE PARAMETERS	*/
	public static int ArrayOfData[] = new int[32];
	public int sizeOfDataArray=0;
	/*	REGISTER FILE PARAMETERS	*/
	public static int ArrayOfRegister[] = new int[32];
	public int sizeOfRegisterArray=0;

	/*	CONFIG FILE PARAMETERS	*/
	public static int adderCC=0,mulCC=0,dividerCC=0,memCC=0,IcacheCC=0,DcacheCC=0;
	public boolean adderPL=false,mulPL=false,dividerPL=false;

	/*	INSTRUCTION FILE PARAMETERS	*/
	public static ArrayList<Instruction> instructionList = new ArrayList<Instruction>();
	public static HashMap<String, Integer> loopMap = new HashMap<>();
	public int instructionCount=0;

	/*	FUNCTIONAL UNITS	*/
	public static IntegerUnit iu;
	public static AdditionUnit au;
	public static MultiplicationUnit mu;
	public static DivisionUnit du;


	public void read(String fileName, boolean isData, int filePointer) throws FileNotFoundException,IOException, InCorrectInstructionFormat, InvalidConfigFile{

		FileReader file = null;
		String line = "";
		BufferedReader reader = null;
		try{
			file = new FileReader(fileName);
			reader = new BufferedReader(file);
			while((line = reader.readLine())!=null){
				switch(filePointer){
				case 1 : parseInstructionFile(line);
				break;
				case 2 : parseConfigFile(line);
				break;
				case 3 : convertBinaryToDecimal(line,isData);
				break;
				case 4 : convertBinaryToDecimal(line,isData);
				break;

				}

			}
		}
		catch(FileNotFoundException fnfe){

			throw fnfe;
		} 
		catch (IOException e) {
			throw e;
		}
		finally{
			if(file!=null){
				try {
					file.close();
					reader.close();
				} catch (IOException e) {
					throw new RuntimeException("IO error");
				}
			}
		}
	}

	private void parseInstructionFile(String line) throws InCorrectInstructionFormat {
		Instruction inst/* = new Instruction()*/ = null;
		String tokens[] = new String[5];
		line = line.trim();
		String name = line;

		/*	CHECK IF IT HAS A LOOP	*/
		if(line.contains(":")){
			int index = line.lastIndexOf(':');
			String loopName = line.substring(0, index);
			line = line.substring(index+1);
			line = line.trim();
			loopMap.put(loopName.trim(), instructionCount);
		}

		try{
			/*	SPLIT FOR RECOGNISING INSTRUCTION	*/
			tokens = line.split("[\\s]", 2);
			String opcode = tokens[0].trim().toUpperCase();

			switch(opcode){
			case "LW" : 
				inst = new LW("LW",getOperands(tokens),name);
				//System.out.println("ROHIT"+inst.name);
				//System.out.println("ROHIT"+inst.arg1[0]);
				//inst.executeInstruction();
				break;
			case "L.D" : 	
				inst = new LD("L.D",getOperands(tokens),name);
				break;
			case "SW" : 
				inst = new SW("SW",getOperands(tokens),name);
				break;
			case "S.D" : 	
				inst = new SD("S.D",getOperands(tokens),name);
				break;
			case "ADD.D" : 
				inst = new ADDD("ADD.D",getOperands(tokens),name);
				break;
			case "SUB.D" : 	
				inst = new SUBD("SUB.D",getOperands(tokens),name);
				break;
			case "MUL.D" : 
				inst = new MULD("MUL.D",getOperands(tokens),name);
				break;
			case "DIV.D" : 	
				inst = new DIVD("DIV.D",getOperands(tokens),name);
				break;
			case "DADD" : 
				inst = new DADD("DADD",getOperands(tokens),name);
				break;
			case "DADDI" : 	
				inst = new DADDI("DADDI",getOperands(tokens),name);
				break;
			case "DSUB" : 
				inst = new DSUB("DSUB",getOperands(tokens),name);
				break;
			case "DSUBI" : 	
				inst = new DSUBI("DSUBI",getOperands(tokens),name);
				break;
			case "AND" : 
				inst = new AND("AND",getOperands(tokens),name);
				break;
			case "ANDI" : 	
				inst = new ANDI("ANDI",getOperands(tokens),name);
				break;
			case "OR" : 
				inst = new OR("OR",getOperands(tokens),name);
				break;
			case "ORI" : 	
				inst = new ORI("ORI",getOperands(tokens),name);
				break;
			case "BEQ" : 
				inst = new BEQ("BEQ",getOperands(tokens),name);
				break;
			case "BNE" : 	
				inst = new BNE("BNE",getOperands(tokens),name);
				break;
			case "HLT" : 
				inst = new HLT("HLT",getOperands(tokens),name);
				break;
			case "J" : 	
				inst = new J("J",getOperands(tokens),name);
				break;
			default://TODO throw exception
				break;

			}
			//System.out.println(inst.name);
			instructionList.add(inst);
			instructionCount++;
		}
		catch(Exception e){
			int x = instructionCount;
			throw new InCorrectInstructionFormat((x+1));
		}
		////System.out.println(loopMap.keySet());
	}

	private String[] getOperands(String[] tokens) throws InCorrectInstructionFormat {
		String argListArray[] = new String[3];
		String arg1[] = new String[3];
		if(!tokens[0].equalsIgnoreCase("HLT")){
			String argList = tokens[1];

			argListArray = argList.trim().split(",");
			for (int i = 0; i < argListArray.length; i++) {
				String arg = argListArray[i] = argListArray[i].trim();
				/*	VALIDATE ARG	*/
				if(arg.charAt(0)!='R' && arg.charAt(0)!='F'){
					if(arg.charAt(0)!='-' && (arg.charAt(0)<'0' || arg.charAt(0)>'9')){
						if(!loopMap.containsKey(arg)){
							if(!tokens[0].equalsIgnoreCase("BEQ")
									&& !tokens[0].equalsIgnoreCase("BNE")
									&& !tokens[0].equalsIgnoreCase("J")){

								throw new InCorrectInstructionFormat(instructionCount+1);
							}
						}
					}
				}
				arg1[i] = argListArray[i];

			}
		}
		//System.out.println("SHOWN");
		/*for (int i = 0; i < arg1.length; i++) {
			System.out.println(arg1[i]);
		}*/
		return arg1;
	}

	public void convertBinaryToDecimal(String lineText, boolean isData){

		int i=0,number=0;
		int length = lineText.length();
		int k = length-1;
		for(i=0;i<length;i++){
			if(lineText.charAt(i)=='1'){
				number += (int) Math.pow(2, k);
			}
			k--;
		}
		if(isData){
			ArrayOfData[sizeOfDataArray] = number;
			sizeOfDataArray++;
		}
		else{
			ArrayOfRegister[sizeOfRegisterArray] = number;
			sizeOfRegisterArray++;
		}
		////System.out.println("Converted Number: "+number);
	}

	public void parseConfigFile(String fileText) throws InvalidConfigFile{
		String array[] = new String[100];
		try{
			fileText = fileText.replace(',',':');
			array = fileText.split(":");

			for(int i=0;i<array.length;i++){
				String key = array[i].trim().toUpperCase();

				switch (key) {
				case "FP ADDER":
					i++;
					adderCC = Integer.parseInt(array[i].trim());
					i++;
					array[i] = array[i].trim();
					adderPL = array[i].equalsIgnoreCase("yes");

					break;

				case "FP MULTIPLIER":
					i++;
					mulCC = Integer.parseInt(array[i].trim());
					i++;
					array[i] = array[i].trim();
					mulPL = array[i].equalsIgnoreCase("yes");
					break;

				case "FP DIVIDER":
					i++;
					dividerCC = Integer.parseInt(array[i].trim());
					i++;
					array[i] = array[i].trim();
					dividerPL = array[i].equalsIgnoreCase("yes");
					break;

				case "MAIN MEMORY":
					i++;
					memCC = Integer.parseInt(array[i].trim());
					break;

				case "I-CACHE":
					i++;
					IcacheCC = Integer.parseInt(array[i].trim());
					break;

				case "D-CACHE":
					i++;
					DcacheCC = Integer.parseInt(array[i].trim());
					break;
				default:
					//throw exception and exit?
					break;
				}
			}


		}
		catch(Exception e){
			throw new InvalidConfigFile(instructionCount+1);
		}
	}

	public void displayAllParsedData(){
		//System.out.println("REGISTER FILE");
		for (int i = 0; i < sizeOfRegisterArray; i++) {
			//System.out.print("\t"+ArrayOfRegister[i]);
		}

		//System.out.println("\nDATA FILE");
		for (int i = 0; i < sizeOfDataArray; i++) {
			//System.out.print("\t"+ArrayOfData[i]);
		}

		//System.out.println("\nCONFIG FILE");
		//System.out.println("Cycle Count\n\tADDER "+adderCC+"\n\tMULT "+mulCC
		//	+"\n\tDIVIDER "+dividerCC+"\n\tmemCC "+memCC+"\n\tIcacheCC"+IcacheCC
		//+"\n\tDcacheCC"+DcacheCC);
		//System.out.println("Pipleline");
		//System.out.println("\tadderPL "+adderPL+"\n\tmulPL "+mulPL+"\n\tdividerPL "+dividerPL);
		//System.out.println("Size "+sizeOfDataArray+"-"+sizeOfRegisterArray);
		//System.out.println("Instruction count "+instructionCount);
	}

	public void setFunctionalUnitParameters(){
		iu = new IntegerUnit(true, 2, false);
		mu = new MultiplicationUnit(mulPL, mulCC, false);
		du = new DivisionUnit(dividerPL, dividerCC, false);
		au = new AdditionUnit(adderPL, adderCC, false);
		//memCC = 2;
	}
}
