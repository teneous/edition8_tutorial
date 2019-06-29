package src.stream;

import java.util.stream.Stream;

/**
 * Created by syoka on 2017/8/28.
 * return single value
 */
public class Part6_Reduce_Stream {
    public static void main(String[] args) {
        reduceOverLoad_1();
        reduceOverLoad_2();
        reduceOverLoad_3();
    }

    //accept a function
    public static void reduceOverLoad_1() {
        Stream<Integer> stream = Stream.of(100, 200, 300);
        System.out.println("First reduce--->" + stream.reduce((x, y) -> x + y).orElse(0));
    }

    /**
     * first parameter T identity is begin seed,second parameter is the next value
     */
    public static void reduceOverLoad_2() {
        Stream<Integer> stream = Stream.iterate(1, i -> i + 1).limit(100);//1 plus to 100 Gauss story
        //start at 100,the process should like this :100 + 1 = 101 then 101 + 2 = 103 then 103 + 3 = 106 +... = 5150
        System.out.println("1+...+100 = ---->" + stream
                .peek(s -> System.out.print(s + "\t"))
                .reduce(100, Integer::sum));
    }

    public static void reduceOverLoad_3() {
        /*Parallel*/
        Stream<Integer> doubleReduce = Stream.iterate(1, i -> i + 1).limit(100).parallel();
        System.out.println("1+...+100 = ---->" + doubleReduce
                .peek(s -> System.out.print(s + "\t"))
                .reduce(100, Integer::sum));//oops return  : 6650
        /** We can see the problem,when we use parallel java8.stream,The java8.stream has been cut off.The answer 6650-5150=1500.
         so ,maybe the java8.stream divided into 15 parts,and begin seed has been added with 15times repeatedly;
         to avoid this parallel error,the third parameter is a function to let the part's result combine which means to solve parallel case
         */
        Stream<Integer> tripleReduce = Stream.iterate(1, i -> i + 1).limit(100).parallel();//parallel java8.stream
        System.out.println("1+...+100 = ---->" + tripleReduce
                .peek(s -> System.out.print(s + "\t"))
                .reduce(100, Integer::sum, (a, b) -> {
                    System.out.println("a:" + a + "---" + "b:" + b); //
                    return b + a - 100;
                }));//return 5150

        /** Assume that Stream divided into N parts,when we combine the N parts,it'will add begin seed for N times,
         *  so we have to subtract for N java8.times,This is how deos the third parameter (a,b)->(a+b)-100 works
         */
    }
}
