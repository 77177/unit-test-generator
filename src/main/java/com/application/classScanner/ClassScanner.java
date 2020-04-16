package com.application.classScanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ClassScanner {
    private static List<Class> scannedClasses;
    private static String classpath;

    public ClassScanner(){
        classpath = "target" + File.separator + "classes";
        scannedClasses = new LinkedList<>();
    }



    public static void scanPath() {
        try (Stream<Path> walk = Files.walk(Paths.get(classpath))) {
            walk.sorted(Comparator.reverseOrder())
                    .filter(file -> file.toString().endsWith(".class"))
                    .map(filepath -> filepath.toString().replace(classpath,""))
                    .map(s -> s.replace(".class",""))
                    .forEachOrdered(className -> {
                        try {
                            String classNameString = className.replace(File.separator, ".").substring(1, className.length());
                            scannedClasses.add(Class.forName(classNameString));
                        } catch (ClassNotFoundException e) {
                            Logger.getLogger("ClassScanner").log(Level.WARNING,"Fail to adding classes: " + e.toString());
                        }
                    });
        } catch (IOException e) {
            Logger.getLogger("ClassScanner").log(Level.WARNING,"Fail to scanning path: " + e.toString());
        }
    }


    public static List<Class> getScannedClasses(){
        return scannedClasses;
    }
}
