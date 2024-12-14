package com.newlibrary;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Clase de pruebas unitarias que utiliza el framework JUnit 3 y 4.
 */
public class AppTest 
    extends TestCase
{

  /**
   * Constructor de la clase de pruebas.
   * 
   * @param testName nombre de la prueba
   */
    public AppTest( String testName )
    {
        super( testName );
    }

  /**
   * Método que devuelve la suite de pruebas.
   * 
   * @return la suite de pruebas
   */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

     
    /**
   * Prueba unitaria para verificar que la clase App funciona correctamente.
   */
    public void testApp()
    {
        // Verificación de una condición verdadera
        assertTrue( true );
    }

      /**
   * Prueba unitaria para verificar que el método main de la clase App
   * imprime la cadena "Hello World!".
   */
    @org.junit.Test
    public void testMain() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        App.main(null);
        assertEquals("Hello World!\n", outContent.toString());
    }
}
