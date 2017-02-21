package gui;


import ru.mail.naumova.Fraction;
import ru.mail.naumova.Calculator;

import java.awt.FlowLayout;
import java.util.EmptyStackException;



import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GuiStream extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	
	JTextField input;
	JLabel result;
	JLabel label;

	GuiStream() {

		JFrame frame = new JFrame("Calculator");
		frame.setLocation(500,500);
		frame.setLayout(new FlowLayout());
		frame.setSize(450, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		input = new JTextField(20);
		result = new JLabel();
		label = new JLabel("=");

		frame.add(input);
		frame.add(label);
		frame.add(result);
		

		frame.setVisible(true);

		
				
	}
				
		

		@Override
	public void run() {
		// TODO Auto-generated method stub
			for(;;){
			try {
				for(int i = 0;i<999999999;i++){};
				String s = input.getText();
				Fraction res = new Fraction();
				Calculator c = new Calculator();
				res = c.calc(s);
				result.setText(res.toString());

			} catch (ArithmeticException exp) {
				result.setText("ошибка, деление на ноль");
			} catch (EmptyStackException exp) {
				result.setText("ошибка, неверно расставлены скобки");
			}catch (NumberFormatException exp) {
				result.setText("ошибка, недопустимые символы");
			}catch (Exception exp) {
				result.setText("ошибка, проверьте правильность ввода");
			}
	}
		}

	public static void main(String[] args) {

		 GuiStream st = new  GuiStream() ;
		 Thread myThready = new Thread(st);	//Создание потока 
	      myThready.start();

	}

	
}
