package com.btit95.sample.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
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

	@Test()
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
		calculatorService.div(7, 0); // should be expect ArithmeticException
		calculatorService.mul(7, 0);

		assertEquals(10, calculateApplication.add(5, 5));

		// Test add method must be called
		verify(calculatorService).add(3, 5);

		// check a minimum 1 call count
		verify(calculatorService, atLeastOnce()).add(3, 5);

		// check if add function is called minimum 2 times
		verify(calculatorService, atLeast(2)).add(3, 5);

		// check if add function is called maximum 3 times
		verify(calculatorService, atMost(3)).add(3, 5);

		// invocation count can be added to ensure multiplication invocations
		// can be checked within given timeframe
		verify(calculatorService, timeout(100).times(1)).add(20, 10);
	}
}
