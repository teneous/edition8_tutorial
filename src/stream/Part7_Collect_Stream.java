package src.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 集合操作，重点！！
 */
public class Part7_Collect_Stream {
    public static void main(String[] args) {
        toArray();
        colections();
        toMap();
    }

    public static void toArray() {
        Stream<String> stream = Stream.of("first", "second", "third");
        Stream<String> copyStream = Stream.of("first", "second", "third");
        Object[] objects = stream.toArray();//return object array
        String[] stringArray = copyStream.toArray(String[]::new);//accept E::new to return E array
    }

    /**
     * stream变换为集合
     * colections accept three parameter.
     * First --->target constructor 容器构造器
     * Second --->add element into target (method) 向容器添加元素
     * Third --->put two object together such as addAll(method) 聚合器
     * In fact ,we usually use Collectors
     */
    public static void colections() {
        //ArrayList例子
        ArrayList<String> collectList = Stream.of("first").collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        assert collectList.size() == 1;
        //HashMap例子 注意：此方法可以避免下面使用"toMap"时如果k或者v其中一个为null时，所导致的空指针问题
        List<Student> students = prepareTestData();
        HashMap<String, Student> collectMap = students.stream().collect(HashMap::new, (map, e) -> map.put(e.getNo(), e), HashMap::putAll);

        //使用collctors工具类
        List<String> list = Stream.of("second").collect(Collectors.toList());
        Set<String> set = Stream.of("second").collect(Collectors.toSet());
        TreeSet<String> treeSet = Stream.of("one", "two", "three").collect(Collectors.toCollection(TreeSet::new));

        //在流中元素通过指定字符进行连接
        String spaceJoin = Stream.of("Hello", "World").collect(Collectors.joining(" "));
        System.out.println("join test 1:--->" +spaceJoin);//output: Hello World

        String commaJoin = Stream.of("Hello", "World").collect(Collectors.joining(","));
        System.out.println("join test 2:--->" + commaJoin);//output Hello,World

        //当然也可以使用String类提供的方法
        String stringJoin = String.join(" ", "Hello", "World");


        //如果想要获取某个流内的最大最小平均值求和等可以使用summarizing方法
        IntSummaryStatistics summaryStatistics = Stream.of(19, 94, 7, 1).collect(Collectors.summarizingInt(Integer::intValue));
        System.out.println("count :--->" + summaryStatistics.getCount());
        System.out.println("average :---->" + summaryStatistics.getAverage());
        System.out.println("max :---->" + summaryStatistics.getMax());
        System.out.println("min :---->" + summaryStatistics.getMin());
        System.out.println("sum :---->" + summaryStatistics.getSum());
    }


    public static void toMap() {
        List<Student> studentList = prepareTestData();
        //K和V都不可以为空，因为使用的时HasMap的merge方，解决办法参考colections的开头
        Map<String, String> stuMap = studentList.stream().collect(Collectors.toMap(Student::getNo, Student::getName));
        stuMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));

        //按照no进行分组，10086会重复，因此肯定会报错
        System.out.println("############two parameter###############");
        studentList.add(new Student("10086", 20, "scala"));
        try {
            Map<String, String> repeatStuMap = studentList.stream()
                            .collect(Collectors.toMap(Student::getNo, Student::getName));
            repeatStuMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));
        } catch (Exception e) {
            System.out.println("duplicate key exception");
        }

        //因此ToMap的第三个参数用于结局k重复时候的问题
        System.out.println("###########three parameter#################");
        //我们这里采用覆盖
        Map<String, String> sovleRepeatStuMap = studentList.stream()
                .collect(Collectors.toMap(Student::getNo, Student::getName, (existsOne, newOne) -> newOne));
        sovleRepeatStuMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));

        System.out.println("############four parameter################");
        //如果你想获取一个排序的map请使用第四个参数,它接受一个LinkedHashMap
        LinkedHashMap<String, String> linkedHashMap = studentList.stream()
                .collect(Collectors.toMap(Student::getNo, Student::getName, (existsOne, newOne) -> newOne, LinkedHashMap::new));
        linkedHashMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));
    }

    public static List<Student> prepareTestData() {
        List<Student> studentList = new ArrayList<>();
        Student jdk5 = new Student("10015", 20, "java1.5");
        Student jdk6 = new Student("10016", 20, "java1.6");
        Student jdk8 = new Student("10018", 20, "java1.8");
        Student jdk7 = new Student("10017", 20, "java1.7");
        Student jdk9 = new Student("10019", 20, "java1.9");
        Student scala = new Student("10086", 22, "scala");
        Student cPlus = new Student("10087", 22, "c++");
        Student kotlin = new Student("10088", 16, "kotlin");
        studentList.add(jdk5);
        studentList.add(jdk6);
        studentList.add(jdk8);
        studentList.add(jdk9);
        studentList.add(scala);
        studentList.add(jdk7);
        studentList.add(cPlus);
        studentList.add(kotlin);
        return studentList;
    }
}


class Student {
    private String no;
    private Integer age;
    private String name;

    public Student() {
    }

    public Student(String no, Integer age, String name) {
        this.no = no;
        this.age = age;
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "no='" + no + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
