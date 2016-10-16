package edo.times_tables;

import java.util.Random;

public enum Operation {
	
	ADD,
	SUBTRACT,
	MULTIPLY,
	DIVIDE;
	
	public static final String getPrintValue(Operation anOperation) {
		switch(anOperation) {
			case ADD      : return "+";
			case SUBTRACT : return "-";
			case MULTIPLY : return "x";
			case DIVIDE   : return String.valueOf('\u00F7'); 
			default       : return "UNKNOWN";
		}
	}
	public static final Operation getOperation(String anOperation) {
		if(anOperation == null || anOperation.length() != 1) {
			return null;
		}
		char o = anOperation.charAt(0);
		
		switch(o) {
			case '+'      : return ADD;
			case '-' 	  : return SUBTRACT;
			case 'x' 	  : return MULTIPLY	;
			case '\u00F7' : return DIVIDE; 
			default       : return null;
		}
	}
	
	public static final Operation randomOperation() {
		int choice = new Random().nextInt(4);
		
		switch (choice) {
		case 0:
			return ADD;
		case 1:
			return SUBTRACT;
		case 2:
			return MULTIPLY;
		default:
			return DIVIDE;
		}
	}
}
