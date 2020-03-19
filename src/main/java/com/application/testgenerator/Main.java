package com.application.testgenerator;

import com.application.testgenerator.classScanner.ClassScanner;

import java.util.List;

public class Main {
    public static void main(String[] args) {
     ClassScanner sc = new ClassScanner();
     sc.scanPath();
     List<Class> listClass = sc.getScannedClasses();
     for (Class cl : listClass){
         System.out.println(cl.getName());
     }
    }
}
