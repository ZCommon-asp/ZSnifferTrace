package com.mango.sniffertrace.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * @author: JacX.
 * @description:
 * @Date: 2021/7/16 10:35
 * @Modified By:JacX.
 * @see
 * @since
 */
@UtilityClass
public class DateUtil {

    /**
     * 时间格式
     */
    @Getter
    @AllArgsConstructor
    public enum DateFormat{
        yyyyMMdd("yyyyMMdd", "yyyy年MM月dd日"),
        yyyyMMddHHmm("yyyyMMddHHmm", "yyyy年MM月dd日HH时mm分"),
        yyyyMMddHHmmss("yyyyMMddHHmmss", "yyyy年MM月dd日HH时mm分ss秒"),
        ;
        private String value;
        private String desc;
    }

    public static long getRemainNowByDay(int day) {
        LocalDateTime midnight = LocalDateTime.now()
                .plusDays(day).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    public static long getRemainNowByMonth(int month) {
        LocalDateTime midnight = LocalDateTime.now()
                .plusMonths(month).with(TemporalAdjusters.firstDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }


    /*** 描述:返回当前日期N个月份之后的第1天
     * @author: JacX.
     * @date 2021/3/26 9:43
     * @param srcDate:
     * @param month:
     * @param descPattern:
     * @return java.lang.String
     * @see
     */
    public static String plusOfMonthFirst(String srcDate, Integer month, String descPattern) {
        return LocalDate.parse(srcDate, DateTimeFormatter.ofPattern(DateFormat.yyyyMMdd.value)).plus(month, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern(descPattern));
    }

    public static String plusOfMonthFirst(int month, String pattern) {
        return LocalDate.now().plus(month, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String nextMonthFirst() {
        return plusOfMonthFirst(1, DateFormat.yyyyMMdd.value);
    }


    public static String plusOfMonthLast(String srcDate, Integer month, String descPattern) {
        return LocalDate.parse(srcDate, DateTimeFormatter.ofPattern(DateFormat.yyyyMMdd.value)).plus(month, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern(descPattern));
    }

    public static String plusOfMonthLast(int month, String pattern) {
        return LocalDate.now().plus(month, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String plusOfMonthAndDayLast(int month, int day, String pattern) {
        return LocalDate.now().plus(month, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth()).plusDays(day).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String plusOfDay(int amountToAdd, String pattern) {
        return LocalDateTime.now().plus(amountToAdd, ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String plusOfChrono(int amountToAdd, ChronoUnit unit, String pattern) {
        return LocalDateTime.now().plus(amountToAdd, unit).format(DateTimeFormatter.ofPattern(pattern));
    }


    /**
     * 描述:
     * 获取两个时间的间隔
     *
     * @param startDate:开始时间
     * @param endDate:结束时间
     * @param pattern:时间格式
     * @param chronoUnit:间隔类型
     * @return long
     * @author: JacX.
     * @date 2020/1/13 9:17
     * @see
     */
    public static Long dateIntervalOfType(String startDate, String endDate, String pattern, ChronoUnit chronoUnit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return end.until(start, chronoUnit);
    }

    public static long dateIntervalOfType(String startDate, String pattern, ChronoUnit chronoUnit) {
        return dateIntervalOfType(startDate, getNow(pattern), pattern, chronoUnit);
    }


    public static long distanceDays(LocalDate startDate) {
        return distanceDays(LocalDate.now(), startDate);
    }

    public static long distanceDays(LocalDate endDate, LocalDate startDate) {
        return endDate.until(startDate, ChronoUnit.DAYS);
    }


    /**
     * @param {[]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/2/28 9:40
     * @description: 以yyyyMMdd的时间格式获取当前时间
     * @see
     */
    public static String getCurrentDay() {
        return getCurrentDay(DateFormat.yyyyMMdd.value);
    }

    public static String getCurrentDay(String pattern) {
        return getBeforeDay(0, pattern);
    }

    /**
     * @param {[]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/2/28 9:40
     * @description: 以yyyyMMdd的时间格式获取前一天时间
     * @see
     */
    public static String getBeforeDay(String pattern) {
        return getBeforeDay(1, pattern);
    }

    /**
     * @param {[]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/2/28 9:40
     * @description: 以yyyyMMdd的时间格式获取前N天时间
     * @see
     */
    public static String getBeforeDay(int day, String pattern) {
        LocalDate beforeDay = LocalDate.now().minusDays(day);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return beforeDay.format(format);
    }


    /***
     * @author: JacX.
     * @date 2019/4/15 9:52
     * @description: 获取今天之后的日期, 格式：yyyyMMdd
     * @param {[day]}
     * @return java.lang.String
     * @see
     */
    public static String getAfterDay(int day) {
        return getAfterDay(day, DateFormat.yyyyMMdd.value);
    }

    /***
     * @author: JacX.
     * @date 2019/4/15 9:51
     * @description: 获取今天之后的日期
     * @param {[day, pattern]}
     * @return java.lang.String
     * @see
     */
    public static String getAfterDay(int day, String pattern) {
        LocalDate afterDay = LocalDate.now().plusDays(day);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return afterDay.format(format);
    }


    public static String getAfterByPrecision(Integer amountToAdd) {
        return getAfterByPrecision(amountToAdd, DateFormat.yyyyMMddHHmmss.value, ChronoUnit.MINUTES);
    }

    /**
     * @param {[day, pattern, chronoUnit]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/6/17 10:52
     * @description: 获取自定义时间精度
     * @see
     */
    public static String getAfterByPrecision(Integer amountToAdd, String pattern, ChronoUnit chronoUnit) {
        if (null == amountToAdd) {
            return "";
        }
        LocalDateTime afterDay = LocalDateTime.now().plus(amountToAdd, chronoUnit);
        DateTimeFormatter format = DateTimeFormatter.ofPattern(pattern);
        return afterDay.format(format);
    }

    /**
     * @param {[days]}
     * @return java.lang.Long
     * @author: JacX.
     * @date 2019/2/28 9:39
     * @description: 获取往前推算N天的时间戳
     * @see
     */
    public static Long getDayTimestamp(int days) {
        return LocalDateTime.now().minusDays(days).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * @param {[days]}
     * @return java.lang.Long
     * @author: JacX.
     * @date 2019/2/28 9:39
     * @description: 获取当前时间的时间戳
     * @see
     */
    public static Long getDayTimestamp() {
        return LocalDateTime.now().minusDays(0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    public static Long getTimestampByDay(int year, int month, int dayOfMonth) {
        System.out.println(LocalDateTime.of(year, month, dayOfMonth, 0, 0));
        return LocalDateTime.of(year, month, dayOfMonth, 0, 0).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * @param {[]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/2/28 9:40
     * @description: 以yyyyMMddHHmmss的时间格式获取当前时间
     * @see
     */
    public static String getNow() {
        return getNow(DateFormat.yyyyMMddHHmmss.value);
    }

    /**
     * @param {[pattern]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/2/28 9:40
     * @description: 以pattern的时间格式获取当前时间
     * @see
     */
    public static String getNow(String pattern) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
    }


    public static String strDateFormat(String date) {
        if (null == date || "".equals(date)) {
            return date;
        }
        return dateFormat(date, "yyyyMMddHHmm", "yyyy年MM月dd日");
    }

    /**
     * @param {[date, srcPattern, destPattern]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/2/28 9:43
     * @description: 将date从srcPattern转化为destPattern
     * @see
     */
    public static String dateFormat(String date, String srcPattern, String destPattern) {
        if (null == date || "".equals(date)) {
            return date;
        }
        String datetime = date;
        try {
            DateTimeFormatter srcDf = DateTimeFormatter.ofPattern(srcPattern);//
            LocalDateTime ldt = LocalDateTime.parse(date, srcDf);

            DateTimeFormatter destDf = DateTimeFormatter.ofPattern(destPattern);//
            datetime = ldt.format(destDf);
        } catch (Exception e) {
            return datetime;
        }
        return datetime;
    }


    /**
     * @param {[date, plusDay, srcPattern, destPattern]}
     * @return java.lang.String
     * @author: JacX.
     * @date 2019/6/26 11:05
     * @description: 获取指定时间之后N天的时间
     * @see
     */
    public static String getAssignAfterDay(String date, int plusDay, String srcPattern, String destPattern) {
        DateTimeFormatter srcDf = DateTimeFormatter.ofPattern(srcPattern);//
        LocalDateTime ldt = LocalDateTime.parse(date, srcDf);
        LocalDateTime plusDate = ldt.plusDays(plusDay);
        DateTimeFormatter destDf = DateTimeFormatter.ofPattern(destPattern);//
        return plusDate.format(destDf);

    }

    public static long dateInterval(String upDay, String lowDay, String pattern) {
        LocalDate upDate = parseStrToDateTime(upDay, pattern).toLocalDate();
        LocalDate lowDate = parseStrToDateTime(lowDay, pattern).toLocalDate();
        return upDate.until(lowDate, ChronoUnit.DAYS);
    }

    public static String dateFormat(String date) {
        return dateFormat(date, "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss");
    }

    public static String dateOfdayFormat(String date) {
        return dateFormat(date, "yyyyMMddHHmmss", "yyyy-MM-dd");
    }

    /**
     * yyyy/MM/dd/HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateFormat1(String date) {
        return dateFormat(date, "yyyyMMddHHmmss", "yyyy/MM/dd/HH:mm:ss");
    }


    public static Long getDateTimestamp(String dateTime) {
        return dataTimeToTimestamp(parseStrToDateTime(dateTime, "yyyyMMdd"));

    }

    public static Long getDateTimestamp(String dateTime, String format) {
        return dataTimeToTimestamp(parseStrToDateTime(dateTime, format));

    }

    public static LocalDateTime parseStrToDateTime(String dateTime, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        if (format.length() <= 8) {
            return LocalDate.parse(dateTime, df).atStartOfDay();
        }
        return LocalDateTime.parse(dateTime, df);
    }


    public static long dataTimeToTimestamp(LocalDateTime ldt) {
        return ldt.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }


    /**
     * @param {[date, pattern]}
     * @return long  betweenDay
     * @author: JacX.
     * @date 2019/9/17 14:54
     * @description:
     * @see
     */
    public static Long getBetweenDayNow(String date, String pattern) {
        return ChronoUnit.DAYS.between(LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern)), LocalDate.now());
        //return Period.between(LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern)), LocalDate.now()).getDays();
    }


    public static boolean isValidExcelDate(double value) {
        return value > -4.9E-324D;
    }


//     public static void main(String[] args) {
//         System.out.println(plusOfDay(1, "yyyyMMddHHmmss"));
// //        System.out.println("当前日 = " + test.get(test.DAY_OF_MONTH));
//         int x = 0, y = 2, z = 1;
//         y += (z-- / ++x);
//
//         System.out.println("www:" + LocalDate.parse("20200203", DateTimeFormatter.ofPattern("yyyyMMdd")).plus(6, ChronoUnit.MONTHS));
//
//         System.out.println("dateIntervalOfType:" + getDateTimestamp("20200203143818", "yyyyMMddHHmmss"));
//         System.out.println("dateIntervalOfType:" + (DateUtil.getDateTimestamp(DateUtil.plusOfMonthLast(6, "yyyyMMdd"), "yyyyMMdd") - DateUtil.getDateTimestamp("20200203143818", "yyyyMMddHHmmss") + ""));
//         System.out.println(y--);
//         System.out.println("plusOfMonthLast:" + plusOfMonthLast(5, "yyyyMMdd"));
//         System.out.println(dateIntervalOfType("20191101", "yyyyMMdd", ChronoUnit.DAYS));
//         System.out.println(dateIntervalOfType("20191216", "yyyyMMdd", ChronoUnit.MONTHS));
//         System.out.println(dateIntervalOfType("20191201", "yyyyMMdd", ChronoUnit.YEARS));
//         System.out.println("getBetweenNow:" + getBetweenDayNow("20190516231512", "yyyyMMddHHmmss"));
//         System.out.println("2019-04-15 00:00:00".compareTo("2019-04-15 00:00:00.0"));
//         System.out.println(getBeforeDay(7, "yyyy-MM-dd"));
//         System.out.println(getBeforeDay(7, "yyyy-MM-dd").compareTo("null"));
//         System.out.println(getDateTimestamp("2019-05-22 00:08:40", "yyyy-MM-dd HH:mm:ss"));
//         System.out.println(getAfterByPrecision(null, "yyyyMMdd", ChronoUnit.MINUTES));
//         System.out.println(getAfterDay(30));
//         System.out.println(getBeforeDay(Integer.MAX_VALUE - 1, "yyyyMMdd"));
//         System.out.println(getBeforeDay(30 - 1, "yyyyMMdd").compareTo("20190521103625"));
//         System.out.println(getAssignAfterDay("20190618102849", 30, "yyyyMMddHHmmss", "yyyyMMddHHmmss"));
//         //System.out.println("20190626".compareTo(null));
//         System.out.println("20190626".compareTo(""));
//         System.out.println("20190626".compareTo("null"));
//         System.out.println(getTimestampByDay(2019, 02, 03));
//         System.out.println(getAfterByPrecision(30, "yyyyMMddHHmmss", ChronoUnit.DAYS));
//         System.out.println(getDateTimestamp("20190203"));
//         System.out.println(getAfterDay(36500));
//         System.out.println(dateInterval("20190825125300", "20190827235900", "yyyyMMddHHmmss"));
//         System.out.println(dateFormat1("20190825125359"));
//         System.out.println(dateFormat("20190903122359", "yyyyMMddHHmmss", "yyyy年MM月dd日"));
//         System.out.println(getAfterDay(1, "yyyyMMdd" + "235959"));
//
//         System.out.println("20191003125959".compareTo("20191004010101"));
//         /**
//          * 返回给前端的日历对象
//          * day           日期
//          * isSign       是否签到过
//          * isPrize      是否有奖品
//          * isMendSign   是否可以补签
//          * isToday      是否是今天
//          * prizeId      如果有奖品的话 奖品id
//          *
//          */
//
//         System.out.println("plusOfMonthLast:" + plusOfMonthLast(1, "yyyyMMdd"));
//         System.out.println("plusOfMonthAndDayLast:" + plusOfMonthAndDayLast(1, 4, "yyyyMMdd"));
//
//         System.out.println(DateUtil.plusOfChrono(240, ChronoUnit.MINUTES, "yyyy-MM-dd HH:mm:ss"));
//
//         System.out.println("getRemainNowByDay:" + getRemainNowByDay(1));
//         System.out.println("getRemainNowByDay:" + getRemainNowByMonth(1));
//
//
//     }

}

