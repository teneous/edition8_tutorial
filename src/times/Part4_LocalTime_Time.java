package src.times;

import java.time.Clock;
import java.time.LocalTime;
import java.time.ZoneId;

public class Part4_LocalTime_Time {
    public static void main(String[] args) {
        localTime();
        localTimeCalculate();
    }

    public static void localTime() {
        //no parameter
        LocalTime time_1 = LocalTime.now();
        System.out.println(".now()-->" + time_1);

        //ZoneId
        LocalTime time_2 = LocalTime.now(ZoneId.systemDefault());
        System.out.println(".now(ZoneId)-->" + time_2);
        LocalTime time_3 = LocalTime.now(ZoneId.of("Asia/Shanghai"));//{area/city}
        System.out.println(".now(ZoneId.of())-->" + time_3);

        //Clock
        LocalTime time_4 = LocalTime.now(Clock.systemDefaultZone());
        System.out.println(".now(Clock.)-->" + time_4);
        LocalTime time_5 = LocalTime.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        System.out.println(".now(Clock.(ZoneId.of()))-->" + time_5);

        //of
        LocalTime time_6 = LocalTime.of(18, 30); //minute,second
        System.out.println("overrload_1--->" + time_6);
        LocalTime time_7 = LocalTime.of(22, 30, 30);//int hour, int minute, int second, int nanoOfSecond
        System.out.println("overrload_2--->" + time_7);
        LocalTime time_8 = LocalTime.of(22, 30, 30, 30);//int hour, int minute, int second, int nanoOfSecond
        System.out.println("overrload_3--->" + time_8);
        LocalTime time_9 = LocalTime.ofSecondOfDay(18 * 60 * 60);//24*60*60-1
        System.out.println("overrload_4--->" + time_9);

    }

    public static void localTimeCalculate() {
        LocalTime now = LocalTime.of(18, 30);
        //plus
        System.out.println("plus 100 nanos---->" + now.plusNanos(100L));
        System.out.println("plus 2 Seconds---->" + now.plusSeconds(20L));
        System.out.println("plus 1 minutes---->" + now.plusMinutes(10L));
        System.out.println("plus -1 hours---->" + now.plusHours(-10L));//equals minus 10
        //minus
        System.out.println("minus 1 nanos---->" + now.minusNanos(100L));
        System.out.println("minus 2 Seconds---->" + now.minusSeconds(20L));
        System.out.println("minus 1 minutes---->" + now.minusMinutes(10L));
        System.out.println("minus -1 hours---->" + now.minusHours(-10L));//equals plus 10
        //modify xx(day,week,year,month)
        System.out.println("only change the hour--->" + now.withHour(6));//change the year to 6AM
        //getHour
        System.out.println("current hour is --->" + now.getHour());//return enum of DayOfWeek
        //compare the LocalTime
        System.out.println("is before current localTime--->" + now.isBefore(LocalTime.now()));
    }
}
