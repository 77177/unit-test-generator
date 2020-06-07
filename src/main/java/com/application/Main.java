package com.application;

import com.application.testDataGenerator.TestDataGenerator;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        TestDataGenerator testDataGenerator = new TestDataGenerator();
        String testObject = testDataGenerator.getTestObject(String.class);
        System.out.println(testObject);
    }
}
