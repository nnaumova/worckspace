package ru.mail.naumova;

import java.lang.String;
import java.lang.Comparable;

public final class Fraction implements Comparable<Fraction> {

	private final int numerator;
	private final int denominator;

	public Fraction(int a, int b) {

		if ((a != 0) && (b == 0))
			throw new ArithmeticException("деление на ноль");
		if (a == 0) {
			a = 0;
			b = 1;
		}
		if (b < 0) {
			b *= (-1);
			a *= (-1);
		}
		int x = (Math.abs(a) < b) ? Math.abs(a) : b;
		for (int i = x; i > 1; i--)
			if (a % i == 0 && b % i == 0) {
				a /= i;
				b /= i;
			}

		numerator = a;
		denominator = b;

	}

	public Fraction(int c) {
		this(c, 1);
	}

	public Fraction() {
		this(1, 1);

	}

	public Fraction(Fraction f) {
		this(f.numerator, f.denominator);

	}

	public Fraction(String s) {
		if (!s.contains("/")) {
			numerator = Integer.valueOf(s).intValue();
			denominator = 1;
		} else {
			String[] sArray = s.split("/");
			numerator = Integer.valueOf(sArray[0]).intValue();
			denominator = Integer.valueOf(sArray[1]).intValue();
		}

	}

	public Fraction add(Fraction second) {

		int a = numerator * second.denominator + second.numerator * denominator;
		int b = denominator * second.denominator;
		Fraction temp = new Fraction(a, b);
		return temp;
	}

	public Fraction sub(Fraction second) {
		int a = numerator * second.denominator - second.numerator * denominator;
		int b = denominator * second.denominator;
		Fraction temp = new Fraction(a, b);
		return temp;

	}

	public Fraction mult(Fraction second) {
		int a = numerator * second.numerator;
		int b = denominator * second.denominator;
		Fraction temp = new Fraction(a, b);
		return temp;
	}

	public Fraction div(Fraction second) {
		int a = numerator * second.denominator;
		int b = denominator * second.numerator;
		Fraction temp = new Fraction(a, b);
		return temp;
	}

	public void print() {

		if (denominator == 1)
			System.out.println(numerator);

		else
			System.out.println(numerator + "/" + denominator);
	}

	@Override
	public String toString() {
		if (denominator == 1)
			return String.valueOf(numerator);

		else
			return numerator + "/" + denominator;
	}

	@Override
	public int hashCode() {
		return (int) Math.abs(numerator * denominator);
	}

	public boolean equals(Object o) {

		if (!(o instanceof Fraction))
			return false;
		Fraction f = (Fraction) o;
		if ((numerator == f.numerator) && (denominator == f.denominator))
			return true;
		return false;
	}

	public int compareTo(Fraction f) {

		if ((numerator * f.denominator - f.numerator * denominator) < 0)
			return -1;
		if ((numerator * f.denominator - f.numerator * denominator) > 0)
			return 1;
		return 0;
	}

}