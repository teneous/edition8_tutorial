package src.stream;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * 如何截取stream元素
 */
public class Part3_Branch_Stream {
    public static void main(String[] args) {
        limit();
        skip();
        concat();
        peek();
    }

    public static void limit() {
        Stream<Double> limitStream = Stream.generate(Math::random).limit(5);//创建5个元素
        limitStream.forEach(s -> System.out.print(s + "\t"));
        System.out.println();
        //当limit大于size大小的时候，返回依然是size个数
        Stream<String> limitStream2 = Stream.of("first", "second", "third", "forth").limit(10);
        limitStream2.forEach(s -> System.out.print(s + "\t"));
    }

    /**
     * 跳过开头几个元素
     */
    public static void skip() {
        Stream<String> skipStream = Stream.of("first", "second", "third", "forth").skip(2);
        skipStream.forEach(s -> System.out.print(s + "\t"));
        System.out.println();
        //当skip个数大于size的时候，将什么都不返回
        Stream<String> skipStream2 = Stream.of("first", "second", "third", "forth").skip(4);
        skipStream2.forEach(s -> System.out.print(s + "\t"));
    }

    public static void concat() {
        Stream<String> partOne = Stream.of("first", "second");
        Stream<String> partTwo = Stream.of("third", "forth");
        Stream<String> partThird = Stream.of("fifth", "last");
        //只支持两两连接,因此如果想连接partThird，你应该先连接partOne和partTwo
        Stream<String> concatStream = Stream.concat(partOne, partTwo);
        concatStream.forEach(s -> System.out.print(s + "\t"));//first second third forth
        System.out.println();

        Stream<Integer> integerStream = Stream.of(1, 2);//创建一个integer的stream
        Stream<? extends Serializable> concat = Stream.concat(integerStream, partThird);//连接字符stream和int stream
        concat.forEach(s -> System.out.println("type:" + s.getClass().getName() + "  value:" + s));
    }


    /**
     * peek不是终结操作，可以用来debug打印stream里元素状态
     */
    public static void peek() {
        Stream<Integer> sourceStream = Stream.iterate(1, i -> i + 2).limit(10);
        System.out.println("The length is :" + sourceStream.peek(s -> System.out.print(s + "\t")).toArray().length);
    }
}
