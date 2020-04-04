package com.application;

import com.application.classScanner.ClassScanner;
import com.application.testDataGenerator.TestDataGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ClassScanner sc = new ClassScanner();
        sc.scanPath();
        List<Class> listClass = sc.getScannedClasses();
        for (Class cl : listClass) {
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

        TestDataGenerator testDataGenerator = new TestDataGenerator();

        System.out.println(testDataGenerator.getTestObject(String.class));
        // добавить в сгенерированную заглушку логику теста

        //1) создаем заглушку +
        //2) взять параметры метода, который мы тестируем (updateCommune)
        //3) на основе параметров метода создать логику создания РАНДОМНЫХ обьектов данного классаю (final Commune group = new Commune())
        //4) после того, как создана логика создания обьектов -> вызвать метод с созданными аргументами. (final Long result = communeControllerUnderTest.updateCommune(group);)


//        @Test
//        void testUpdateCommune() {
//            // Setup
//            final Commune group = new Commune();
//
//            // Run the test
//            communeControllerUnderTest.updateCommune(group);
//
//
//         }
    }
}
