/**
 * All rights Reserved, Designed By www.tydic.com
 *
 * @Title: TimeUtils.java
 * @Package cn.startrails.boboiscoming.mg.util
 * @Description: 用一句话描述该文件做什么
 * @author: 陈晓君
 * @date: 2019年3月22日 上午10:47:46
 * @version V1.0
 * @Copyright: 2019 www.tydic.com Inc. All rights reserved.
 */
package com.xo.common.utils;

import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class TimeUtils {
    /**
     * 一年
     */
    public static final long DEFAULT_YEAR = 31536000L * 1000L;
    /**
     * 一天
     */
    public static final long DAY = 24L * 60L * 60L * 1000L;

    /**
     * @Title: getSevenDayTime
     * @Description: 获取当前时间六天之后的时间戳
     * @author: 陈晓君
     */
    public static String getSevenDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,
                calendar.get(Calendar.DAY_OF_YEAR) + 7);
        return calendar.getTimeInMillis() + "";
    }


    public static String stampStrToTime(String timeStr) {
        SimpleDateFormat format = new SimpleDateFormat("MM.dd HH:mm");
        Date date = new Date(Long.parseLong(timeStr));
        return format.format(date);
    }

    public static String getSevenEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR,
                calendar.get(Calendar.DAY_OF_YEAR) + 6);
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND, 59);
        //将毫秒至999
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() + "";
    }

    /**
     * @Title: getThisMonthTime
     * @Description: 获取指定日期所在月份开始的时间戳
     * @author: 陈晓君
     */
    public static String getMonthBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis() + "";
    }

    /**
     * @Title: getThisMonthTime
     * @Description: 获取指定日期所在月份结束的时间戳
     * @author: 陈晓君
     */
    public static String getMonthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //设置为当月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        //将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        c.set(Calendar.MINUTE, 59);
        //将秒至59
        c.set(Calendar.SECOND, 59);
        //将毫秒至999
        c.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        return c.getTimeInMillis() + "";

    }


    /**
     * @throws
     * @Title: getCurrentTime
     * @Description: 获取系统当前的时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getCurrentTime() {
        return System.currentTimeMillis() + "";
    }

    /**
     * @throws
     * @Title: getMorningTime
     * @Description: 获取某天凌晨0点的时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getDayStartTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis() + "";

    }

    /**
     * @throws
     * @Title: getNightTime
     * @Description: 获取某天的最晚的时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getDayEndTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND, 59);
        //将毫秒至999
        calendar.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        return calendar.getTimeInMillis() + "";
    }

    //获取本周的开始时间
    public static String getBeginDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() + "";
    }


    //获取本周的结束时间
    public static String getEndDayOfWeek() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 8 - dayofweek);
        //将小时至23
        cal.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        cal.set(Calendar.MINUTE, 59);
        //将秒至59
        cal.set(Calendar.SECOND, 59);
        //将毫秒至999
        cal.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        return cal.getTimeInMillis() + "";
    }
    //获取本周的开始时间
    public static long getBeginDayOfWeek(long time) {
        Date date = new Date(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        //将毫秒至0
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }


    //获取本周的结束时间
    public static long getEndDayOfWeek(long time) {
        Date date = new Date(time);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 8 - dayofweek);
        //将小时至23
        cal.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        cal.set(Calendar.MINUTE, 59);
        //将秒至59
        cal.set(Calendar.SECOND, 59);
        //将毫秒至999
        cal.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        return cal.getTimeInMillis();
    }
    /**
     * @Title: getThisMonthTime
     * @Description: 获取指定日期所在月份开始的时间戳
     * @author: 陈晓君
     */
    public static String getMonthBeginExpire(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        //设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 8);
        //将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        c.set(Calendar.MINUTE, 0);
        //将秒至0
        c.set(Calendar.SECOND, 0);
        //将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis() + "";
    }

    /**
     * @throws
     * @Title: getLastMonthBegin
     * @Description: 最近一个月开始的时间戳
     * @param:
     * @return: void
     * @author: 陈晓君
     */
    public static String getRecentMonthBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return calendar.getTimeInMillis() + "";

    }

    /**
     * @throws
     * @Title: getLastMonthBegin
     * @Description: 最近一个月结束的时间戳
     * @param:
     * @return: void
     * @author: 陈晓君
     */
    public static String getRecentMonthEnd() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, +1);
        //将小时至23
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        //将分钟至59
        calendar.set(Calendar.MINUTE, 59);
        //将秒至59
        calendar.set(Calendar.SECOND, 59);
        //将毫秒至999
        calendar.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        // 获取本月第一天的时间戳
        return calendar.getTimeInMillis() + "";

    }

    /**
     * @throws
     * @Title: getRecentSevenDayBegin
     * @Description: 最近一个周的开始时间
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getRecentWeekDayBegin() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -6);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return calendar.getTimeInMillis() + "";
    }

    /**
     * @throws
     * @Title: getLastWeekBeginTime
     * @Description: 上一周的开始时间
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getLastWeekBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        calendar.add(Calendar.DATE, 2 - dayofweek - 7);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return calendar.getTimeInMillis() + "";

    }

    /**
     * @throws
     * @Title: getLastWeekEndTime
     * @Description: 上一周的结束时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getLastWeekEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayofweek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        calendar.add(Calendar.DATE, 2 - dayofweek - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() + "";

    }

    /**
     * @throws
     * @Title: getLastMonthBeginTime
     * @Description: 上个月的开始时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getLastMonthBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return calendar.getTimeInMillis() + "";
    }

    /**
     * @throws
     * @Title: getLastMonthEndTime
     * @Description: 上个月的结束时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getLastMonthEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 2, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 2, day);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() + "";
    }

    // 获取今年是哪一年
    public static Integer getNowYear() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return Integer.valueOf(gc.get(1));
    }

    // 获取本月是哪一月
    public static int getNowMonth() {
        Date date = new Date();
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        return gc.get(2) + 1;
    }


    /**
     * @Title: stampToDate
     * @Description: 由时间戳的Long型转换成时间yyyy - MM - dd的字符串格式
     * @author: 陈晓君
     */
    public static String stampToDate(Long s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(s);
        return format.format(date);
    }

    /**
     * 将时间转换成 年-月-日格式
     *
     * @param date 时间对象
     * @return string
     */
    public static String dataToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }

    /**
     * 将时间转换成 年月日格式
     *yyyyMMdd
     * @param date 时间对象
     * @return string
     */
    public static String formatDataToString(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }
    /**
     * 将时间转为年月日时分秒格式
     */
    public static String wxFormatTime(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }
    /**
     * 将时间转换成 年月日格式
     *yyyy年MM月dd日

     * @return string
     */
    public static String formatLongToDate(String time) {
        Date date=new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }


    /**
     * @throws
     * @Title: stampToTimeStamp
     * @Description: 由时间戳的Long型转换成时间yyyy - MM - dd HH : mm : ss的字符串格式
     * @param: @param s
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String stampToTimeStamp(Long s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(s);
        return format.format(date);
    }

    /**
     * @throws
     * @Title: stampToTimeStamp
     * @Description: 由时间戳的Long型转换成时间yyyy - MM - dd HH : mm : ss的字符串格式
     * @param: @param s
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String onlyDate(Long s) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(s);
        return format.format(date);
    }

    /**
     * 把日期转换成dd的字符串
     * @param date 日期
     * @return String
     */
    public static String parseDateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.format(date);
    }

    /**
     * 比较本月和上月的日期，返回天数多的月份的号数
     * @param nowMonths 本月
     * @param lastMonths 上月
     * @return List<String>
     */
    public static List<String> compareMonth(List<Date> nowMonths, List<Date> lastMonths){
        List<String> xAxis=new ArrayList<>();
        if(nowMonths.size()>=lastMonths.size()){
            for(Date date:nowMonths){
                xAxis.add(TimeUtils.parseDateToStr(date));
            }
        }else{
            for(Date date:lastMonths){
                xAxis.add(TimeUtils.parseDateToStr(date));
            }
        }
        return xAxis;
    }
    /**
     * @param s 时间戳
     * @return String 时间字符串
     * @author 陈晓君
     * @date 2019/5/
     * 把时间戳转换成小时的字符串返回
     */
    public static String stampToHour(Long s) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(s);
        return format.format(date);
    }

    /**
     * @throws
     * @Title: stampToTimeStamp
     * @Description: 由时间戳的Long型转换成时间yyyy - MM - dd HH : mm的字符串格式
     * @param: @param s
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String stampToTime(Long s) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        Date date = new Date(s);
        return format.format(date);
    }

    /**
     * @param dateStr
     * @throws
     * @Title: parseToDate
     * @param: @throws Exception
     * @return: Date
     * @author: 陈晓君
     */
    public static Date parseToDate(String dateStr) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return format.parse(dateStr);

    }

    /**
     * @return timestamp 时间戳
     * @author 陈晓君
     * @date 2019/5/
     * 计算昨天的开始时间戳
     */

    public static String getBeginDayOfYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        return getDayStartTime(time);

    }

    /**
     * @return timestamp 时间戳
     * @author 陈晓君
     * @date 2019/5/8
     * * @Description:计算昨天结束时间戳
     */
    public static String getEndDayOfYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        return getDayEndTime(time);
    }


    /**
     * @param datetime 时间戳
     * @return string 星期
     * @author 陈晓君
     * @date 2019/5/
     * 时间戳字符串转换成date
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(Long.parseLong(datetime));
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return weekDays[Math.max(w, 0)];
    }

    public static int getDayOfMonth(long time) {
        Date date = new Date(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * @param sourceTime
     * @return
     * @deac 计算会员过期时间一年
     */
    public static String computeExpireTime(String sourceTime) {
        return computeExpireTime(sourceTime, DEFAULT_YEAR);
    }

    /**
     * @param sourceTime
     * @param computeTime
     * @return
     * @desc 计算会员过期时间
     */
    public static String computeExpireTime(String sourceTime, Long computeTime) {
        Long expireTime;
        if (StringUtils.isEmpty(sourceTime)
                || Long.parseLong(sourceTime) <= System.currentTimeMillis()) {
            expireTime = System.currentTimeMillis() + computeTime;
        } else {
            expireTime = Long.parseLong(sourceTime) + computeTime;
        }
        return expireTime + "";
    }

    public static String format(Date date, String pattern) {
        //创建一个格式化日期对象
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }


    /**
    * 本月的Date列表 a=0 ,上月 a=-1;
     */
    public static List<Date> getMonthDay(int a){
        List<Date> dates=new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() -1+a, 1);
        int day = calendar.getActualMaximum(5);
        for(int i=0;i<day;i++){
            calendar.set(getNowYear(), getNowMonth() - 1+a, 1+i);
            dates.add(calendar.getTime());
        }
        return dates;
    }

    /**
     * @throws
     * @Title: getMorningTime
     * @Description: 获取某天凌晨0点的时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getLoginTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return calendar.getTimeInMillis() + "";

    }

    /**
     * 根据传入时间获取该日期是今年的第几周, 如果12月1日和新年的1月1日同一周会返回 第一周
     * @param time 毫秒级时间戳
     * @return int 第几周
     */
    public static int getWeekOfYear(String time) {
        Calendar weekTemplate = Calendar.getInstance();
        weekTemplate.setTimeInMillis(Long.parseLong(time));
        weekTemplate.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        weekTemplate.setFirstDayOfWeek(Calendar.MONDAY);
        System.out.println(weekTemplate.getTimeInMillis());
        return weekTemplate.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取毫秒级时间戳年份
     * @param time 毫秒级时间戳
     * @return int 年份
     */
    public static int getYear(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(time));
        return calendar.get(Calendar.YEAR);
    }


    /**
     * @throws
     * @Title: getLastMonthBeginTime
     * @Description: 从当前系统时间，过a天后的开始时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static Date getDayBeginTime(int a) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,a);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        /*calendar.set(Calendar.MILLISECOND, 0);*/
        // 获取本月第一天的时间戳
        return calendar.getTime();
    }

    /**
     * @throws
     * @Title: getLastMonthEndTime
     * @Description: 从当前系统时间，过a天后的结束时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static Date getDayEndTime(int a) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,a);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
       /* calendar.set(Calendar.MILLISECOND, 999);*/
        return calendar.getTime();
    }



    /**
     * @throws
     * @Title: getLastMonthBeginTime
     * @Description: 上个月的开始时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getPastMonthBeginTime(int a) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1-a, 1);
        //将小时至0
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        //将分钟至0
        calendar.set(Calendar.MINUTE, 0);
        //将秒至0
        calendar.set(Calendar.SECOND, 0);
        //将毫秒至0
        calendar.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return calendar.getTimeInMillis() + "";
    }

    /**
     * @throws
     * @Title: getLastMonthEndTime
     * @Description: 上个月的结束时间戳
     * @param: @return
     * @return: String
     * @author: 陈晓君
     */
    public static String getPastMonthEndTime(int a) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(getNowYear(), getNowMonth() - 1-a, 1);
        int day = calendar.getActualMaximum(5);
        calendar.set(getNowYear(), getNowMonth() - 1-a, day);

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis() + "";
    }

    public static int getLongTimeMonth(String time){
        Date date=new Date(Long.parseLong(time));
        return date.getMonth()+1;
    }

    /**
     * 判断今天是星期几  0 周日 1 2 3 4 5 6 周一到周六
     * @return
     */
    public static int getTodayInWeek(){
        Date today = new Date();
        Calendar c= Calendar.getInstance();
        c.setTime(today);
        int weekday= c.get(Calendar.DAY_OF_WEEK)-1;
        if(weekday==0){
            weekday=7;
        }
        return weekday;
    }

    /**
     * 获取今年第一天
     * @param timestamp 时间戳
     * @return 时间戳
     */
    public static String getYearBegin(String timestamp) {
        return getYearBegin(new Date(Long.parseLong(timestamp)));
    }/**
     * 获取今年最后一天
     * @param timestamp 时间戳
     * @return 时间戳
     */
    public static String getYearEnd(String timestamp) {
        return getDayEndTime(new Date(Long.parseLong(timestamp)));
    }
    /**
     * 获取今年第一天
     * @return 时间戳
     */
    public static String getYearBegin(){
        return getYearBegin(new Date());
    }
    /**
     * 获取今年最后一天
     * @return 时间错
     */
    public static String getYearEnd(){
        return getYearEnd(new Date());
    }
    /**
     * 获取某年第一天开始时间
     * @param date {@link Date}
     * @return 时间戳
     */
    public static String getYearBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 1);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis() + "";
    }

    /**
     *  获取某年的最后一天
     * @param date {@link Date}
     * @return 时间戳
     */
    public static String getYearEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.roll(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTimeInMillis() + "";
    }

    /**
     * 获取某天的所有整点时间 ex:14:00:00 13:00:00  01:00:00 - 00:00:00
     * @param date
     * @return
     */
    public static List<Date> getDayAllHour(Date date) throws Exception {
        String startTime = TimeUtils.getDayStartTime(date);

        date = new Date();
        date.setTime(Long.parseLong(startTime));
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for(int i = 0; i<24;i++){
            calendar.add(Calendar.HOUR_OF_DAY,1);
            dates.add(calendar.getTime());
        }

        return dates;
    }

    public static Date getExpireTime(Date date){
        if(date==null || date.before(new Date())){
            date=new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR,1);
        return calendar.getTime();
    }

    /**
     * 获取当前小时的开始时间
     */
    public static Date getDateHourBeginning(Date date) throws Exception {
        //已小时为单位
        SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd HH");
        String formatDateHour = pattern.format(date);
        //获取时间
        date = pattern.parse(formatDateHour);
        return date;
    }
}

