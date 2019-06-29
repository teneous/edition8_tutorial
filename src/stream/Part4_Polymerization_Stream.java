package src.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Simply to express the count,max,min,findAny.anyMatch,allMatch,noneMatch,findFirst
 */
public class Part4_Polymerization_Stream {
    public static void main(String[] args) {
        maxMixCount();
        match();
    }

    public static void maxMixCount() {
        Stream<String> maxStream = Stream.of("one", "three", "four", "seven");
        Stream<String> minStream = Stream.of("one", "three", "four", "seven");
        Stream<String> countStream = Stream.of("one", "three", "four", "seven");
        Optional<String> max = maxStream.max(String::compareToIgnoreCase);//通过字符串abc..排序
        Optional<String> min = minStream.min(String::compareToIgnoreCase);//返回的是一个Optional后面会讲
        System.out.println(max.orElse(""));
        System.out.println(min.orElse(""));
        System.out.println(countStream.count());
    }

    public static void match() {
        /*findFirst*/
        Stream<String> findFirstStream = Stream.of("one", "three", "four", "seven");
        System.out.println("findFirst--->" + findFirstStream.findFirst().orElse("null"));
        /*findAny*/
        List<String> source = Arrays.asList("one", "three", "four", "seven");
        Optional<String> r1 = source.stream().findAny();
        System.out.println("findAny r1---->" + r1.orElse("null"));
        Optional<String> r2 = source.stream().filter(s -> s.contains("r")).findAny();
        System.out.println("findAny r2---->" + r2.orElse("null"));
        //并行流返回3或者4
        Optional<String> r3 = source.stream().parallel().filter(s -> s.contains("r")).findAny();//return three or four
        System.out.println("findAny r3---->" + r3.orElse("null"));

        /*anyMatch*/
        Stream<String> anyMatchStream = Stream.of("one", "three", "four", "seven");
        //loop twice got the three ,then print it out
        System.out.println("|| Current string is Three ---->" + anyMatchStream
                .peek(s -> System.out.print("loop at : " + s + "\t"))
                .anyMatch(s -> s.contains("r")));

        /*allMatch*/
        Stream<String> allMatchStream = Stream.of("one", "three", "four", "seven");
        System.out.println("All string.length > 3 : ---->" + allMatchStream.allMatch(s -> s.length() >= 3));

        /*allNone*/
        Stream<String> allNoneStream = Stream.of("one", "three", "four", "seven");
        System.out.println("There are no string contains g : ---->" + allNoneStream.noneMatch(s -> s.contains("g")));
    }
}
