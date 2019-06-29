package src.stream;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Part7_Collect_Stream {
    public static void main(String[] args) {
        toArray();
        colectins();
        toMap();
    }

    public static void toArray() {
        Stream<String> stream = Stream.of("first", "second", "third");
        Object[] objects = stream.toArray();//return object array
        String[] stringArray = stream.toArray(String[]::new);//accept E::new to return E array
    }

    /***
     * colectins accept three parameter.
     * First --->target constructor
     * Second --->add element into target (method)
     * Third --->put two object together such as addAll(method)
     * In fact ,we usually use Collectors
     */
    public static void colectins() {
        ArrayList<String> firstStream = Stream.of("first").collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        List<String> list = Stream.of("second").collect(Collectors.toList());
        Set<String> set = Stream.of("second").collect(Collectors.toSet());
        TreeSet<String> treeSet = Stream.of("one", "two", "three").collect(Collectors.toCollection(TreeSet::new));
        //concat two element with split character
        System.out.println("join test 1:--->" + Stream.of("join", "test").collect(Collectors.joining(" ")));//return join test
        System.out.println("join test 2:--->" + Stream.of("join", "test").collect(Collectors.joining("||")));//return join || test
        //if you wanna get a object which contains java8.stream.max/java8.stream.min/java8.stream.average/java8.stream.sum etc
        IntSummaryStatistics summaryStatistics = Stream.of(19, 94, 7, 1).collect(Collectors.summarizingInt(Integer::intValue));//return int like Integer::intValue
        System.out.println("count :--->" + summaryStatistics.getCount());
        System.out.println("average :---->" + summaryStatistics.getAverage());
        System.out.println("max :---->" + summaryStatistics.getMax());
        System.out.println("min :---->" + summaryStatistics.getMin());
        System.out.println("sum :---->" + summaryStatistics.getSum());
    }

    public static void toMap() {
        List<Student> studentList = prepareTestData();
        //both K and V can't be null
        Map<String, String> stuMap = studentList.stream().collect(Collectors.toMap(Student::getNo, Student::getName));
        stuMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));

        //when add one already exists ,see what happen
        System.out.println("############two parameter###############");
        studentList.add(new Student("10086", 20, "scala"));
        try {
            Map<String, String> repeatStuMap = studentList.stream().collect(Collectors.toMap(Student::getNo, Student::getName));
            repeatStuMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));//trow Exception
        } catch (Exception e) {
            System.out.println("duplicate key exception");
        }

        System.out.println("###########three parameter#################");
        //第三个参数用于k重复时候的对应策略
        Map<String, String> sovleRepeatStuMap = studentList.stream().
                collect(Collectors.toMap(Student::getNo, Student::getName, (existsOne, newOne) -> newOne));//recover
        sovleRepeatStuMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));

        System.out.println("############four parameter################");
        //如果你想获取一个排序的map请使用第四个参数
        LinkedHashMap<String, String> linkedHashMap = studentList.stream().collect(Collectors.toMap(Student::getNo, Student::getName, (existsOne, newOne) -> newOne, LinkedHashMap::new));//recover
        linkedHashMap.forEach((k, v) -> System.out.println("key-->" + k + " value-->" + v));
    }

    public static List<Student> prepareTestData() {
        List<Student> studentList = new ArrayList<>();
        Student jdk5 = new Student("10015", 20, "java1.5");
        Student jdk6 = new Student("10016", 20, "java1.6");
        Student jdk8 = new Student("10018", 20, "java1.8");
        Student jdk7 = new Student("10017", 20, "java1.7");
        Student jdk9 = new Student("10019", 20, "java1.9");
        Student cPlus = new Student("10087", 22, "c++");
        Student kotlin = new Student("10088", 16, "kotlin");
        studentList.add(jdk5);
        studentList.add(jdk6);
        studentList.add(jdk8);
        studentList.add(jdk9);
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
