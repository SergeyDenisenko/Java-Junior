package hw1;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Integer[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        System.out.println(getAverageEven(numbers));
    }

    public static Double getAverageEven(Integer[] list) {
        Stream<Integer> numbers = Stream.of(list);
        return numbers.filter(el -> (el % 2 == 0)).mapToDouble(e -> e).average().getAsDouble();
    }
}