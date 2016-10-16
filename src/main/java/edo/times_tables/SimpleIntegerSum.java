package edo.times_tables;

public class SimpleIntegerSum {

	private int first;
	private int second;
	private Operation operation;
	private int indToHide;
	
	public SimpleIntegerSum(int aFirstNumber, int aSecondNumber, Operation anOperation, int anIndToHide){
		first = aFirstNumber;
		second = aSecondNumber;
		if(anOperation == Operation.DIVIDE) {
			first *= second; 
		}
		operation = anOperation;
		indToHide = anIndToHide;
	}
	
	public int evaluate() {
		switch(operation) {
			case ADD      : return first + second;
			case SUBTRACT : return first - second;
			case MULTIPLY : return first * second;
			case DIVIDE   : return first / second; 
			default       : return 0;
		}	
	}	
	
	public String sum(){
		StringBuffer buff = new StringBuffer();
		
		if(first < 10) {
			buff.append(' ');
		}
		buff.append(first);
		
		buff.append(' ');
		buff.append(Operation.getPrintValue(operation));
		buff.append(' ');
		
		if(second < 10) {
			buff.append(' ');
		}
		buff.append(second);
		
		buff.append(" = ");
		
		return buff.toString();
	}
	
	public String sumWithAnswer(){
		return sum() + evaluate();
	}
	
	public String missingNumberSum(){
		StringBuffer buff = new StringBuffer();
		
		boolean missFirst = Math.random() < 0.5d;
		
		if(missFirst) {
			buff.append("__");
		}
		else {
			if(first < 10) {
				buff.append(' ');
			}
			buff.append(first);
		}
		
		buff.append(' ');
		buff.append(Operation.getPrintValue(operation));
		buff.append(' ');
		
		if(missFirst) {
			if(second < 10) {
				buff.append(' ');
			}
			buff.append(second);
		}
		else {
			buff.append("__");
		}
		
		buff.append(" = ");
		buff.append(evaluate());
		
		return buff.toString();
	}
	
	public String getFirst() {
		return (indToHide == 0) ? "" : "" + first;
	}
	
	public String getSecond() {
		return (indToHide == 1) ? "" : "" + second;
	}
	
	public String getAnswer() {
		return (indToHide == 2) ? "" : "" + evaluate();
	}
	
	public int getIndToHide() {
		return indToHide;
	}
	
	public Operation getOperation() {
		return operation;
	}
}
