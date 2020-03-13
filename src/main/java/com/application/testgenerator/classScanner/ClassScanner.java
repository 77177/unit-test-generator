package com.application.testgenerator.classScanner;

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
    private static String path;

    public ClassScanner(){
        this.path = "src/main/java/com/application/testgenerator/examples";
        scannedClasses = new LinkedList<>();
    }

    public static void scanPath() {
        //Logger.getLogger("ClassScanner").log(Level.INFO,"Scanning classes in " + path);
        try (Stream<Path> walk = Files.walk(Paths.get(path))) {
            walk.sorted(Comparator.reverseOrder())
                    .filter(file -> file.toString().endsWith(".java"))
                    .map(file -> file.toString().substring(14))
                    .map(file -> file.substring(0,file.lastIndexOf('.')).replace('\\','.'))
                    .forEachOrdered(className -> {
                        try {
                            scannedClasses.add(Class.forName(className));
                            //Logger.getLogger("ClassScanner").log(Level.INFO,"Added class: " + className);
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
