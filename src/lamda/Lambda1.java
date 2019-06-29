package src.lamda;

import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

/**
 * 如何创建lambda
 * Consumer parameter T return void;  accpet(); 消费者接口，传入T返回空
 * Supplier parameter void return T;  get(); 生产者接口，传入空返回T
 * Runnable parameter void return void; run(); 传入空，返回空
 * BiConsumer parameter T,U return void; accpet();传入两个参数T和U 返回空
 * Function parameter T return T  apply(): 函数传入T返回T
 */
public class Lambda1 {
    public static void main(String[] args) {
        basicalSyntaxLamdba();//基础语法
        codeBlockLamdba();//代码块
        ifElseStatementLamdba();//代码块中的条件表达式
    }

    /**
     * Type parameter -> expression
     * 类型 参数 -> 表达式
     */
    private static void basicalSyntaxLamdba() {
        /*例：定义一个BiConsumer (type1 parameter 1，type2 parameter 2)->expression*/
        BiConsumer<String, String> lambdaSimple = (String str1, String str2) -> System.out.println(str1 + "---" + str2);
        lambdaSimple.accept("Hello", "Lamda");

        //类型可以省略，并通过后面被推断出来(parameter 1，parameter 2)->expression
        BiConsumer<String, String> lambdaReference = (str1, str2) -> System.out.println(Integer.parseInt(str1) + "---" + Integer.parseInt(str2));
        lambdaReference.accept("22娘", "23娘");

        //如果没有参数可以简化为()->expresioon
        Runnable lambdaRunnable = () -> System.out.println("like Runnable");
        lambdaRunnable.run();

        /*如果 s ->System.out.println(s)这样可以直接变形System.out::println
        ① class::static method | String::length 类名::静态方法
        ② object::normal method | new String()::length 对象::普通方法
        ③ class::normal method | first-->new String():: then --->length  首先是先new出一个该类对象然后在调用方法
        ④ super::method*/
    }


    /**
     * 如果lamda表达式超过一行，则可以使用{}，类似与代码块
     */
    private static void codeBlockLamdba() {
        BiConsumer<String, String> lambdaBlock = (String str1, String str2) -> {
            System.out.println(str1);
            System.out.println(str2);
            System.out.println(str1 + "---" + str2);
        };
        lambdaBlock.accept("Code", "Block");
    }

    /**
     * labmda表达式必须有一个完整的if与else
     */
    private static void ifElseStatementLamdba() {
        BiPredicate<String, String> lamdaBlock2 = (String str1, String str2) -> {
            if (str1.length() > str2.length()) {
                return true;
            } else {
                return false;
            }
        };
        System.out.println("The result Is----" + lamdaBlock2.test("strawberry", "cherry"));
    }
}
