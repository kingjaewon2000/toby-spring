package com.example.demo.calculator;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
