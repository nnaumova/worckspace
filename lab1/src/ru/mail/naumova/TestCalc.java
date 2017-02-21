package ru.mail.naumova;

import static org.junit.Assert.*;

import java.util.EmptyStackException;

import org.junit.Before;
import org.junit.Test;

public class TestCalc {
	
	private Calculator cal;
	
	@Before
    public void setUp() {
		cal = new Calculator();
    }

	
	@Test(expected= EmptyStackException.class)
	public void test1() {

		String s = "10))(*5"; //неправильные скобки
		
		Fraction a = cal.calc(s);
		Fraction result = new Fraction(50, 1);
		assertTrue(result.equals(a));
		// fail("Not yet implemented");
	}

	@Test(expected= RuntimeException.class)
	public void test2() {
		String s = "d1/п2+1/2"; //ошибка в строке
		
		Fraction a = cal.calc(s);
		Fraction result = new Fraction(1, 1);
		assertTrue(result.equals(a));
		// fail("Not yet implemented");
	}

	@Test
	public void test3() {
	
		 fail("Not yet implemented");
	}

	


}


//Component frame = null;
//JOptionPane.showMessageDialog(frame,"");
