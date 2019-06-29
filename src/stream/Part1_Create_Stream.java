package src.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by syoka on 2017/8/24.
 * this class basical teach how to create a java8.stream
 * 如何创建一个基本的stream对象
 */
public class Part1_Create_Stream {
    public static void main(String[] args) {
        getStream();//如何创建一个stream
        returnStream();//stream的返回值
        lazyLoadDemo();//懒加载
    }

    /**
     * 如何生成一个stream类
     */
    public static void getStream() {
        /* 创建一个空的stream对象 */
        Stream<Object> emptyStream = Stream.empty();
        /*最常用的stream方法*/
        Stream<String> ofStream = Stream.of("one", "two", "three", "four");
        /*数据生成stream*/
        String[] source = {"one", "two", "three", "four"};
        Stream<String> arrayStream = Arrays.stream(source);
        /*stream可以进行部分裁剪 */
        Stream<String> subStream = Arrays.stream(source, 0, 3);//类比substring
        /*传入一个提供者*/
        Stream<Double> randomStream = Stream.generate(Math::random);
        /*按照某个f(x)规律无限生成，最好配上limit限制生成个数*/
        Stream<Integer> five = Stream.iterate(1, x -> x + 1).limit(5);//iterator = f(f(seed))
        /*容器类Collection变为stream,Map则不行*/
        Stream<String> listStream = new ArrayList<String>().stream();
        Stream<String> setStream = new HashSet<String>().stream();
    }

    /**
     * stream对象是不会操作改变底层元对象数据的,相反，他会返回一个新的stream对象
     */
    public static void returnStream() {
        //数组转换的stream
        String[] source = {"one", "two", "three", "four", "five"};
        Stream<String> stream = Arrays.stream(source);
        //将stream所有字符串变为大写 (map用于变更stream中每个元素的值，collect是聚合方法，我们后面再讲)
        List<String> target = stream.map(String::toUpperCase).collect(Collectors.toList());
        for (String tar : target) {
            System.out.print(tar + "\t");//ONE TWO THREE FOUR FIVE
        }
        //打印原数组并没有发生变化
        for (String sou : source) {
            System.out.print(sou + "\t");//one two three four five
        }
    }

    /**
     * 测试懒加载
     */
    public static void lazyLoadDemo() {
        List<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        Stream<String> stream = list.stream();//此时stream对象应该只有2个元素
        list.add("third");//此时list在追加一个,通过打印stream的值来观察是否存在懒加载
        //第一次调用
        stream.forEach(s -> System.out.print(s + "\t"));

        /*:请记住，我们不能改变原List，stream是创建一个新的对象B,你的所有操作均是在B上，而不是A*/
        Stream<String> wrongStream = list.stream();
        wrongStream.forEach(s -> list.add("forth"));//不能往原集合添加元素
    }
}