package hw2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Используя Reflection API, напишите программу,
 * которая выводит на экран все методы класса String.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        int count = 1;

        Class<?> obj = Class.forName(String.class.getName());
        Method[] methods = obj.getDeclaredMethods();

        for (Method method : methods) {
            System.out.printf("%d:\t%s\n", count++, method.getName());
        }
    }
}
