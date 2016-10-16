package edo.times_tables;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.ComboBox;
import com.googlecode.lanterna.gui2.Component;
import com.googlecode.lanterna.gui2.DefaultWindowManager;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.TextBox;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class NumberQuiz {
	
   private static final int TOTAL_SUMS = 10;
	
   public static void main(String[] args) throws IOException {
	   
	   final BasicWindow window = new BasicWindow();

	   List<SimpleIntegerSum> sums = getSums();
	   
	   // Setup terminal and screen layers
	   Terminal terminal = new DefaultTerminalFactory().createTerminal();
	   Screen screen = new TerminalScreen(terminal);
	   screen.startScreen();
        
       // Create panel to hold components
       Panel panel = new Panel();
       panel.setLayoutManager(new GridLayout(6));
       
       for (int i = 0; i < sums.size(); i++) {

	       SimpleIntegerSum sum = sums.get(i);
	       int indToAnswer = sum.getIndToHide();
	        
	       TextBox txtNum1 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);
	       if(indToAnswer != 0) {
	    	   txtNum1.setText(sum.getFirst()).setReadOnly(true);
	       }
	       
	       Label op = new Label(Operation.getPrintValue(sum.getOperation()));
	       panel.addComponent(op);
	       
	       TextBox txtNum2 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);
	       if(indToAnswer != 1) {
	    	   txtNum2.setText(sum.getSecond()).setReadOnly(true);
	       }
	       	
	       	panel.addComponent(new Label("="));
	
	       	TextBox txtNum3 = new TextBox().setValidationPattern(Pattern.compile("[0-9]*")).addTo(panel);
	       	if(indToAnswer != 2) {
	       		txtNum3.setText(sum.getAnswer()).setReadOnly(true);
	       	}
	       	
	       	panel.addComponent(new Label(""));
   		}
       
       panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
       new Button("Check Answers!", new Runnable() {
            public void run() {
            	Panel p = (Panel) window.getComponent();
            	Collection<Component> components = p.getChildren();
                if(components.size() % 6 != 0) {
                	throw new RuntimeException("Uh oh!");
                }
                
                int total = 0;
                
                Iterator<Component> iComponents = components.iterator();
                while (iComponents.hasNext() && total < TOTAL_SUMS) {
                	int num1 = Integer.valueOf(((TextBox) iComponents.next()).getText());
                	Operation oper = Operation.getOperation(((Label) iComponents.next()).getText());
                	int num2 = Integer.valueOf(((TextBox) iComponents.next()).getText());
                	String dummy = ((Label) iComponents.next()).getText();
                	int answ = Integer.valueOf(((TextBox) iComponents.next()).getText());
                	System.err.println(num1 + " " + oper + " " + num2 + " " + dummy + " " + answ);
                	Label answerLabel = ((Label) iComponents.next());
                	if(checkAnswer(num1, oper, num2, answ)) {
                		answerLabel.setText("CORRECT");
                	}
                	else {
                		answerLabel.setText("WRONG");
                	}
                	total++;
				}
            }

			private boolean checkAnswer(int num1, Operation oper, int num2, int answ) {
				switch(oper) {
					case ADD      : return (num1+num2) == answ;
					case SUBTRACT : return (num1-num2) == answ;
					case MULTIPLY : return (num1*num2) == answ;
					case DIVIDE   : return (num1/num2) == answ; 
					default       : return false;
				}
			}
        }).addTo(panel);
        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
        panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));

        // window to hold the panel
        window.setComponent(panel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }

	private static List<SimpleIntegerSum> getSums() {
		SumGenerator gen = new SumGenerator(TOTAL_SUMS, 12, Arrays.asList(Operation.MULTIPLY), true);
		gen.generateTest();
		return gen.getSums();
	}
}
