package com.newlibrary.classes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.newlibrary.clases.Operations;

/**
 * Test class that uses the JUnit 4 framework.
 */
@RunWith(JUnit4.class)
public class OperationTests {

    /**
   * Test to verify that the sum method of the Operations class returns the correct result.
   */
  @Test
  public void testCheckResult() {

    // Creating an instance of the Operations class
    Operations op = new Operations();

    // Input parameters for the sum method
    int num1 = 2;
    int num2 = 3;
    
    // Calling the sum method with parameters num1 and num2
    int result = op.sum(num1, num2);

    // Expected result of adding num1 and num2
    int expectedResult = 5;
            
     // Verification of the expected result
    assertEquals(expectedResult, result);
  }

}