package src.stream;

import java.util.Optional;

/**
 * this is a simply demo for optional
 */
public class Part5_Optional_Stream {
    public static void main(String[] args) {
        createOptional();
        assertOptional();
        mapAndFlatMapOptional();
    }

    public static void createOptional() {
        /*create an optional object*/
        Optional<String> first = Optional.of("first");
        Optional<Object> empty = Optional.empty();
        Optional<Object> nullOptional = Optional.ofNullable(null);//equals Optional.empty()
        Optional<Object> StringOptional = Optional.ofNullable("first");
    }

    public static void assertOptional() {
        Optional<String> source = Optional.empty();
        if (source.isPresent()) {
            System.out.println("Source is empty");
        }
        //ifPresent will not return any value
        source = Optional.of("YuZaSou");
        source.ifPresent(value -> System.out.println("IfPresent value is -->" + value));
        // get() will return the element
        System.out.println("Get value is ----> " + source.get());
        //orElse
        Optional<String> emptyOptional = Optional.ofNullable(null);
        System.out.println("OrElse :--->" + emptyOptional.orElse("auto create orElse"));//if emptyOptional is empty return the value from orElse
        //orElseGet
        System.out.println("OrElseGet :--->" + emptyOptional.orElseGet(() -> {
            return "create by orElseGet";
        }));
        //orElseThrow
        try {
            System.out.println("OrElseThrow :--->" + emptyOptional.orElseThrow(IllegalArgumentException::new));
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException");
        }
    }

    public static void mapAndFlatMapOptional() {
        Optional<String> optional = Optional.of("strawberry");
        System.out.println("Map:--->" + optional.map(String::toUpperCase));
        System.out.println("null Map:--->" + optional.map(x -> x = null));//return empty optional when map(return value is null)
        /*FlatMap must return an optional,FlatMap does't encapsulate the results in Optional,you should do it by yourself*/
        System.out.println("FlatMap :--->" + optional.flatMap(s -> Optional.of(s.toUpperCase())).orElse("null value"));
        //from book 写给大忙人看的Java SE 8
        System.out.println("FlatMap ---->" + Optional.of(16D).flatMap(Part5_Optional_Stream::getInverse).flatMap(Part5_Optional_Stream::getSqureRoot));
    }

    public static Optional<Double> getInverse(Double start) {
        return start == 0 ? Optional.empty() : Optional.of(1 / start);
    }

    public static Optional<Double> getSqureRoot(Double start) {
        return start < 0 ? Optional.empty() : Optional.of(Math.sqrt(start));
    }
}
