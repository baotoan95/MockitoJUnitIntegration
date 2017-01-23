package com.btit95.sample.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.btit95.sample.app.CalculateApplication;
import com.btit95.sample.services.CalculatorService;

public class CalculateApplicationTester {
	@Mock
	private CalculatorService calculatorService;
	@InjectMocks
	private CalculateApplication calculateApplication = new CalculateApplication();
	
	@Before
	public void setUp() {
		this.calculatorService = mock(CalculatorService.class);
	}
	
	@Test(expected = ArithmeticException.class)
	public void testAdd() {
		when(calculatorService.add(3, 5)).thenReturn(8);
		when(calculatorService.sub(anyInt(), anyInt())).then(new Answer<Integer>() {
			public Integer answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = (Object[]) invocation.getArguments();
				return Integer.parseInt(args[0].toString()) - Integer.parseInt(args[1].toString());
			}
		});
		when(calculatorService.div(7, 0)).thenThrow(new ArithmeticException());
		doThrow(new ArithmeticException()).when(calculatorService).mul(7, 0);
		
		assertEquals(8, calculatorService.add(3, 5));
		assertEquals(7, calculatorService.sub(10, 3));
		calculatorService.div(7, 0);
		calculatorService.mul(7, 0);
		
		assertEquals(10, calculateApplication.add(5, 5));
	}
}
