package src.lamda;

/**
 * 本类介绍如何在lamda表达式中捕获异常
 * Created by syoka on 2017/9/1.
 */
public class Lambda2 {

    public static void main(String[] args) throws Exception {
        catchExcetionWayOne(); //throw it to sup class
        catchExcetionWayTwo();//catch it
        catchExcetionWayThree(new ExceptionHandler());//customized handler catch it
    }

    /**
     * 将异常往方法上抛
     */
    private static void catchExcetionWayOne() throws Exception {
        Runnable catchException = () -> {
            int i = 1 / 0;
        };
        catchException.run();
    }

    /**
     * 内部捕获异常
     */
    private static void catchExcetionWayTwo() {
        Runnable catchException = () -> {
            try {
                int i = 1 / 0;
            } catch (Exception e) {
                System.out.println("catch Exception --->" + e.getClass().getName());
                System.out.println("Message---->" + e.getMessage());
            }
        };
        catchException.run();
    }

    /**
     * 方法2的变形 建议此方法
     * 定义一个消费者函数式接口，然后对其统一处理
     */
    private static void catchExcetionWayThree(ICustomizedExceptionHandler<Exception> exceptionHandler) {
        Runnable catchException = () -> {
            try {
                int i = 1 / 0;
            } catch (ArithmeticException e) {
                exceptionHandler.excute(e);
            }
        };
        catchException.run();
    }
}

//创建一个消费者类型的函数接口
@FunctionalInterface
interface ICustomizedExceptionHandler<T> {
    void excute(T e);
}

class ExceptionHandler implements ICustomizedExceptionHandler<Exception> {
    @Override
    public void excute(Exception e) {
        //do your business
        System.out.println("catch Exception --->" + e.getClass().getName());
    }
}



