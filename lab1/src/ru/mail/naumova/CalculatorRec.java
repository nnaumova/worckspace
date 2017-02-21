package ru.mail.naumova;

import java.util.Stack;
import java.lang.String;

public class CalculatorRec {
	private final boolean DEBUG = true;


	private String cheque(String input) {
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
					|| input.charAt(i) == '(' || input.charAt(i) == ')' || Calculator.isOperator(input
						.charAt(i)))){
				throw new RuntimeException("недопустимые символы"); //поменять
				}
		}
		if(!checkBr.isEmpty())throw new RuntimeException("скобки");
		
		if (DEBUG)
			System.out.println(input);
return input;
	}
	
	private String upgradeString(String str) {
		
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
		return str;

	}

	public Fraction calc(String s) {
		s = upgradeString(cheque(s));
		Stack<Character> operations = new Stack<Character>();
		Stack<Fraction> fractions = new Stack<Fraction>();
		s += ".";
		for (int i = 0; i < s.length(); i++) {
			if (DEBUG)
				System.out.println(s.charAt(i) + "цикл");

			String strFr = "";
			while (s.charAt(i) != '.'
					&& (Character.isDigit(s.charAt(i)) || s.charAt(i) == '/')) {
				strFr += s.charAt(i);
				if (i < s.length() - 1)
					i++;
			}
			if (strFr != "") {
				Fraction frTemp = new Fraction(strFr);
				fractions.push(frTemp);
				strFr = "";
				if (DEBUG)
					System.out.println(frTemp.toString() + "жробь");
			}

			if (Calculator.isOperator(s.charAt(i))) {
				operations.push(s.charAt(i));

			}

			else if (s.charAt(i) == '(') {
				i++;
				int temp = s.length() - 1;
				for (int j = s.length() - 1; s.charAt(j) != ')'; j--) {
					temp = j;
				}
				String subStr = s.substring(i, temp - 1);
				if (DEBUG)
					System.out.println(subStr + "подстрока");
				fractions.push(calc(subStr));
				if (DEBUG)
					System.out.println("вышли из рекурсии");

				i = temp;

			}

			if (s.charAt(i) == ')' || s.charAt(i) == '.') {
				

				while (!(operations.isEmpty())) {

					Fraction fr2 = fractions.pop();
					Fraction fr1 = fractions.pop();
					if (DEBUG)
						System.out.println("+++ " + operations.peek() + " 111 "
								+ fr2 + " 222 " + fr1);
					Fraction frRes = new Fraction(0, 1);

					switch (operations.pop()) {
					case '+':
						frRes = fr1.add(fr2);
						break;
					case '-':
						frRes = fr2.sub(fr1);
						break;
					case ':':
						frRes = fr2.div(fr1);
						break;
					case '*':
						frRes = fr1.mult(fr2);
						break;
					default:
						break;
					}
					if (DEBUG)
						System.out.println("результат " + frRes.toString());
					fractions.push(frRes);
				}

			}
		}

		Fraction result = fractions.pop();
		if (DEBUG)
			System.out.println(result.toString());
		return result;
	}

}
