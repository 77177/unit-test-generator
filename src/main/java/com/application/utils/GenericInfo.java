package com.application.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class GenericInfo {


    public Map<String, String> getAllMethodGenericTypes(Class goalClass, String methodName) {

        System.out.println(goalClass.getName());

        Method[] methods = goalClass.getDeclaredMethods();
        Map<String, String> methodNames = new HashMap();

        System.out.println("Method's number: " + methods.length + "\n");

        for (Method method : methods) {
            if(method.getName().contains(methodName)) {
                System.out.println("Method's name: " + method.getName());
                Parameter[] parameters = method.getParameters();

                for (Parameter parameter : parameters) {
                    System.out.println("Arg's name: " + parameter.getName());
                    System.out.println("Arg's simple type: " + parameter.getType());
                    System.out.println("Arg's generic type: " + parameter.getParameterizedType().getTypeName() + "\n");
                    methodNames.put(parameter.getName(), parameter.getParameterizedType().getTypeName());
                }
            }
        }
        return methodNames;
    }


    public String getFieldGenericType(Class goalClass, String fieldName){

        System.out.println(goalClass.getName());

        Field[] fields = goalClass.getDeclaredFields();
        System.out.println("Fields number: " + fields.length + "\n");
        for(Field field : fields){
            if(field.getName().contains(fieldName)) {
                System.out.println("Field's name: " + field.getName());
                System.out.println("Field's generic type: " + field.getGenericType() + "\n");
                return field.getGenericType().toString();
            }
        }
        return "";

    }
}
