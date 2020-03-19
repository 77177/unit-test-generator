package com.application;

import com.application.testgenerator.TestGenerator;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {
        TestGenerator.start(System.getProperty("class.directory"));
    }
}
