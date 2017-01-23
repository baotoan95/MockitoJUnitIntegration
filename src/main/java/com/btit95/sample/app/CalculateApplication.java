package com.btit95.sample.app;

import com.btit95.sample.services.CalculatorService;

public class CalculateApplication {
	private CalculatorService calculatorService;
	
	public int add(int a, int b) {
		return calculatorService.add(a, b);
	}
	
	public int sub(int a, int b) {
		return calculatorService.sub(a, b);
	}
	
	public int mul(int a, int b) {
		return calculatorService.mul(a, b);
	}
	
	public int div(int a, int b) {
		return calculatorService.div(a, b);
	}
}
