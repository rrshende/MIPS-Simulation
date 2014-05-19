package Exception;

import Main.Utility;

@SuppressWarnings("serial")
public class InvalidConfigFile extends Exception{
	private String message ="Error: Invalid config file:";
private int lineNumber;
	
	public InvalidConfigFile(int instructionCount) {
		lineNumber = instructionCount;
	}

	public String getmMessage() {
		Utility.writeException(message+ " at line number "+lineNumber);
		return message+ " at line number "+lineNumber;
	}

}
