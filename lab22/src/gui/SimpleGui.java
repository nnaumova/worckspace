package gui;

import ru.mail.naumova.Fraction;
import ru.mail.naumova.Calculator;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class SimpleGui extends JFrame {
	private static final long serialVersionUID = 1L;

	JTextField input;
	JLabel result;
	JButton button1;

	SimpleGui() {

		JFrame frame = new JFrame("Calculator");
		frame.setLocation(500, 500);
		frame.setLayout(new FlowLayout());
		frame.setSize(450, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		input = new JTextField(20);
		result = new JLabel();
		button1 = new JButton("=");
		button1.setBackground(new Color(150, 10, 50));
		button1.setForeground(Color.WHITE);
		result.setForeground(new Color(150, 10, 50));

		frame.add(input);
		frame.add(button1);
		frame.add(result);

		frame.setVisible(true);

		// ActionListener listener = new TestActionListener(); //первый вариант
		// button1.addActionListener(listener);

		// button1.addActionListener(new TestActionListener()); //второй вариант

		button1.addActionListener(new ActionListener() { // анонимный слушатель
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String s = input.getText();
					Fraction res = new Fraction();
					Calculator c = new Calculator();
					res = c.calc(s);
					result.setText(res.toString());

				} catch (ArithmeticException exp) {
					result.setText("ошибка, деление на ноль");
				} catch (EmptyStackException exp) {
					result.setText("ошибка, неверно расставлены скобки");
				} catch (NumberFormatException exp) {
					result.setText("ошибка, недопустимые символы");
				} catch (Exception exp) {
					result.setText("ошибка, проверьте правильность ввода");
				}

			}

		});

	}

	public class TestActionListener implements ActionListener { // отдельный
																// класс
																// обработки
																// нажатия
		public void actionPerformed(ActionEvent e) {
			try {
				String s = input.getText();
				Fraction res = new Fraction();
				Calculator c = new Calculator();
				res = c.calc(s);
				result.setText(res.toString());

			} catch (ArithmeticException exp) {
				result.setText("ошибка, деление на ноль");
			} catch (EmptyStackException exp) {
				result.setText("ошибка, неверно расставлены скобки");
			} catch (NumberFormatException exp) {
				result.setText("ошибка, недопустимые символы");
			} catch (Exception exp) {
				result.setText("ошибка, проверьте правильность ввода");
			}
		}
	}

	public static void main(String[] args) {

		new SimpleGui();

	}
}
