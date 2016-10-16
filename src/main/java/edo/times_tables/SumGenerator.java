package edo.times_tables;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;

public class SumGenerator {
	
	private Random rand;
	private int numberOfQuestions;
	private int maxNumber;
	private List<Operation> lOfOperations;
	private ArrayList<SimpleIntegerSum> sumArray;
	private boolean missingNumbers;
	
	public SumGenerator(int numberOfQuestions, int maxNumber, List<Operation> lOfOperations, boolean missingNumbers) {
		Assert.assertTrue(numberOfQuestions > 1);
		Assert.assertTrue(maxNumber > 1);
		Assert.assertTrue(maxNumber < 100);
		Assert.assertTrue(lOfOperations.size() > 0);

		this.rand = new Random();
		this.numberOfQuestions = numberOfQuestions;
		this.maxNumber = maxNumber;
		sumArray = new ArrayList<SimpleIntegerSum>(numberOfQuestions);
		this.lOfOperations = lOfOperations;
		this.missingNumbers = missingNumbers;
	}

	public void generateTest() {
		
		for (int i = 0; i < numberOfQuestions; i++) {
			int indToHide = (missingNumbers) ? rand.nextInt(3) : -1; 
			sumArray.add(new SimpleIntegerSum(randInt(1, maxNumber), randInt(1, maxNumber), getOperation(), indToHide));
		}
	}
	
	private Operation getOperation() {
		return lOfOperations.get(rand.nextInt(lOfOperations.size()));
	}

	private int randInt(int floor, int ceil){
		return floor + rand.nextInt(ceil - floor);
	}
	
	public List<SimpleIntegerSum> getSums() {
		return sumArray;
	}
	
	public static void main(String[] args){
		SumGenerator gen = new SumGenerator(30, 12, Arrays.asList(Operation.MULTIPLY, Operation.DIVIDE), true);
		gen.generateTest();
		for (SimpleIntegerSum s: gen.getSums()) {
			System.out.println(s.sumWithAnswer());
		}
	}
}
