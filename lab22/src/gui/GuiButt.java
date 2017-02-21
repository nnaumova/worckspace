package gui;

import ru.mail.naumova.Fraction;
import ru.mail.naumova.Calculator;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GuiButt extends JFrame {

	private static final long serialVersionUID = 1L;
	JTextArea display = new JTextArea();
	
	

	GuiButt() {
		setTitle("Калькулятор");
		setBounds(300, 300, 300, 300);
		
		JPanel buttonPanel = new JPanel(new GridLayout(4, 3));
		JPanel buttonPane2 = new JPanel(new GridLayout(3, 2));
		
		JButton button0 = new JButton("0");
		JButton button1 = new JButton("1");
		JButton button2 = new JButton("2");
		JButton button3 = new JButton("3");
		JButton button4 = new JButton("4");
		JButton button5 = new JButton("5");
		JButton button6 = new JButton("6");
		JButton button7 = new JButton("7");
		JButton button8 = new JButton("8");
		JButton button9 = new JButton("9");
		JButton buttonSum = new JButton("+");
		JButton buttonBack = new JButton("C");
		JButton buttonDivide = new JButton(":");
		JButton buttonSub = new JButton("-");
		JButton buttonMul = new JButton("*");
		JButton buttonStart = new JButton("=");
		JButton buttonFr = new JButton("/");
		JButton buttonR = new JButton("(");
		JButton buttonL = new JButton(")");

		ActionListener actionListener = new ButtonListener();
		button0.setActionCommand("0");
		button1.setActionCommand("1");
		button2.setActionCommand("2");
		button3.setActionCommand("3");
		button4.setActionCommand("4");
		button5.setActionCommand("5");
		button6.setActionCommand("6");
		button7.setActionCommand("7");
		button8.setActionCommand("8");
		button9.setActionCommand("9");
		
		buttonFr.setActionCommand("/");
		buttonSum.setActionCommand("+");
		buttonMul.setActionCommand("*");
		buttonDivide.setActionCommand(":");
		buttonSub.setActionCommand("-");
		buttonR.setActionCommand("(");
		buttonL.setActionCommand(")");
		
		button0.addActionListener(actionListener);
		button1.addActionListener(actionListener);
		button2.addActionListener(actionListener);
		button3.addActionListener(actionListener);
		button4.addActionListener(actionListener);
		button5.addActionListener(actionListener);
		button6.addActionListener(actionListener);
		button7.addActionListener(actionListener);
		button8.addActionListener(actionListener);
		button9.addActionListener(actionListener);
		
		buttonFr.addActionListener(actionListener);
		buttonSum.addActionListener(actionListener);
		buttonMul.addActionListener(actionListener);
		buttonDivide.addActionListener(actionListener);
		buttonSub.addActionListener(actionListener);
		buttonR.addActionListener(actionListener);
		buttonL.addActionListener(actionListener);

		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String temp = display.getText();
				display.setText(temp.substring(0, temp.length() - 1));
			}
		});

				
		buttonStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Calculator wer = new Calculator();
					String result2 = display.getText();
					Fraction result1 = wer.calc(result2);
					display.setText(result1.toString());
				} catch (ArithmeticException exp) {
					display.setText("ошибка, деление на ноль");
				} catch (EmptyStackException exp) {
					display.setText("ошибка, неверно расставлены скобки");
				} catch (NumberFormatException exp) {
					display.setText("ошибка, недопустимые символы");
				} catch (Exception exp) {
					display.setText("ошибка, проверьте правильность ввода");
				}

			}
		});
		setLayout(new BorderLayout());
		add(display, BorderLayout.NORTH);
		add(buttonPanel, BorderLayout.CENTER);
		add(buttonPane2, BorderLayout.LINE_END);
		add(buttonStart, BorderLayout.SOUTH);

		buttonPanel.add(button1);
		buttonPanel.add(button2);
		buttonPanel.add(button3);
		buttonPanel.add(button4);
		buttonPanel.add(button5);
		buttonPanel.add(button6);
		buttonPanel.add(button7);
		buttonPanel.add(button8);
		buttonPanel.add(button9);
		buttonPanel.add(button0);
		buttonPanel.add(buttonBack);
		buttonPanel.add(buttonFr);
		buttonPane2.add(buttonSum);
		buttonPane2.add(buttonSub);
		buttonPane2.add(buttonMul);
		buttonPane2.add(buttonDivide);
		buttonPane2.add(buttonR);
		buttonPane2.add(buttonL);
		
		

		setVisible(true);
	}

	public class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			display.setText(display.getText() + e.getActionCommand());
		}
	}

	public static void main(String[] args) {
		new GuiButt();
	}
}
