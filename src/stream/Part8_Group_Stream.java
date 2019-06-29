package src.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * 分组重点！！
 */
public class Part8_Group_Stream {
    public static void main(String[] args) {
        grounpBy();
        partitionBy();//boolean类型为key
        donwStreamDemo();//自定义V值映射
    }

    /**
     * 同map一样，只是v是集合
     */
    public static void grounpBy() {
        List<Student> studentList = Part7_Collect_Stream.prepareTestData();
        //java8以前的做法
        Map<Integer, List<Student>> jdk6StuMap = new HashMap<>();
        for (Student student : studentList) {
            if (jdk6StuMap.containsKey(student.getAge())) {
                jdk6StuMap.get(student.getAge()).add(student);
            } else {
                List<Student> tempList = new ArrayList<>();
                tempList.add(student);
                jdk6StuMap.put(student.getAge(), tempList);
            }
        }
        jdk6StuMap.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));
        System.out.println("#################java8####################");

        // java 8
        Map<Integer, List<Student>> jdk8StuMap = studentList.stream().collect(Collectors.groupingBy(Student::getAge));
        jdk8StuMap.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));
        System.out.println("##################paralles Map###################");
        ConcurrentMap<Integer, List<Student>> paralleMap =
                studentList.stream()
                        .parallel()
                        .collect(Collectors.groupingByConcurrent(Student::getAge));
        paralleMap.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));
    }


    public static void partitionBy() {
        List<Student> studentList = Part7_Collect_Stream.prepareTestData();
        //map的k只会是true或者false
        Map<Boolean, List<Student>> partionMap = studentList.stream()
                .collect(Collectors.partitioningBy(x -> x.getAge() > 21));
        partionMap.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));
    }


    /**
     * 如果你想对map中的Value进行在处理
     */
    public static void donwStreamDemo() {
        List<Student> studentList = Part7_Collect_Stream.prepareTestData();
        //改变v的类型为set
        Map<Integer, Set<Student>> jdk8StuSet = studentList.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.toSet()));
        jdk8StuSet.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));

        System.out.println("####################count#########################");
        //v为满足条件的数量
        Map<Integer, Long> jdk8StuCount = studentList.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.counting()));
        jdk8StuCount.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));

        System.out.println("####################summint########################");
        //summing
        Map<Integer, IntSummaryStatistics> jdk8StuIntSummary = studentList.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.summarizingInt(x -> Integer.parseInt(x.getNo()))));
        jdk8StuIntSummary.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v.toString())); //value --->IntSummaryStatistics

        System.out.println("####################mapping########################");
        //先按照年龄分组，在获取每个分组中，编号最大的
        Map<Integer, Optional<Student>> jdk8StuMaxBy = studentList.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.maxBy((stu1, stu2) -> {
                    int no1 = Integer.parseInt(stu1.getNo());
                    int no2 = Integer.parseInt(stu2.getNo());
                    return Integer.compare(no1 - no2, 0);
                })));
        jdk8StuMaxBy.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));

        System.out.println("####################mapping########################");
        //可以自定义一个v值映射关系,v为满足k的名字集合
        Map<Integer, List<String>> jdk8StuMappingBy = studentList.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.mapping(Student::getName, Collectors.toList())));
        jdk8StuMappingBy.forEach((k, v) -> System.out.println("k-->" + k + "  v--->" + v));

        //如果想要获得Map<T,Map<X,R>>这种结构可使用如下方法
        Map<Integer, Map<String, List<Student>>> doubleMap = studentList.stream()
                .collect(Collectors.groupingBy(Student::getAge, Collectors.groupingBy(Student::getName)));

    }
}
