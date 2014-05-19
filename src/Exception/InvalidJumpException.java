package Exception;

import Main.Utility;

@SuppressWarnings("serial")
public class InvalidJumpException extends Exception{
	private String message ="Error: Invalid instruction: missing label";
	private int lineNumber;
	//EDIT THIS IN J.JAVA
	public InvalidJumpException(int instructionCount) {
		lineNumber = instructionCount;
	}

	

	public String getmMessage() {
		Utility.writeException(message+ " at line number "+lineNumber);
		 message = message+ " Line["+lineNumber+"]";
		 return message;
	}
}
