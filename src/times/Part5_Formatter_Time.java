package src.times;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Part5_Formatter_Time {
    public static void main(String[] args) {
        dateTimeFormatter();

        System.out.println(((Date) (DateTimeFormatter.ofPattern("yyyy-MM-dd").parse("2018-05-02"))).getClass());
    }

    public static void dateTimeFormatter() {
        System.out.println("20170903--->" + DateTimeFormatter.BASIC_ISO_DATE.format(LocalDateTime.now()));
        System.out.println("2017-09-03--->" + DateTimeFormatter.ISO_DATE.format(LocalDateTime.now()));
        System.out.println("2017-09-03T18:20:52.228---->" + DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
        System.out.println("18:20:52.228---->" + DateTimeFormatter.ISO_TIME.format(LocalDateTime.now()));
        //customized 自定义
        System.out.println("custome-->" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
        //parse
        LocalDate first = LocalDate.parse("1994-07-01");
        LocalDate second = LocalDate.parse("1994$07$01", DateTimeFormatter.ofPattern("yyyy$MM$dd"));
        System.out.println("first.equals(second)" + first.equals(second));
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
}
