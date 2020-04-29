package com.application.utils;

import com.application.classScanner.ClassScanner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.List;

public class GenericInfo {

    public String getGenericType(){
        ClassScanner classScanner = new ClassScanner();
        classScanner.scanPath();
        List<Class> classes = classScanner.getScannedClasses();
        Class testGenericClass = null;

        //выбираем тестовый класс с дженериками вручную
        for (Class cl : classes) {
            if(cl.getName().contains("Generics")){
                testGenericClass = cl;
            }
        }

        System.out.println(testGenericClass.getName());

        Field[] fields = testGenericClass.getDeclaredFields();
        System.out.println("Fields number: " + fields.length + "\n");
        for(Field field : fields){
            System.out.println("Field's name: "+ field.getName());
            System.out.println("Field's generic type: "+ field.getGenericType()+"\n");
        }

        Method[] methods = testGenericClass.getDeclaredMethods();
        for(Method method : methods){
            System.out.println("Method's name: "+ method.getName());
            Parameter[] parameters = method.getParameters();
            for(Parameter parameter : parameters){
                System.out.println("Arg's name: "+ parameter.getName());
                System.out.println("Arg's simple type: "+ parameter.getType());
                System.out.println("Arg's generic type: " + parameter.getParameterizedType().getTypeName() + "\n");
            }
        }



        return "";
    }
}
