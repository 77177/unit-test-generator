package com.application;

import com.application.classScanner.ClassScanner;
import com.application.testDataGenerator.TestDataGenerator;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Main {
    final static Random rnd = new Random();

    private static String firstCharSmall(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    private static String getValueByType(Class<?> typ, String parName) {
        String ls = System.lineSeparator();
        String res = "\t\tfinal " + typ.getCanonicalName() + " " +
                parName + " = ";
        String typName = typ.getSimpleName();
        if (typName.equals("char")) {
            char rndChar = (char)(rnd.nextInt(127 - 32) + 32);
            res += "'" + rndChar + "'";
        } else if (typName.equals("int")) {
            res += String.valueOf(rnd.nextInt());
        } else if (typName.equals("float")) {
            res += String.valueOf((float)rnd.nextDouble());
        } else if (typName.equals("double")) {
            res += String.valueOf(rnd.nextDouble());
        } else if (typName.equals("boolean")) {
            res += rnd.nextInt(2) == 1 ? "true" : "false";
        } else if (typName.equals("String")) {
            int len = rnd.nextInt(10) + 1;
            String str = "";
            for (int i = 0; i < len; i++) {
                char rndChar = (char)(rnd.nextInt(123-97) + 97);
                str += rndChar;
            }
            res += "\"" + str + "\"";
        } else if (typ.isArray()) {
            res += "new " + typ.getComponentType().getCanonicalName() + "[" + rnd.nextInt(50) + "];" + ls;
            res += "\t\tfor (int i = 0; i < " + parName + ".length; i++) {" + ls;
            res += "\t\t\t" + parName + "[i] = new " + typ.getComponentType().getCanonicalName() + "();" + ls;
            res += "\t\t}" + ls;
            return res;
        } else {
            List<String> arguments = new LinkedList<String>();
            if (typ.getConstructors().length != 0) {
                Constructor<?> ctor = typ.getConstructors()[0];
                int parCnt = ctor.getParameterCount();
                for (int i = 0; i < parCnt; i++) {
                    Class<?> parType = ctor.getParameterTypes()[i];
                    String ctorParName = parName + "_" + i;
                    res = getValueByType(parType, ctorParName) + res;
                    arguments.add(ctorParName);
                }
            }
            res += "new " + typ.getCanonicalName() + "(" + String.join(", ", arguments) + ")";
        }
        return res + ";" + ls;
    }

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ClassScanner sc = new ClassScanner();
        sc.scanPath();
        List<Class> listClass = sc.getScannedClasses();
        for (Class cl : listClass) {
            //System.out.println(cl.getName());
        }

        List<String> classes = new LinkedList<String>();
        String ls = System.lineSeparator();
        for (Class cl : listClass) {
            String classDesc = "public class " + cl.getSimpleName() + "Test {" + ls;
            List<String> methods = new LinkedList<String>();
            for (Method m : cl.getDeclaredMethods()) {
                String methodDesc = "\t@Test" + ls;
                methodDesc += "\tpublic void " + m.getName() + "Test {" + ls;
                methodDesc += "\t\t// Setup" + ls;
                int parCnt = m.getParameterCount();
                List<String> arguments = new LinkedList<String>();
                for (int i = 0; i < parCnt; i++) {
                    Class<?> parType = m.getParameterTypes()[i];
                    String parName = "arg" + i;
                    methodDesc += getValueByType(parType, parName);
                    arguments.add(parName);
                }
                methodDesc += "\t\t" + ls;
                methodDesc += "\t\t// Run the test" + ls;
                String resPart = "";
                if (!m.getReturnType().getName().equals("void")) {
                    resPart = m.getReturnType().getName() + " res = ";
                }
                methodDesc += "\t\t" + resPart +
                        firstCharSmall(cl.getSimpleName()) + "." +
                        m.getName() + "(" + String.join(", ", arguments) + ");" + ls;
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
