package com.newlibrary;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

/**
 * Unit testing class that uses the JUnit 5 framework.
 */
public class AppTest {

    /**
     * Unit test to verify that the App class works correctly.
     */
    @Test
    public void testApp() {
        // Verification of a true condition
        assertTrue(true);
    }

    /**
     * Unit test to verify that the main method of the App class prints the string "Hello World!".
     */
    @Test
    public void testMain() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        App.main(null);
        assertEquals("Hello World!\n", outContent.toString());
    }
}