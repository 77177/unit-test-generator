package com.application.testgenerator;

import com.application.testgenerator.classScanner.ClassScanner;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
     ClassScanner sc = new ClassScanner();
     sc.scanPath();
     List<Class> listClass = sc.getScannedClasses();
     for (Class cl : listClass){
         System.out.println(cl.getName());
     }

        List<String> classes = new LinkedList<String>();
        String ls = System.lineSeparator();
        for (Class cl : listClass) {
            String classDesc = "public class " + cl.getSimpleName() + "Test {" + ls;
            List<String> methods = new LinkedList<String>();
            for (Method m : cl.getDeclaredMethods()) {
                String methodDesc = "\t@Test" + ls;
                methodDesc += "\tpublic void " + m.getName() + "Test {" + ls;
                methodDesc += "\t\t" + ls;
                methodDesc += "\t}" + ls;
                methods.add(methodDesc);
            }
            classDesc += String.join(ls, methods);
            classDesc += "}" + ls;
            classes.add(classDesc);
        }
        System.out.println(String.join(ls, classes));
    }
}
