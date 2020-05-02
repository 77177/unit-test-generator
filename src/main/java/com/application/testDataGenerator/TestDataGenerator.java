package com.application.testDataGenerator;

import java.lang.reflect.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDataGenerator {

    public Field setField(Object o, Field f) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException, ClassNotFoundException {

        f.setAccessible(true);

        if (isGenericField(f)) {
            setGenericField(o, f);
        } else if (isArrayField(f)) {
            setArrayField(o, f);
        } else if (isPrimitiveOrWrapperField(f)) {
            setPrimitiveOrWrapperField(o, f);
        } else {
            setField(o, f);
        }

        return f;
    }

    private void setPrimitiveOrWrapperField(Object o, Field f) throws IllegalAccessException {

        Random randomGenerator = new Random();

        Type type = f.getGenericType();
        String typeName = type.getTypeName();
        String normalizedTypeName = typeName.toLowerCase();

        if (normalizedTypeName.contains("string")) {
            String string = getRandomString();
            f.set(o, string);
        } else if (normalizedTypeName.contains("int")) {
            f.set(o, (int) (Math.random() * 1000));
        } else if (normalizedTypeName.contains("long")) {
            f.set(o, (long) (Math.random() * 1000));
        } else if (normalizedTypeName.contains("float")) {
            f.set(o, (float) Math.random() * 1000);
        } else if (normalizedTypeName.contains("double")) {
            f.set(o, Math.random() * 1000);
        } else if (normalizedTypeName.contains("boolean")) {
            f.set(o, ((randomGenerator.nextInt(100)) > 50 ? Boolean.TRUE : Boolean.FALSE));
        } else if (normalizedTypeName.contains("localdatetime")) {
            f.set(o, LocalDateTime.now());
        } else if (normalizedTypeName.contains("localtime")) {
            f.set(o, LocalTime.now());
        } else if (normalizedTypeName.contains("localdate")) {
            f.set(o, LocalDate.now());
        }

    }

    private void setArrayField(Object o, Field f) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Type type = f.getGenericType();
        String typeName = type.getTypeName();

        if (typeName.contains("int")) {
            f.set(o, Arrays.stream(getRandomArray(Integer.class, 10)).mapToInt(o1 -> o1).toArray());
        } else if (typeName.contains("long")) {
            f.set(o, Arrays.stream(getRandomArray(Long.class, 10)).mapToLong(o1 -> o1).toArray());
        } else if (typeName.contains("float")) {
            throw new UnsupportedOperationException();
        } else if (typeName.contains("double")) {
            f.set(o, Arrays.stream(getRandomArray(Double.class, 10)).mapToDouble(o1 -> o1).toArray());
        } else if (typeName.contains("char")) {
            f.set(o, getRandomString().toCharArray());
        } else if (typeName.contains("Int")) {
            f.set(o, getRandomArray(Integer.class, 10));
        } else if (typeName.contains("Long")) {
            f.set(o, getRandomArray(Long.class, 10));
        } else if (typeName.contains("Float")) {
            throw new UnsupportedOperationException();
        } else if (typeName.contains("Double")) {
            f.set(o, getRandomArray(Double.class, 10));
        } else if (typeName.contains("Char")) {
            f.set(o, getRandomString().chars().mapToObj(i -> (char) i).toArray(Character[]::new));
        } else if (typeName.contains("String")) {
            f.set(o, getRandomArray(String.class, 10));
        }
    }

    private void setGenericField(Object o, Field f) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Type type = f.getGenericType();
        String typeName = type.getTypeName();

        String[] split = typeName.split("<");
        String collectionClassName = split[0];
        String elementClassName = split[1].substring(0, split[1].length() - 1);
        Class<?> collectionClass = Class.forName(collectionClassName);
        Class<?> elementClass = Class.forName(elementClassName);

        if (collectionClass.getName().contains("Set")) {
            f.set(o, getRandomList(elementClass, 10).stream().collect(Collectors.toSet()));
        } else if (collectionClass.getName().contains("List")) {
            f.set(o, getRandomList(elementClass, 10));
        }

    }

    private boolean isPrimitiveOrWrapperField(Field f) {
        return !f.getGenericType().getTypeName().contains("[") && !f.getGenericType().getTypeName().contains("<");
    }

    private boolean isArrayField(Field f) {
        return f.getGenericType().getTypeName().contains("[");
    }

    private boolean isGenericField(Field f) {
        return f.getGenericType().getTypeName().contains("<");
    }

    public String getRandomString() {
        Random randomGenerator = new Random();
        return Stream.iterate(0, i -> ++i)
                .limit(20)
                .map(integer -> randomGenerator.nextInt(25) + 65)
                .mapToInt(integer -> integer)
                .mapToObj(i -> (char) (i))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public <T> T getTestObject(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        if (isPrimitive(clazz)) {
            return getPrimitiveObject(clazz);
        }

        Constructor<T> constructor = clazz.getConstructor();
        T newInstance = constructor.newInstance();

        List<Field> fields = Arrays.asList(newInstance.getClass().getDeclaredFields());

        fields.forEach(field -> {
            try {
                setField(newInstance, field);
            } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        return newInstance;
    }

    private <T> T getPrimitiveObject(Class<T> clazz) {

        T returnObject = (T) new Object();
        Random randomGenerator = new Random();
        if (clazz.equals(String.class)) {
            returnObject = (T) getRandomString();
        } else if ((clazz.equals(Integer.class)) || (clazz.getSimpleName().equals("int"))) {
            returnObject = (T) Integer.valueOf((int) (Math.random() * 1000));
        } else if ((clazz.equals(Long.class)) || (clazz.getSimpleName().equals("long"))) {
            returnObject = (T) Long.valueOf((long) (Math.random() * 1000));
        } else if ((clazz.equals(Double.class)) || (clazz.equals(Float.class)) || (clazz.getSimpleName().equals("double")) || (clazz.getSimpleName().equals("float"))) {
            returnObject = (T) Double.valueOf((Math.random() * 1000));
        } else if (clazz.equals(Boolean.class) || clazz.getSimpleName().equals("boolean")) {
            returnObject = (T) ((randomGenerator.nextInt(100)) > 50 ? Boolean.TRUE : Boolean.FALSE);
        } else if (clazz.equals(LocalDate.class)) {
            returnObject = (T) LocalDate.now();
        } else if (clazz.equals(LocalTime.class)) {
            returnObject = (T) LocalTime.now();
        } else if (clazz.equals(LocalDateTime.class)) {
            returnObject = (T) LocalDateTime.now();
        }
        return returnObject;
    }

    public <T> boolean isPrimitive(Class<T> clazz) {
        return clazz.equals(String.class) |
                clazz.equals(Integer.class) |
                clazz.equals(Float.class) |
                clazz.equals(Double.class) |
                clazz.equals(Long.class) |
                clazz.equals(LocalDate.class) |
                clazz.equals(LocalTime.class) |
                clazz.equals(LocalDateTime.class) |
                clazz.equals(Boolean.class) |
                clazz.getSimpleName().equals("int") |
                clazz.getSimpleName().equals("long") |
                clazz.getSimpleName().equals("boolean") |
                clazz.getSimpleName().equals("float") |
                clazz.getSimpleName().equals("double");
    }

    public <T> ArrayList<T> getRandomList(Class<T> clazz, int size) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ArrayList<T> arrayList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            arrayList.add(getTestObject(clazz));
        }

        return arrayList;
    }

    public <T> T[] getRandomArray(Class<T> clazz, int size) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        ArrayList<T> randomList = getRandomList(clazz, size);

        T[] randomArray = (T[]) Array.newInstance(clazz,size);

        for (int i = 0; i < randomList.size(); i++) {
            randomArray[i] = randomList.get(i);
        }

        return randomArray;
    }
}
