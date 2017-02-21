package ru.mail.naumova;

import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

@RunWith(Parameterized.class)
public class TestParser {
	@Parameters
	public static List<Object[]> data() {
		return Arrays.asList(new Object[][] { { "2+2", "4" },
				{ "1-2*3", "-5" }, { "12:3+5*2", "14" },
				{ "1*2*(3-5)*2", "-8" }, { "(100+(10-10)):50", "2" }, //простые числа 0-4
				{ "1/2+1/2", "1" }, { "1/2-1/2", "0" }, { "3/1:2/1+3/2*7/7", "3" },
				{ "5/3*(1/5-2/5)", "-1/3" }, { "8/2:(2/3*(3/2-0/1))-1/3", "11/3" }, //дроби 5-9
				{ "7*(7/5-2/5)-10", "-3" }, { "6/2:(9-21:7+(5-5/2)+1/2)", "1/3" }, //смешанные числа 10-11
				{ "2 - 2 * 3 ", "-4" }, //пробел 12
				{ "-2*3/4", "-3/2" },{ "8/2-(-10/3*(-3:5))", "2" },  //ведущий минус 13-14
				{ "3++", "4" }, { "--5/4", "1/4" }, { "5--*3/2-7/4:14/8", "5" }, { "1--*++5/4+(6+8:2)", "10" }  //унарные операции
		});
		
	}

	private String fInput;
	private String fExpected;

	public TestParser(String input, String expected) {
		fInput = input;
		fExpected = expected;
	}

	@Test
	public void test() {
		Calculator c = new Calculator();
		Fraction result = new Fraction(fExpected);
		assertEquals(result, c.calc(fInput));
	}
}
