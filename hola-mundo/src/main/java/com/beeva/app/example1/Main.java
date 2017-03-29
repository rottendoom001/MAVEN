package com.beeva.app.example1;

import com.beeva.app.calculadora.Calculadora;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calculadora calc = new Calculadora();
		
		for (int i = 0 ; i< args.length; i++){
			System.out.println( i + " >> " + args[i]);
		}
		
		if (args[0].compareTo("suma") == 0){
			System.out.println("resultado = " + calc.suma(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
		} else if (args[0].compareTo("resta") == 0){
			System.out.println("resultado = " + calc.resta(Integer.parseInt(args[1]), Integer.parseInt(args[2])));
		} else {
			System.out.println("/////// OPCIÓN INVÁLIDA ////////");
		}
	}

}
