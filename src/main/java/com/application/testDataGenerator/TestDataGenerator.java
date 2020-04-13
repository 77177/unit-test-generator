package com.application.testDataGenerator;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDataGenerator {

    public Field setField(Object o, Field f) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {

        f.setAccessible(true);

        Random randomGenerator = new Random();
        if (f.getType().equals(String.class)) {
            String string = Stream.iterate(0, i -> ++i)
                    .limit(20)
                    .map(integer -> randomGenerator.nextInt(64 - 34 + 1))
                    .mapToInt(integer -> integer)
                    .mapToObj(i -> (char) (i))
                    .map(String::valueOf)
                    .collect(Collectors.joining());
            f.set(o, string);
        } else if ((f.getType().equals(Integer.class)) || (f.getType().getSimpleName().equals("int"))) {
            f.set(o, (int) (Math.random() * 1000));
        } else if ((f.getType().equals(Long.class)) || (f.getType().getSimpleName().equals("long"))) {
            f.set(o, (long) (Math.random() * 1000));
        } else if ((f.getType().equals(Double.class)) || (f.getType().equals(Float.class)) || (f.getType().getSimpleName().equals("double")) || (f.getType().getSimpleName().equals("float"))) {
            f.set(o, Math.random() * 1000);
        } else if ((f.getType().equals(Long.class)) || (f.getType().getSimpleName().equals("long"))) {
            f.set(o, ((randomGenerator.nextInt(100)) > 50 ? Boolean.TRUE : Boolean.FALSE));
        } else if (f.getType().equals(LocalDate.class)) {
            f.set(o, LocalDate.now());
        } else if (f.getType().equals(LocalTime.class)) {
            f.set(o, LocalTime.now());
        } else if (f.getType().equals(LocalDateTime.class)) {
            f.set(o, LocalDateTime.now());
        } else {
            f.set(o, getTestObject(f.getType()));
        }
        return f;
    }

    public <T> T getTestObject(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {


        if (isPrimitive(clazz)) {
            Random randomGenerator = new Random();
            if (clazz.equals(String.class)) {
                return (T) Stream.iterate(0, i -> ++i)
                        .limit(20)
                        .map(integer -> randomGenerator.nextInt(64 - 34 + 1))
                        .mapToInt(integer -> integer)
                        .mapToObj(i -> (char) (i))
                        .map(String::valueOf)
                        .collect(Collectors.joining());
            } else if ((clazz.equals(Integer.class)) || (clazz.getSimpleName().equals("int"))) {
                return (T) Integer.valueOf((int) (Math.random() * 1000));
            } else if ((clazz.equals(Long.class)) || (clazz.getSimpleName().equals("long"))) {
                return (T) Long.valueOf((long) (Math.random() * 1000));
            } else if ((clazz.equals(Double.class)) || (clazz.equals(Float.class)) || (clazz.getSimpleName().equals("double")) || (clazz.getSimpleName().equals("float"))) {
                return (T) Double.valueOf((Math.random() * 1000));
            } else if (clazz.equals(Boolean.class) || clazz.getSimpleName().equals("boolean")) {
                return (T) ((randomGenerator.nextInt(100)) > 50 ? Boolean.TRUE : Boolean.FALSE);
            } else if (clazz.equals(LocalDate.class)) {
                return (T) LocalDate.now();
            } else if (clazz.equals(LocalTime.class)) {
                return (T) LocalTime.now();
            } else if (clazz.equals(LocalDateTime.class)) {
                return (T) LocalDateTime.now();
            }
        }

        Constructor<T> constructor = clazz.getConstructor();
        T newInstance = constructor.newInstance();

        List<Field> fields = Arrays.asList(newInstance.getClass().getDeclaredFields());

        fields.forEach(field -> {
            try {
                setField(newInstance, field);
            } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
        return newInstance;
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

    public <T> Object[] getRandomArray(Class<T> clazz, int size) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        ArrayList<T> randomList = getRandomList(clazz, size);

        return randomList.toArray();
    }
}
