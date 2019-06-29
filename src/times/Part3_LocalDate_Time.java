package src.times;

import java.util.Date;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Part3_LocalDate_Time {
    public static void main(String[] args) {
        localDate();
        localDateCaculate();
        getFirstOrLastDayMonth();
    }

    public static void localDate() {
        //no parameter
        LocalDate now_1 = LocalDate.now();
        System.out.println(".now()-->" + now_1);

        //ZoneId 默认时区
        LocalDate now_2 = LocalDate.now(ZoneId.systemDefault());
        System.out.println(".now(ZoneId)-->" + now_2);
        LocalDate now_3 = LocalDate.now(ZoneId.of("Asia/Shanghai"));//{area/city}
        System.out.println(".now(ZoneId.of())-->" + now_3);

        //Clock
        LocalDate now_4 = LocalDate.now(Clock.systemDefaultZone());
        System.out.println(".now(Clock.)-->" + now_4);
        LocalDate now_5 = LocalDate.now(Clock.system(ZoneId.of("Asia/Shanghai")));
        System.out.println(".now(Clock.(ZoneId.of()))-->" + now_5);

        //of
        LocalDate now_6 = LocalDate.of(1994, 7, 1);
        System.out.println("birthday--->" + now_6);
        LocalDate now_7 = LocalDate.of(1994, Month.JULY, 1);
        System.out.println("birthday--->" + now_7);
    }

    public static void localDateCaculate() {
        LocalDate now = LocalDate.of(1999, 12, 31);
        System.out.println("is leap year--->" + now.isLeapYear());
        //plus
        System.out.println("plus 1 day---->" + now.plusDays(1L));
        System.out.println("plus 2 weeks---->" + now.plusWeeks(2L));
        System.out.println("plus 1 month---->" + now.plusMonths(1L));
        System.out.println("plus -1 month---->" + now.plusYears(-1L));//equals minus 1
        //minus
        System.out.println("minus 1 day---->" + now.minusDays(1L));
        System.out.println("minus 2 weeks---->" + now.minusWeeks(2L));
        System.out.println("minus 1 month---->" + now.minusMonths(1L));
        System.out.println("minus -1 month---->" + now.minusYears(-1L));//equals plus 1
        //modify xx(day,week,year,month)
        System.out.println("only change the year--->" + now.withYear(1994));//change the year to 1994
        //create a leap LocalDate
        LocalDate leap = LocalDate.of(2000, 2, 29);
        System.out.println("leap year--->" + leap.withYear(2001));//change the leap to common,check the day turns to be 28
        //getDayOfWeek
        System.out.println("day of week is --->" + now.getDayOfWeek());//return enum of DayOfWeek
        //compare the localdate
        System.out.println("1999 is before 2000--->" + now.isBefore(leap));
        //period
        Period between = Period.between(now, LocalDate.now().plusYears(1L));//plus 1 year 0 months 0 days
    }


    public static void getFirstOrLastDayMonth(){
        YearMonth ym = YearMonth.parse("time", DateTimeFormatter.ofPattern("yyyy-mm"));
        LocalDate start = ym.atDay(1);//获取这个月第一天
        LocalDate end = ym.atEndOfMonth();//获取这个月最后一天
        //转换为date
        Date startDate = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
