package com.example.demo.calculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

    public Integer calcSum(String filePath) throws IOException {
        LineCallback<Integer> sumCallback = (line, value) -> value + Integer.valueOf(line);
        return lineReadTemplate(filePath, sumCallback, 0);
    }

    public Integer calcMultiply(String filePath) throws IOException {
        LineCallback<Integer> sumCallback = (line, value) -> value * Integer.valueOf(line);
        return lineReadTemplate(filePath, sumCallback, 1);
    }

    public <T> T lineReadTemplate(String filePath, LineCallback<T> lineCallback, T initVal) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filePath));
            T res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = lineCallback.doSomethingWithLine(line, res);
            }

            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public String concatenate(String numFilePath) throws IOException {
        LineCallback<String> concatnateCallback = (line, value) -> value + line;
        return lineReadTemplate(numFilePath, concatnateCallback, "");
    }
}
