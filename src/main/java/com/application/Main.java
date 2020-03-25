package com.application;

import com.application.classScanner.ClassScanner;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ClassScanner sc = new ClassScanner();
        sc.scanPath();
        List<Class> listClass = sc.getScannedClasses();
        for (Class cl : listClass) {
            System.out.println(cl.getName());
        }

        // взять классы и с помошью reflection создать заглушки для теста по типу

        //public class ClassNameTest {
        //        @Test
        //        public void MethodNameTest{
        //
        //        }
        //}


    }
}
