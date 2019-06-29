package src.stream;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 基本数据类型流
 * Created by syoka on 2017/8/31.
 */
public class Part9_OrignType_Stream {

    public static void main(String[] args) {
        IntAndLongStreamDemo();
    }

    public static void IntAndLongStreamDemo() {
        IntStream.empty();
        IntStream.of(1, 2, 5, 3);
        IntStream rangeInt = IntStream.range(0, 5);//0 1 2 3 4
        IntStream rangeClosedInt = IntStream.rangeClosed(0, 5);//0 1 2 3 4 5
        Stream<Integer> boxedInteger = IntStream.rangeClosed(0, 5).boxed();

        /**LongStream*/
        LongStream.empty();//similar to IntStream
        LongStream.of(1, 2, 5, 3);
        LongStream.range(0, 5);//0L 1L 2L 3L 4L
        LongStream.rangeClosed(0, 5);//0L 1L 2L 3L 4L 5L
        Stream<Long> boxedLong = LongStream.rangeClosed(0, 5).boxed();

        /**DoubleStream*/
        DoubleStream.empty();//similar to java8.stream
        DoubleStream.of(1, 2, 5, 3);
    }
}
