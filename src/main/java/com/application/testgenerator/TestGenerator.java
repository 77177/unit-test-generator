package com.application.testgenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestGenerator {

    public static void start(String path) throws IOException {

        List<Path> classFiles = getClassFiles(path);


    }

    private static List<Path> getClassFiles(String directory) throws IOException {
        return Files.walk(Paths.get(directory))
                .filter(path -> path.toString().endsWith("class"))
                .collect(toList());
    }
}
