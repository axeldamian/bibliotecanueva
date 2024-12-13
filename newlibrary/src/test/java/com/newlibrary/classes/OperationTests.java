package com.newlibrary.classes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.newlibrary.clases.Operations;

@RunWith(JUnit4.class)
public class OperationTests {

  @Test
  public void testCheckResult() {
    Operations op = new Operations();
    int resultado = op.sum(2, 3);
    assertEquals(5, resultado);
  }

}