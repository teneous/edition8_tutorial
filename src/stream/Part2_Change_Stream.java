package src.stream;

import src.utils.CommandLineUtil;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

/**
 * stream类的常用操作1
 * 这个例子适用于绝大多数情
 */
public class Part2_Change_Stream {
    public static void main(String[] args) {
        forEach();
        fliter();
        distinct();
        map();
        flatMap();
        sort();
    }

    /*forEach是一个终结操作*/
    public static void forEach() {
        List<Integer> source = Arrays.asList(3, 1, 5);
        //java 5~7我们需要这样来写
        for (Integer temp : source) {
            System.out.print(temp);
        }
        //java 8中我们可以这样来写
        CommandLineUtil.printLine();
        source.forEach(System.out::print);//内部传入一个消费者函数 recommend
        CommandLineUtil.printLine();
        source.stream().forEach(System.out::print);//也可使用stream类的遍历，这种就有点多此一举类
        CommandLineUtil.printLine();
        source.stream().forEachOrdered(System.out::print);//严格按照集合顺序遍历，效率略低于foreach
    }

    /**
     * 保留filter函数中为true的元素
     */
    public static void fliter() {
        String[] songs = {"What do you mean", "Heal the world", "Flamingo", "Sorry", "Cry on my shoulder"};
        Stream<String> songStream = Arrays.stream(songs);
        Stream<String> filterStream = songStream.filter(e -> e.length() >= 10);//字数大于等于10的被保留
        filterStream.forEach(System.out::print);
        CommandLineUtil.printLine();
        /*再次声明，foreach是终结操作，一旦进行过终结操作，那么此stream不可在改变
            exception:stream has already been operated upon or closed
            Stream<String> stream2 = filterStream.filter(character -> !character.contains("g"));
            stream2.forEachOrdered(System.out::println);
        */
        //另建一个stream对象
        Stream<String> MyflavorStream = Arrays.stream(songs);
        //查询以H开头的歌曲
        MyflavorStream
                .filter(e -> e.startsWith("H"))
                .forEach(s -> System.out.print(s + "\t"));//Heal the world
    }

    /**
     * 去重操作
     */
    public static void distinct() {
        Stream<Integer> integerStream = Stream.of(1, 3, 5, 1, 6, 7);
        Stream<Integer> distinctStream = integerStream.distinct();
        distinctStream.forEach(s -> System.out.print(s + "\t"));
    }


    /**
     * map会改变stream中每一个元素对象
     * 适用于将一个元素流变为另一个元素流
     */
    public static void map() {
        Stream<Integer> source = Stream.of(1, 3, 5, 7, 9);
        Stream<Integer> stream = source.map(a -> (a * 2) - 1);//map将改变流中每一个元素的值
        stream.forEach(s -> System.out.print(s + "\t"));
        CommandLineUtil.printLine();

        //生成一个f(x) = 2x - 1 && n = 5的int流，然后转换为double流
        DoubleStream iteratorStreamDesc = Stream.iterate(1, i -> (i * 2 - 1)).limit(5).mapToDouble(i -> i - 1);
        //第一次的输出作为第二次的输入
        iteratorStreamDesc.forEach(s -> System.out.print(s + "\t"));//output:0.0 0.0 0.0 0.0 0.0
        /**
         * 你可能疑惑为啥都是0.0呢？
         * 首先起始值是1，第二次是1 * 2 - 1 = 1 ，第三次时是用作第二次的结果作为第二次的i 则还是1 * 2 - 1 = 1
         */
        CommandLineUtil.printLine();

        //再来看加号的情况就很容易了
        DoubleStream iteratorStreamASC = Stream.iterate(1, i -> (i * 2 + 1)).limit(5).mapToDouble(i -> i - 1);
        iteratorStreamASC.forEach(s -> System.out.print(s + "\t"));
        CommandLineUtil.printLine();

        //典型案例，将tream<String>变为Stream<Integer> ，流对象的改变
        Stream<Integer> integerStream = Stream.of("1", "2", "3", "4").map(Integer::parseInt);
        integerStream.forEachOrdered(s -> System.out.print(s + "\t"));
    }

    /**
     * 元素流的展开{{1,2}，{3,4}，{5,6}}  - > flatMap  - > {1,2,3,4,5,6}
     */
    public static void flatMap() {
        //准备一个三层嵌套的list,我们想在将它拆分成Stream<String>
        List<String> smallList = Collections.singletonList("inner List");
        List<List<String>> middleList = Collections.singletonList(smallList);
        List<List<List<String>>> largeList = Collections.singletonList(middleList);

        Stream<List<String>> listStream = largeList.stream().flatMap(Collection::stream);//第一次平铺
        Stream<String> stream = listStream.flatMap(Collection::stream);//第二次平铺
        stream.forEach(System.out::print);
        CommandLineUtil.printLine();

        //我们换位对象试试，{PersonA}，{PersonB}，{PersonC}}  - > flatMap  - > {A,B,C}
        List<Person> fstList = new ArrayList<>();
        fstList.add(new Person(1, "jack"));
        List<Person> secList = new ArrayList<>();
        secList.add(new Person(2, "nancy"));
        Stream<List<Person>> stringStream = Stream.of(fstList, secList);
        List<Person> collect = stringStream.flatMap(Collection::stream).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     * 元素排序
     */
    public static void sort() {
        Stream<Integer> integerStream = Stream.of(5, 6, 2, 9, 1, 3);
        Stream<Integer> sorted = integerStream.sorted(Comparator.naturalOrder());
        sorted.forEach(x -> System.out.print(x + "\t"));//output：// 1 2 3 5 6 9
        CommandLineUtil.printLine();

        /*自定义比较器*/
        Person p1 = new Person(1, "Jack", "English");
        Person p2 = new Person(2, "Ayako", "Japanese");
        Person p3 = new Person(3, "Lee", "Mandarin");
        Stream<Person> personStream = Stream.of(p1, p2, p3);
        //String默认升序，使用reverse变为进行逆转
        Stream<Person> sortPersonStream = personStream.sorted(Comparator.comparing(Person::getLanguege).reversed());
        sortPersonStream.forEach(System.out::println);
    }
}

class Person implements Serializable, Comparator<String> {
    private int id;
    private String name;
    private String languege;
    private Set<String> hobby;


    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Person(int id, String name, String languege) {
        this.id = id;
        this.name = name;
        this.languege = languege;
    }

    public Person(int id, String name, Set<String> hobby) {
        this.id = id;
        this.name = name;
        this.hobby = hobby;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getHobby() {
        return hobby;
    }

    public void setHobby(Set<String> hobby) {
        this.hobby = hobby;
    }

    public String getLanguege() {
        return languege;
    }

    public void setLanguege(String languege) {
        this.languege = languege;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", languege='" + languege + '\'' +
                ", hobby=" + hobby +
                '}';
    }

    @Override
    public int compare(String str1, String str2) {
        return str1.length() - str2.length();
    }
}