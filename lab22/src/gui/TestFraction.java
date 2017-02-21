package gui;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;



import ru.mail.naumova.Fraction;
public class TestFraction {

	
	@Test(expected = ArithmeticException.class)
	public void testExc() {

		Fraction fr = new Fraction(50, 0);
		assertEquals("50/0",fr.toString());
		
	}
	
	@Test(expected = NumberFormatException.class)
	public void testEx() {

		Fraction fr = new Fraction("ff");
		assertEquals("50/0",fr.toString());
		
	}
	@Test
	public void testAdd() {
		Fraction a, b;
		a = new Fraction(6, 3);
		b = new Fraction(2, 3);
		Fraction result = a.add(b);
		assertTrue(result.equals(new Fraction("8/3")));
		
	}
	@Test
	public void testAdd2() {
		Fraction a, b;
		a = new Fraction(15, 6);
		b = new Fraction(0);
		Fraction result = a.add(b);
		assertTrue(result.equals(new Fraction("5/2")));
		
	}

	
	@Test
	public void testSub() {
		Fraction a, b;
		a = new Fraction(1);
		b = new Fraction(2, 3);
		Fraction result = a.sub(b);
		assertTrue(result.equals(new Fraction("1/3")));
		
	}

	
	@Test
	public void testMult() {
		Fraction a, b;
		a = new Fraction("1/3");
		b = new Fraction("0");
		Fraction result = new Fraction(0);
		Fraction c = a.mult(b);
		assertTrue(result.equals(c));
		
	}

	@Test
	public void testDiv() {
		Fraction a, b;
		a = new Fraction(1, 3);
		b = new Fraction(2, 3);
		Fraction result = new Fraction(1, 2);
		Fraction c = a.div(b);
		assertTrue(result.equals(c));
	}
		@Test
		public void testDiv2() {
			Fraction a, b;
			a = new Fraction(2);
			b = new Fraction(14);
			Fraction result = new Fraction(2, 14);
			Fraction c = a.div(b);
			assertTrue(result.equals(c));
		
	}
	@Test(timeout=100)
	public void testTime() {
		Fraction a, b;
		a = new Fraction("22222/242422");
		b = new Fraction("93639/3693");
		Fraction result = new Fraction(10000000, 10000);
		Fraction c = a.div(b);
		assertTrue(result.equals(c));
		
	}
	@Ignore
	@Test
	public void testIgnor() {
		Fraction a, b;
		a = new Fraction("10/5");
		b = new Fraction("9/3");
		Fraction result = new Fraction(5);
		Fraction c = a.add(b);
		assertTrue(result.equals(c));
		
	}


}

