package com.roydon.common.utils.date;

import com.roydon.common.utils.StringUtil;
import org.apache.commons.lang3.RandomUtils;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Desc 描述：在日期格式中HH:mm:ss表示24小时制，hh:mm:ss表示12小时制
 * @Date 2018年5月31日 下午4:26:57
 */
public class DateUtil {

    // 一天的毫秒数
    public static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;
    public static final long ONE_HOUR_MS = 1 * 60 * 60 * 1000;
    public static final long FIVE_MINUTE_MS = 5 * 60 * 1000;

    private static ThreadLocal<SimpleDateFormat> dateFormatLocal1 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal2 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal3 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal4 = new ThreadLocal<>();
    // private static DateFormat statDateFormat = new SimpleDateFormat("MM-dd
    // HH:mm");

    private static ThreadLocal<SimpleDateFormat> dateFormatLocal5 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal6 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal7 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal8 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal9 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal10 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal11 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal12 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal13 = new ThreadLocal<>();
    private static ThreadLocal<SimpleDateFormat> dateFormatLocal14 = new ThreadLocal<>();

    private static final long CURRENT_NANOS = System.currentTimeMillis() * 1000 * 1000;
    private static final long CURRENT_BEGIN_NANOS = System.nanoTime();

    public static long currentNanos() {
        long gap = System.nanoTime() - CURRENT_BEGIN_NANOS;
        long currentNaos = gap + CURRENT_NANOS;
        return currentNaos;
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return getDateFormat3().format(date);
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDay2(Date date) {
        return getDateFormat6().format(date);
    }

    private static SimpleDateFormat getDateFormat1() {
        SimpleDateFormat dateFormat = dateFormatLocal1.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormatLocal1.set(dateFormat);
        }
        return dateFormat;
    }

    public static SimpleDateFormat getDateFormat2() {
        SimpleDateFormat dateFormat = dateFormatLocal2.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            dateFormatLocal2.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 格式化后日期格式： yyyyMMddHHmmssSSS
     *
     * @param date
     * @return
     */
    public static String formatDateAll(Date date) {
        return getDateFormat7().format(date);
    }

    public static LocalDate string2LocalDate(String date, String pattern) {
        pattern = pattern == null ? "yyyy-MM-dd" : pattern;
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static String formatDateLong(Long date, String pattern) {
        return formatDate(new Date(date), pattern);
    }

    public static String formatDate(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static LocalDateTime string2LocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String localDate2String(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String localDate2YMString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    public static String localDateTime2String(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String localDateTime2StringMillis(LocalDateTime datetime) {
        return datetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    /**
     * 清除时分秒、毫秒域
     *
     * @param date
     * @return
     */
    public static long getDayTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private static SimpleDateFormat getDateFormat3() {
        SimpleDateFormat dateFormat = dateFormatLocal3.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatLocal3.set(dateFormat);
        }
        return dateFormat;
    }

    public static SimpleDateFormat getDateFormat4() {
        SimpleDateFormat dateFormat = dateFormatLocal4.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("HH:mm:ss");
            dateFormatLocal4.set(dateFormat);
        }
        return dateFormat;
    }

    public static SimpleDateFormat getDateFormat6() {
        SimpleDateFormat dateFormat = dateFormatLocal6.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyyMMdd");
            dateFormatLocal6.set(dateFormat);
        }
        return dateFormat;
    }

    public static SimpleDateFormat getDateFormat7() {
        SimpleDateFormat dateFormat = dateFormatLocal7.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            dateFormatLocal7.set(dateFormat);
        }
        return dateFormat;
    }

    public static SimpleDateFormat getDateFormat8() {
        SimpleDateFormat dateFormat = dateFormatLocal8.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
            dateFormatLocal8.set(dateFormat);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormat5() {
        SimpleDateFormat dateFormat = dateFormatLocal5.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("MM-dd HH:mm");
            dateFormatLocal5.set(dateFormat);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormat9() {
        SimpleDateFormat dateFormat = dateFormatLocal9.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            dateFormatLocal9.set(dateFormat);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormat10() {
        SimpleDateFormat dateFormat = dateFormatLocal10.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormatLocal10.set(dateFormat);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormat11() {
        SimpleDateFormat dateFormat = dateFormatLocal11.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormatLocal11.set(dateFormat);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormat12() {
        SimpleDateFormat dateFormat = dateFormatLocal12.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
            dateFormatLocal11.set(dateFormat);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormat13() {
        SimpleDateFormat dateFormat = dateFormatLocal13.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            dateFormatLocal13.set(dateFormat);
        }
        return dateFormat;
    }

    private static SimpleDateFormat getDateFormat14() {
        SimpleDateFormat dateFormat = dateFormatLocal14.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            dateFormatLocal14.set(dateFormat);
        }
        return dateFormat;
    }

    /**
     * 将日期转化为String 格式：MMddHHmm
     *
     * @param date
     * @return
     */
    public static String dateToMMDDHHMM(Date date) {
        return getDateFormat5().format(date);
    }

    /**
     * 将日期转化为String 格式：MMddHHmm
     *
     * @param date
     * @return
     */
    public static String dateToMMDDHHMM(long date) {
        return getDateFormat5().format(date);
    }

    public static String dateToyyyyMMddHHmm(long date) {
        return getDateFormat9().format(date);
    }

    /**
     * 毫秒转 yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String dateToyyyyMMddHHmmss(long date) {
        return getDateFormat10().format(date);
    }

    public static String dateToyyyyMMddHHmmss(LocalDateTime date) {
        return dateToyyyyMMddHHmmss(DateUtil.localDateTime2Long(date));
    }

    public static String dateToyyyyMMddHHmmssSSS(long date) {
        return dateToyyyyMMddHHmmssSSS(new Date(date));
    }

    public static String dateToyyyyMMddHHmmssSSS(Date date) {
        return getDateFormat12().format(date);
    }

    public static String dateToFriendlyDisplayTime(Date date) {
        return getDateFormat13().format(date);
    }

    public static String dateToFriendlyDisplayTime(long date) {
        return dateToFriendlyDisplayTime(new Date(date));
    }

    public static String dateToFriendlyDisplayDate(Date date) {
        return getDateFormat14().format(date);
    }

    public static String dateToFriendlyDisplayDate(long date) {
        return dateToFriendlyDisplayDate(new Date(date));
    }

    public static Date parseDateFormatLocal10(String str) {
        Date date = null;
        try {
            date = getDateFormat10().parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToyyyy(long date) {
        return getDateFormat11().format(date);
    }

    public static LocalDateTime yyyyMMddHHmmssToDate(String str) {
        try {
            final Date date = getDateFormat10().parse(str);
            return long2LocalDateTime(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 描述：将毫秒数的时间转为字符串时间：yyyy-MM-dd HH:mm:ss
     *
     * @param time
     * @return <p>
     * 2016年11月3日 下午4:03:22
     */
    public static String millisToStr(long time) {
        Date date = new Date(time);
        return getDateFormat1().format(date);

    }

    /**
     * @param ms
     * @return
     * @Desc 描述：将毫秒数转为日期
     * @Date 2017年3月17日 下午8:19:15
     */
    public static Date getFormatDateFromMS(long ms) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ms);
        return cal.getTime();
    }

    /**
     * 将日期转化为String 格式：yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String dateToYYYYMMDDHHMMSS(Date date) {
        return getDateFormat2().format(date);
    }

    /**
     * 获取当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getDateStampNow() {
        return dateToYYYYMMDDHHMMSS();
    }

    /**
     * 将日期转化为String 格式：yyyyMMddHHmmss
     *
     * @return
     */
    public static String dateToYYYYMMDDHHMMSS() {
        return dateToYYYYMMDDHHMMSS(new Date());
    }

    public static String getYYYYMMDDHHMMSS(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return localDateTime.format(formatter);
    }

    /**
     * 将String 格式：yyyyMMddHHmmss转化为日期
     *
     * @param dateStr
     * @return
     */
    public static Date stringYYYYMMDDHHMMSSToDate(String dateStr) {
        try {
            return getDateFormat2().parse(dateStr);
        } catch (ParseException e) {
        }
        return null;
    }

    /**
     * 获取日期。格式：yyyy-MM-dd
     *
     * @param date
     * @return 2015-6-29 下午3:56:40
     */
    public static String dateToStardardYYYYMMDD(Date date) {
        return getDateFormat3().format(date);
    }

    /**
     * @param millSecond
     * @return
     * @Desc 描述：根据给定的毫秒数，计算这个毫秒数代表的时间的下一天的零点时间戳。
     * @Date 2017年5月24日 上午9:50:19
     */
    public static long getTomorrowZeroTime(long millSecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millSecond);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.set(Calendar.DAY_OF_YEAR, day + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * @return int
     * @Description 获取时间是一年中的那一天
     */
    public static int todayOfYear(long selectTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectTime);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取当前年份
     *
     * @param nowtime
     * @return 2016年2月11日 上午12:07:36
     */
    public static int getCurrentYear(long nowtime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(nowtime);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 字符串格式转毫秒,
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return 2015-5-20 上午10:50:34
     * @throws ParseException
     */
    public static long strDateToMills(String date) throws ParseException {
        long day = strDateToDate(date).getTime();
        return day;
    }

    /**
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static long strDateToSeconds(String date) throws ParseException {
        long seconds = 0;
        seconds = strDateToMills(date) / 1000;
        return seconds;
    }

    /**
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     * @throws ParseException
     */
    public static Date strDateToDate(String date) throws ParseException {
        return getDateFormat1().parse(date);
    }

    public static Date strDateToDate2(String date) throws ParseException {
        return getDateFormat2().parse(date);
    }

    /**
     * 字符串格式转毫秒,
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return 2015-5-20 上午10:50:34
     * @throws ParseException
     */
    public static long strDateToMills2(String date) throws ParseException {
        long day = 0;
        day = getDateFormat2().parse(date).getTime();
        return day;
    }

    /**
     * 字符串格式转毫秒,
     *
     * @param date yyyy-MM-dd HH:mm:ss
     * @return 2015-5-20 上午10:50:34
     * @throws ParseException
     */
    public static long strDateToMills3(String date) throws ParseException {
        long day = 0;
        day = getDateFormat6().parse(date).getTime();
        return day;
    }

    /**
     * 获取一天的时分秒：格式:hh:mm:ss
     *
     * @param date
     * @return
     */
    public static String timeOfDayHHMMSS(Date date) {
        return getDateFormat4().format(date);
    }

    /**
     * yyyy-MM-dd-HH-mm-ss
     *
     * @param date
     * @return
     */
    public static String timeofAll(Date date) {
        return getDateFormat8().format(date);
    }

    /**
     * 获取时间格式为：HH:MM的形式
     *
     * @param date
     * @return 2016年1月7日 下午6:55:25
     */
    public static String timeOfDayHHMM(Date date) {
        SimpleDateFormat timerOfDayFormater12 = new SimpleDateFormat("HH:mm");
        return timerOfDayFormater12.format(date);
    }

    /**
     * 获取时间格式为：HH:MM的形式
     *
     * @param time
     * @return 2016年1月7日 下午6:55:25
     */
    public static String timeOfDayHHMM(long time) {

        return timeOfDayHHMM(new Date(time));
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 得到几天前的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.add(Calendar.DATE, -day);
        return now.getTime();
    }

    public static String getStartTimeByDayWithFormat(Date d) {
        Date dayTime = getStartTimeByDay(d);
        final String startString = DateUtil.dateToyyyyMMddHHmmss(dayTime.getTime());
        return startString;
    }

    public static String getLastTimeByDayWithFormat(Date d) {
        Date dayTime = DateUtil.getLastTimeByDay(d);
        final String endString = DateUtil.dateToyyyyMMddHHmmss(dayTime.getTime());
        return endString;
    }

    /**
     * 获取当天时间的开始时间 即：yyyy-MM-dd 00:00:00
     *
     * @param d
     * @return
     */
    public static Date getStartTimeByDay(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);
        return now.getTime();
    }

    /**
     * 获取当天时间的最后时间 即：yyyy-MM-dd 23:59:59
     *
     * @param d
     * @return
     */
    public static Date getLastTimeByDay(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.HOUR_OF_DAY, 23);
        now.set(Calendar.MINUTE, 59);
        now.set(Calendar.SECOND, 59);

        return now.getTime();
    }

    /**
     * 判断是否同一天
     *
     * @param time1
     * @param time2
     * @return true 同一天；false 不在同一天
     */
    public static boolean isOneDay(long time1, long time2) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time1);
        int year1 = calendar.get(Calendar.YEAR);
        int month1 = calendar.get(Calendar.MONTH);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTimeInMillis(time2);

        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        if (year1 == year2 && month1 == month2 && day1 == day2) {
            return true;
        }

        return false;
    }

    /**
     * 返回两个时间之间相差的天数(默认参数1比参数2小),单位毫秒
     *
     * @param startTime
     * @param currentTime
     * @return
     */
    public static long differenceNumberOfDays(long startTime, long currentTime) {
        long len = currentTime - startTime;
        if(len % ONE_DAY_MS == 0){
            return Math.abs(len / ONE_DAY_MS);
        }
        return Math.abs(len / ONE_DAY_MS) + 1;
    }

    /**
     * 描述：获取当前时间的字符串格式时间：yyyy-MM-dd HH:mm:ss
     *
     * @return <p>
     * 2016年11月3日 下午7:01:28
     */
    public static String getNowDateStr() {
        Date date = new Date();
        return dateToStr(date);
    }

    /**
     * 描述：date转string字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return <p>
     * 2016年11月3日 下午6:59:52
     */
    public static String dateToStr(Date date) {
        return getDateFormat1().format(date);

    }

    /**
     * <p>
     * Title: isDayOfWeek
     * </p>
     * <p>
     * Description: 判断今天是周几
     * </p>
     *
     * @param day 按正常的周几输入，比如周一，就是1
     */
    public static boolean isDayOfWeek(int day) {
        Calendar strDate = Calendar.getInstance();
        if ((strDate.get(Calendar.DAY_OF_WEEK) - 1) == day) {
            return true;
        }
        return false;
    }

    /**
     * <p>
     * Title: getWeekDayStr
     * </p>
     * <p>
     * Description: 获取今天是星期几
     * </p>
     *
     * @param date   日期
     * @param prefix 星期，输出 当前日期为 星期几
     */
    public static String getWeekDayStr(Date date, String prefix) {
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(date);
        String weekDay = "";
        switch (strDate.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                weekDay = "日";
                break;
            case Calendar.MONDAY:
                weekDay = "一";
                break;
            case Calendar.TUESDAY:
                weekDay = "二";
                break;
            case Calendar.WEDNESDAY:
                weekDay = "三";
                break;
            case Calendar.THURSDAY:
                weekDay = "四";
                break;
            case Calendar.FRIDAY:
                weekDay = "五";
                break;
            case Calendar.SATURDAY:
                weekDay = "六";
                break;
            default:
                //忽略
        }

        return prefix + weekDay;
    }

    /**
     * @param hhmmss
     * @return
     * @Desc 描述：把一个hh:mm:ss的时间转化为对应的毫秒数，时间格式必须是24小时制
     * @Date 2018年5月31日 下午4:19:40
     */
    public static long hhmmssTomills(String hhmmss) {
        if (hhmmss == null) {
            throw new IllegalArgumentException("时间格式不能为null" + ",应该是hh:mm:ss的格式");
        }
        String[] strs = hhmmss.split(":");
        if (strs.length != 3) {
            throw new IllegalArgumentException("时间格式不正确：" + hhmmss + ",应该是hh:mm:ss的格式");
        } else {
            int h = StringUtil.valueOfInt(strs[0]);
            int m = StringUtil.valueOfInt(strs[1]);
            int s = StringUtil.valueOfInt(strs[2]);
            long mills = (h * 3600L + m * 60L + s) * 1000L;
            return mills;
        }

    }

    public static long todayHhMmSsToMillis(String hhmmss) {
        if (hhmmss == null) {
            throw new IllegalArgumentException("时间格式不能为null" + ",应该是hh:mm:ss的格式");
        }
        String[] strs = hhmmss.split(":");
        if (strs.length != 3) {
            throw new IllegalArgumentException("时间格式不正确：" + hhmmss + ",应该是hh:mm:ss的格式");
        } else {
            return LocalDateTime.of(LocalDate.now(), LocalTime.parse(hhmmss)).atZone(ZoneId.systemDefault()).toInstant()
                    .toEpochMilli();
        }

    }

    public static Long localDate2Long(LocalDate localDate) {
        return localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static Long localDateTime2Long(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    public static Long localTime2Long(LocalTime localTime) {
        return localTime.toSecondOfDay() * 1000L;
    }

    public static LocalDate long2LocalDate(long timestamp) {
        return new Date(timestamp).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime long2LocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalTime long2LocalTime(long timestamp) {
        return LocalTime.ofSecondOfDay(timestamp / 1000);
    }

    // 一天内剩余的毫秒数
    public static long getLeftMillSecondsInDay() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        long millSeconds = ChronoUnit.MILLIS.between(LocalDateTime.now(), midnight);
        return millSeconds;
    }

    public static LocalDateTime getTodayPlusStartLocalDateTime(int day) {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return todayStart.plusDays(day);
    }

    public static LocalDateTime getTodayStartLocalDateTime() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return todayStart;
    }

    public static LocalDateTime getTodayEndLocalDateTime() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return todayStart;
    }

    public static LocalDateTime getEndLocalDateTime(LocalDate localDate) {
        LocalDateTime todayStart = LocalDateTime.of(localDate, LocalTime.MAX);
        return todayStart;
    }

    public static LocalDateTime getEndLocalDateTime(LocalDateTime localDateTime) {
        LocalDateTime todayStart = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        return todayStart;
    }

    public static LocalDateTime getStartLocalDateTime(LocalDateTime localDateTime) {
        LocalDateTime todayStart = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return todayStart;
    }

    // 获取当天零点时间对象
    public static long getTodayStartLong() {
        return DateUtil.localDateTime2Long(getTodayStartLocalDateTime());
    }

    public static long getTodayEndLong() {
        return DateUtil.localDateTime2Long(getTodayEndLocalDateTime());
    }

    public static long getTodayPlusStartLocalLong(int day) {
        return DateUtil.localDateTime2Long(getTodayPlusStartLocalDateTime(day));
    }

    public static void main(String[] args) {

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		System.out.println(sdf.format(new Date()));
//		Date date = new Date();
//		System.out.println(sdf.format(date));
//		System.out.println(sdf.format(new Date(date.getTime() + 1000 * 60)));
//
//		System.out.println(long2LocalDateTime(Long.MAX_VALUE));
        System.out.println(DateUtil.dateToyyyyMMddHHmmssSSS(1620797774822L));
        System.out.println(friendlyDisplayTime(10L));
        System.out.println(friendlyDisplayTime(1000L));
        System.out.println(friendlyDisplayTime(100000L));

        System.out.println(dateToFriendlyDisplayTime(1620797774822L));

    }

    public static String getBeforeTimeStr(Date date) {
        if (date.getTime() < 1000) {
            return date.getTime() + "毫秒";
        }

        long detSec = date.getTime() / 1000;
        int month = 2592000;
        int day = 86400;
        int hour = 3600;
        int min = 60;

        String result;

        if (detSec > month) {
            // 大于一个月，直接显示时间
            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
            result = dateFormat.format(date.getTime());
        } else if (detSec > day) {
            // 大于一天，显示几天前
            result = (int) (detSec / day) + "天";
        } else if (detSec > hour) {
            // 大于一小时，显示几小时前
            result = (int) (detSec / hour) + "小时";
        } else if (detSec > min) {
            // 大于一分钟，显示几分钟前
            result = (int) (detSec / min) + "分钟";
        } else {
            // 显示一分钟前
            result = detSec + "秒";
        }

        return result;
    }

    /**
     * 验证字符串是否为指定日期格式
     *
     * @param dateStr 待验证字符串
     * @param pattern 日期字符串格式, 例如 "yyyy-MM-dd"
     * @return 有效性结果, true 为正确, false 为错误
     */
    public static boolean isValid(String dateStr, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            // 转化为 Date类型测试判断
            date = dateFormat.parse(dateStr);
            return dateStr.equals(dateFormat.format(date));
        } catch (Exception e) {
            return false;
        }
    }

    public static Date parseDateString(String dateStr, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            // 转化为 Date类型测试判断
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    private static void setMinTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    private static void setMaxTimeOfDay(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    /**
     * 获取两个时间节点之间的月份列表
     **/
    public static List<String> getMonthBetween(Date minDate, Date maxDate) {
        ArrayList<String> result = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();
        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * 取范围日期的随机日期时间,不含边界
     *
     * @param startDay
     * @param endDay
     * @return
     */
    public static LocalDateTime randomLocalDateTime(int startDay, int endDay, boolean before) {

        int plusMinus = 1;
        if (startDay < 0 && endDay > 0) {
            plusMinus = Math.random() > 0.5 ? 1 : -1;
            if (plusMinus > 0) {
                startDay = 0;
            } else {
                endDay = Math.abs(startDay);
                startDay = 0;
            }
        } else if (startDay < 0 && endDay < 0) {
            plusMinus = -1;

            startDay = startDay + endDay;
            endDay = startDay - endDay;
            startDay = startDay - endDay;

            startDay = Math.abs(startDay);
            endDay = Math.abs(endDay);

        }
        if (before) {
            plusMinus = -1;
        }
        LocalDate day = LocalDate.now().plusDays(plusMinus * RandomUtils.nextInt(startDay, endDay));
        int hour = RandomUtils.nextInt(1, 24);
        int minute = RandomUtils.nextInt(0, 60);
        int second = RandomUtils.nextInt(0, 60);
        LocalTime time = LocalTime.of(hour, minute, second);
        return LocalDateTime.of(day, time);
    }

    /**
     * 获取月份天数
     *
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当月天数
     *
     * @return
     */
    public static int getDaysOfCurrentMonth() {
        return getDaysOfMonth(new Date());
    }

    //日期比较
    public static int compareDate(Date first, Date second){
        return first.compareTo(second);
    }

    /**
     * 友好显示时间
     * @param ms
     * @return
     */
    public static String friendlyDisplayTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) {
            sb.append(day+"天");
        }
        if(hour > 0) {
            sb.append(hour+"小时");
        }
        if(minute > 0) {
            sb.append(minute+"分");
        }
        if(second > 0) {
            sb.append(second+"秒");
        }
        if(milliSecond > 0) {
            sb.append(milliSecond+"毫秒");
        }
        return sb.toString();
    }

    public static Object formatDate(Object object){
        if (object == null) {
            return null;
        }

        Long time = null;
        if (object instanceof Timestamp) {
            time = ((Timestamp) object).getTime();
        } else if (object instanceof LocalDateTime) {
            time = DateUtil.localDateTime2Long((LocalDateTime) object);
        } else if (object instanceof LocalDate) {
            time = DateUtil.localDate2Long((LocalDate) object);
        }

        if (time != null){
            return dateToyyyyMMddHHmmss(time);
        }

        return time;
    }

    public static Long parseDate(Object object) {
        if (object == null) {
            return null;
        }
        Long time = null;
        if (object instanceof Timestamp) {
            time = ((Timestamp) object).getTime();
        } else if (object instanceof LocalDateTime) {
            time = DateUtil.localDateTime2Long((LocalDateTime) object);
        } else if (object instanceof LocalDate) {
            time = DateUtil.localDate2Long((LocalDate) object);
        } else if (object instanceof Long) {
            time = (Long) object;
        } else if (object instanceof String) {
            final Date date = parseDateFormatLocal10((String) object);
            if (date != null){
                time = date.getTime();
            }
        }
        return time;
    }

}
