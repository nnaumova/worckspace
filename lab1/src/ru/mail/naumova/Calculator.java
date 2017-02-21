package ru.mail.naumova;

import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.String;


public class Calculator {
	private final boolean DEBUG = false;
	private String myString;
	private Fraction result;
	 private static Logger log = Logger.getLogger(Calculator.class.getName());
	 

	private void cheque(String input) {
		log.info("проверка строки");
		log.fine("то что мы не увидим");
		input = input.replaceAll(" ", "");
		Stack<Character> checkBr = new Stack<Character>();
		for (int i = 0; i < input.length(); i++) {
			if (DEBUG)
				System.out.println(input.charAt(i));
			if (input.charAt(i) == '(')
				checkBr.push(input.charAt(i));
			else if (input.charAt(i) == ')')
				checkBr.pop(); // если скобки стоят неправильно
								// EmptyStackException

			else if (!(Character.isDigit(input.charAt(i)) || input.charAt(i) == '/'
					|| input.charAt(i) == '(' || input.charAt(i) == ')' || isOperator(input
						.charAt(i)))){
				throw new NumberFormatException("недопустимые символы");
				}
		}
		if(!checkBr.isEmpty())throw new RuntimeException("скобки");
		myString = input;
		if (DEBUG)
			System.out.println(input);

	}

	private void upgradeString() {
		log.info("преобразование унарных операторов и ведущего мунуса");
		String str = myString;
		String tempBegin = null, tempMid = null, tempEnd = null ;
		if ((str.charAt(0) == '-' || str.charAt(0) == '+') && (str.charAt(1) != '-' && str.charAt(0) != '+'))
			str = "0" + str;
		for (int i = 0; i < str.length() - 1; i++) {
			if (DEBUG)
				System.out.println(str.charAt(i));

			if (str.charAt(i) == '('
					&& (str.charAt(i + 1) == '-' || str.charAt(i + 1) == '+')) {
				tempBegin = str.substring(0, i + 1);
				tempEnd = str.substring(i + 1);
				str = tempBegin + "0" + tempEnd;
			}
			if ((str.charAt(i) == '+' && str.charAt(i + 1) == '+')
					|| (str.charAt(i) == '-' && str.charAt(i + 1) == '-')) {
				if ((i == 0) || Calculator.isOperator(str.charAt(i - 1))) {
					tempBegin = str.substring(0, i);
					for (int j = i+2; j < str.length()
							&& !Calculator.isOperator(str.charAt(j)); j++) {
						tempMid = str.substring(i + 2, j+1);
						tempEnd = str.substring(j+1);
					}
					str = tempBegin + "(" + tempMid + str.charAt(i) + "1)" + tempEnd;
				}
				else if ((i == str.length()-2) || Calculator.isOperator(str.charAt(i +2))) {
					tempEnd = str.substring(i+2);
					for (int j = i-1; j >= 0
							&& !Calculator.isOperator(str.charAt(j)); j--) {
						tempMid = str.substring(j, i+1);
						tempBegin = str.substring(0, j);
					}
					str = tempBegin + "(" + tempMid + "1)" + tempEnd;
				}

			}

		}
		if (DEBUG)
			System.out.println(str);
		myString= str;

	}

	public static boolean isOperator(char c) { // возвращяем тру если один из
												// символов
		// ниже
		return c == '+' || c == '-' || c == '*' || c == ':';
	}

	private static int priority(char op) {
		switch (op) { // при + или - возврат 1, при * / % 2 иначе -1
		case '+':
		case '-':
			return 1;
		case '*':
		case ':':
			return 2;
		case '(':
			return 0;
		default:
			return -1;
		}
	}

	private String toPolish() {
		log.info("перевод в польскую запись");
		
		String polString = null;
		Stack<Character> st = new Stack<Character>();
		for (int i = 0; i < myString.length(); i++) {

			if (Character.isDigit(myString.charAt(i))
					|| myString.charAt(i) == '/') {
				polString += myString.charAt(i);

			} else if (isOperator(myString.charAt(i))) {
				if (Character.isDigit(myString.charAt(i - 1)))
					polString += ',';
				if (st.isEmpty()
						|| (priority((char) st.peek())) < (priority(myString
								.charAt(i)))) {
					st.push(myString.charAt(i));
				} else {
					while (!(st.isEmpty())
							&& (priority((char) st.peek())) >= (priority(myString
									.charAt(i)))) {
						polString += st.pop();
					}
					st.push(myString.charAt(i));
				}
			} else if (myString.charAt(i) == '(')
				st.push(myString.charAt(i));
			else if (myString.charAt(i) == ')') {
				if (Character.isDigit(myString.charAt(i - 1)))
					polString += ',';
				while ((char) st.peek() != '(')
					polString += st.pop();
				st.pop();
			}
		}
		while (!st.isEmpty())
			polString += st.pop();

		if (DEBUG)
			System.out.println(polString);
		return polString;

	}

	public Fraction calc(String s) {

		cheque(s);
		upgradeString();
		String polString = toPolish();
		if (DEBUG)
			System.out.println(polString);
		Stack<Fraction> stak = new Stack<Fraction>();
		String strFr = "";
		for (int i = 0; i < polString.length(); i++) {
			if (DEBUG)
				System.out.println(polString.charAt(i));
			if (Character.isDigit(polString.charAt(i))
					|| polString.charAt(i) == '/') {
				strFr += polString.charAt(i);
			}
			if ((polString.charAt(i) == ',' || isOperator(polString.charAt(i)))
					&& strFr != "") {
				Fraction fr = new Fraction(strFr);
				stak.push(fr);
				strFr = "";
			}

			if (isOperator(polString.charAt(i))) {
				Fraction fr2 = stak.pop();
				Fraction fr1 = stak.pop();
				Fraction frRes = new Fraction(0, 1);
				switch (polString.charAt(i)) {
				case '+':
					frRes = fr1.add(fr2);
					break;
				case '-':
					frRes = fr1.sub(fr2);
					break;
				case ':':
					frRes = fr1.div(fr2);
					break;
				case '*':
					frRes = fr1.mult(fr2);
					break;
				default:
					break;
				}
				stak.push(frRes);
			}

		}
		result = stak.pop();
		if (DEBUG)
			System.out.println(result.toString());

		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try
		{

		Calculator c = new Calculator();
		c.calc("ff");
		}
		catch(RuntimeException e){
		log.log(Level.SEVERE,
				"Не удалось создать строку из за неверного ввода", 
				e);
		}
		// Fraction d = new Fraction(5,0);

	}

}
