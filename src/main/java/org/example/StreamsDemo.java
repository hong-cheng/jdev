/* Joe James - OGGI AI
Stream - has a data source - Collections, Lists , Sets, ints, longs, lines of a file, etc
    intermediate operations - such as filters, maps, sort return a stream so u can chain them
    can be filtered - can be sorted, mapped - can be aggregated;
    finally a terminal operations -
      ie: forEach, collect or reduce are either void or return a non-stream result
      can reduce to a value or a list: count(), max(), etc
    makes heavy use of lambdas - ie: disposable functions

    tips: filter first , then map, then sort, then reduce
    use ParallelStream to speed up processing for large datasets
 */
package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamsDemo {
    public static void main(String[] args) throws IOException {
        // integer stream
        IntStream
                .range(1, 10)      // 0..9
                .skip(3)         // skip first 3
                .forEach(x -> System.out.println(x));  // use lambda
        System.out.println();

        // print out a sum of all the numbers from 1 to 10
        System.out.println(
                IntStream
                        .range(1, 10)
                        .sum());
        System.out.println();

        // find first alpabetically sorted item
        Stream.of("Ava", "Ameri", "Albert")   // create a stream out of something
               .sorted()
                .findFirst()
                .ifPresent(System.out::println);

        // filter sample
        String[] names = {"joe", "jane", "Ankit", "jill", "Moe", "rana"};
        Arrays.stream(names)  // or: Stream.of(names)
                .filter(x -> x.startsWith("S"))
                .sorted()
                .forEach(x -> System.out.println(x));

        // find the avg of the squares of an int array
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Arrays.stream(ints)
               .map(x -> x * x)
               .average()
                .ifPresent(System.out::println);

        List<String> people = Arrays.asList(names);
        people
                .stream()
                .map(String::toLowerCase)  // maps to a Class::FunctionName
                .filter(x -> x.startsWith("a"))
                .forEach(System.out::println);

        // process a file
        Stream<String> lines = Files.lines(Paths.get("src/main/java/org/example/Main.java"));
        List<String> pub_lines = lines
                .sorted()
                .filter(x -> x.contains("public"))
                .collect(Collectors.toList());
        lines.close();
        pub_lines.forEach(x -> System.out.println(x));

        // read from csv file
        Stream<String> rows = Files.lines(Paths.get("src/main/resources/data.csv"));
        int rowCount = (int) rows
                .map(x -> x.split(","))
                .filter(x -> x.length == 4)
               .count();
        rows.close();
        System.out.println(rowCount);

        // convert a list to hash
        Stream<String> rows2 = Files.lines(Paths.get("src/main/resources/data.csv"));
        Map<String, Integer> map =
          rows2
                .map(x -> x.split(","))
                .filter(x -> x.length == 4)
                .collect(Collectors.toMap(
                        x -> x[1],
                        x -> Integer.parseInt(x[2].trim())
                ));
        rows2.close();
        for(String key: map.keySet()) {
            System.out.println(key + ": " + map.get(key));
        }

        // calc total using reduce - sum() is only good for ints so not very useful
        double total = Stream.of(3.1, 8.5, 9.9)
                .reduce(0.0, (Double runningTotal, Double x) -> runningTotal + x)
                               .intValue();
        System.out.println(total);
    }
}