package src.times;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/**
 * a day = 86400 seconds
 */
public class Part1_Instant_Time {
    public static void main(String[] args) throws InterruptedException {
        convertDateAndInstant();
        durationRelated();
    }

    public static void durationRelated() throws InterruptedException {
        Instant before = Instant.now();
        Thread.sleep(500);
        Instant after = Instant.now();
        //获取两个时间的间隔
        Duration duration = Duration.between(before, after);
        System.out.println("seconds--->" + duration.toMillis());
        System.out.println("nanos--->" + duration.toNanos());
        System.out.println("minute--->" + duration.toMinutes());
        System.out.println("hours--->" + duration.toHours());
        System.out.println("days--->" + duration.toDays());
    }

    /**
     * 类型转换
     */
    public static void convertDateAndInstant() {
        /*date ---> instant*/
        Instant instant = new Date().toInstant();
        /*instant ---->date*/
        Date from = Date.from(instant);
    }
}
