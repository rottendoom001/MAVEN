package com.beeva.app.calculadora;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculadoraTest {

	@Test
	public void sumaTest() {
		Calculadora cal = new Calculadora();
		int res = cal.suma(10, 10);
		assertEquals("10 + 10 = 20", res, 20);
	}

	@Test
	public void restaTest() {
		Calculadora cal = new Calculadora();
		int res = cal.resta(10,10);
		assertEquals("10 - 10 = 0", res, 0);
	}
}
