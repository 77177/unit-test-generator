package com.application.classScanner;

import lombok.Getter;

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

@Getter
public class ClassScanner {
    private List<Class> scannedClasses;
    private String classpath;

    public ClassScanner(){
        classpath = "target/classes";
        scannedClasses = new LinkedList<>();
    }

    public void scanPath() {
        try (Stream<Path> walk = Files.walk(Paths.get(classpath))) {
            walk.sorted(Comparator.reverseOrder())
                    .filter(file -> file.toString().endsWith(".class"))
                    .map(filepath -> filepath.toString().replace(classpath,""))
                    .map(s -> s.replace(".class",""))
                    .forEachOrdered(className -> {
                        try {
                            String classNameString = className.replace("/", ".").substring(1, className.length());
                            scannedClasses.add(Class.forName(classNameString));
                        } catch (ClassNotFoundException e) {
                            Logger.getLogger("ClassScanner").log(Level.WARNING,"Fail to adding classes: " + e.toString());
                        }
                    });
        } catch (IOException e) {
            Logger.getLogger("ClassScanner").log(Level.WARNING,"Fail to scanning path: " + e.toString());
        }
    }

    public Class getClassByName(String name){
        scanPath();

        for (Class cl : scannedClasses) {
            if(cl.getName().contains(name)){
                return cl;
            }
        }
        return null;
    }

}
