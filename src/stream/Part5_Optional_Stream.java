package src.stream;

import java.util.Optional;

/**
 * optional主要是为了防止空指针，可以理解它在对象在做了一层封装
 * this is a simply demo for optional
 */
public class Part5_Optional_Stream {
    public static void main(String[] args) {
        createOptional();
        assertOptional();
        mapAndFlatMapOptional();
    }

    public static void createOptional() {
        /*create an optional object*/
        Optional<String> first = Optional.of("first");
        Optional<Object> empty = Optional.empty();
        Optional<Object> nullOptional = Optional.ofNullable(null);//equals Optional.empty()
        Optional<Object> StringOptional = Optional.ofNullable("first");
    }


    public static void assertOptional() {
        //创建一个空的optinal
        Optional<String> source = Optional.empty();
        //写法一
        if (source.isPresent()) {
            System.out.println("Source is empty");
        }
        source.ifPresent(s-> System.out.println("Source is empty"));

        //写法二
        source = Optional.of("YuZaSou");
        source.ifPresent(value -> System.out.println("IfPresent value is -->" + value));

        //如果元素存在则返回元素值
        System.out.println("Get value is ----> " + source.get());

        //如果元素不存在则执行orElse
        Optional<String> emptyOptional = Optional.ofNullable(null);
        System.out.println("OrElse :--->" + emptyOptional.orElse("auto create orElse"));

        //如果元素不存在则执行orElseGet中的元素
        System.out.println("OrElseGet :--->" + emptyOptional.orElseGet(() -> {
            return "create by orElseGet";
        }));

        //如果元素不存在则跑异常
        try {
            System.out.println("OrElseThrow :--->" + emptyOptional.orElseThrow(IllegalArgumentException::new));
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException");
        }
    }


    /**
     * optional的map方法，依然是变更元素
     */
    public static void mapAndFlatMapOptional() {
        Optional<String> optional = Optional.of("strawberry");
        //比如全部变为大写
        System.out.println("Map:--->" + optional.map(String::toUpperCase));

        //等同于optional.empty
        System.out.println("null Map:--->" + optional.map(x -> x = null));

        /*FlatMap和map返回差不多，区别在于它返回值必定是optional对象，另外FlatMap不会自动帮你包装结果，你需要自己包装*/
        System.out.println("FlatMap :--->" + optional.flatMap(s -> Optional.of(s.toUpperCase())).orElse("null value"));

        //from book 写给大忙人看的Java SE 8
        System.out.println("FlatMap ---->" + Optional.of(16D)
                .flatMap(Part5_Optional_Stream::getInverse) // 1/16
                .flatMap(Part5_Optional_Stream::getSqureRoot));// 1/4
    }

    public static Optional<Double> getInverse(Double start) {
        return start == 0 ? Optional.empty() : Optional.of(1 / start);
    }

    public static Optional<Double> getSqureRoot(Double start) {
        return start < 0 ? Optional.empty() : Optional.of(Math.sqrt(start));
    }
}
