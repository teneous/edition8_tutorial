package src.others;


interface GameCharacter {
    void levelUp(Integer times);
}

/**
 * 内部类中使用外部变量必须声明final，我们看看在8版本中的新写法
 */
public class Part1_Final_Edtion {
    public static void main(String[] args) {
        jdk6ComplierSuccess();
        jdk6ComplierError();
        jdk8ComplierSuccess();
        jdk8ComplierError();
        jdk8ComplierChangeSuccess();
    }

    /**
     * if you wanna know why it should be modify by final
     * please click : https://stackoverflow.com/questions/4732544/why-are-only-final-variables-accessible-in-anonymous-class
     */
    private static void jdk6ComplierSuccess() {
        final int level = 1; //外部参数必须定义为final修饰
        GameCharacter oddFinal = new GameCharacter() {
            @Override
            public void levelUp(Integer paramLevel) {
                System.out.println("jdk6ComplierSuccess: inner level is-->" + paramLevel + "  outter level times-->" + level);
            }
        };
        oddFinal.levelUp(level);
    }

    private static void jdk6ComplierError() {
        //如果去掉final修饰符就会有编译报错
        Integer level = 1;
        GameCharacter oddFinal = new GameCharacter() {
            @Override
            public void levelUp(Integer innertime) {
                //报错信息:Variable totalTimes is accessed from within inner class; needs to be declared final
                System.out.println("jdk6ComplierError: inner level times-->" + innertime + "  outter level times-->" + level);
            }
        };
        oddFinal.levelUp(level);
    }

    /**
     * java8编译成功，不需要显式声明为final
     */
    private static void jdk8ComplierSuccess() {
        Integer level = 1;
        GameCharacter oddFinal = innertime ->
                System.out.println("jdk8ComplierSuccess: inner level times-->" + innertime + "  outter level times-->" + level);
        oddFinal.levelUp(level);
    }

    /**
     * 如果在内部类改变了外部值，则会依然会报错
     */
    private static void jdk8ComplierError() {
        Integer level = 1; //没有显示声明final
        GameCharacter oddFinal = innertime -> {
//            totalTimes = 3;//打开注解我们可以看待编译报错
            //报错信息:variable used in lambda expression should be final or effectively final
            System.out.println("jdk8ComplierError: inner level times-->" + innertime + "  outter level times-->" + level);
        };
        oddFinal.levelUp(level);
    }

    /**
     * 对于数组而言，只要引用不变，引用对象内部的属性是可以修改的
     */
    private static void jdk8ComplierChangeSuccess() {
        final Integer[] levels = {1}; //声明一个数组
        GameCharacter oddFinal = innertime -> {
            levels[0] = 3;// 修改数据里面的元素
            System.out.println("jdk8ComplierChangeSuccess: inner level times-->" + innertime + "  outter level times-->" + levels[0]);
        };
        oddFinal.levelUp(levels[0]);
    }
}

