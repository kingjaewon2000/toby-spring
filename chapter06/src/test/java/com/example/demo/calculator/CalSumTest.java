package com.example.demo.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalSumTest {

    Calculator calculator;
    String numFilePath;

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = getClass()
                .getResource("numbers.txt")
                .getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        assertEquals(10, calculator.calcSum(numFilePath));
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        assertEquals(24, calculator.calcMultiply(numFilePath));
    }

    @Test
    public void concatenateStrings() throws IOException {
        assertEquals("1234", calculator.concatenate(numFilePath));
    }

}
