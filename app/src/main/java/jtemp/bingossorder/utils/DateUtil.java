package jtemp.bingossorder.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by y66676 on 2016/4/5.
 */
public class DateUtil {

    private DateUtil(){

    }
    public static int getCurYear(){
        Calendar c=Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }
//    获取当前月份
    public static int getCurMonth(){
        Calendar c=Calendar.getInstance();
        return   c.get(Calendar.MONTH) + 1;
    }
    public static int getCurDay(){
        Calendar c=Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }
    public static int getCurYear(long time){
        Calendar c=Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }
//    获取当前月份
    public static int getCurMonth(long time){
        Calendar c=Calendar.getInstance();
        return   c.get(Calendar.MONTH) + 1;
    }
    public static int getCurDay(long time){
        Calendar c=Calendar.getInstance();
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public static List<Integer> getRecent3Months(){
        Calendar c=Calendar.getInstance();
        Date now =new Date();
        c.setTime(now);
        List<Integer> list=new ArrayList<Integer>();
        for(int i=0;i<3;i++){
            list.add(c.get(Calendar.MONTH)+1);
            c.add(Calendar.MONTH,1);
        }
        return list;
    }
////    获取三个月之内的日期
//    public static List<DateModel> getDatesforRecent3Months(){
//        Calendar start=Calendar.getInstance();
//        Date now =new Date();
//        start.setTime(now);
//        Calendar end=Calendar.getInstance();
//        end.setTime(now);
//        end.add(Calendar.MONTH, 3);
//        end.set(Calendar.DAY_OF_MONTH,1);
////        end.set(Calendar.DAY_OF_MONTH,-1);
//        List<DateModel> list=new ArrayList<DateModel>(92);
//        DateModel dm0=new DateModel();
//        dm0.date=now;
//        list.add(dm0);
//        while (true){
//            start.add(Calendar.DAY_OF_MONTH, 1);
//            if(start.getTimeInMillis() < end.getTimeInMillis()){
//                DateModel dm=new DateModel();
//                dm.date=start.getTime();
//                list.add(dm);
//            }else{
//                break;
//            }
//
//        }
//        return list;
//
//    }
    public static  String pattern ="yy-MM-dd HH:mm";
    public static  String pattern2 ="yyyy-MM-dd";
    public static  String pattern3 ="yyyy-MM-dd HH:mm";
    public static  String pattern4 ="HH:mm";
    public static  String pattern5="MM-dd";

    public static String getStart_endTimeStr(long time_start,long end_time){
        if(time_start==end_time){
            return DateUtil.dateFormat(DateUtil.pattern3, time_start) ;
        }
        if(getCurYear(time_start)==getCurYear(end_time)&&getCurMonth(time_start)==getCurMonth(end_time)&&getCurDay(time_start)==getCurDay(end_time)){
            return DateUtil.dateFormat(DateUtil.pattern3, time_start) +" - "+ DateUtil.dateFormat(DateUtil.pattern4,end_time);
        }else{
            return DateUtil.dateFormat(DateUtil.pattern3, time_start) +" - "+ DateUtil.dateFormat(DateUtil.pattern3,end_time);
        }
    }
    public static String getStart_endTimeStr(String time_start,String time_end){
        long start_time = Long.parseLong(time_start);
        long end_time = Long.parseLong(time_end);
        return getStart_endTimeStr(start_time,end_time);
    }

//    public static List<HourModel> getCurDayHours(List<HourModel> list,Date d) {
//       list.clear();
//       String time1 ="10:00"; String time2="22:00";
//        SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd");
//       String date_str= sdf.format(d);
//        String date_time1=date_str+" "+time1;
//        String date_time2=date_str+" "+time2;
//        long time1_long =dateFormat(pattern,date_time1);
//        long time2_long=dateFormat(pattern,date_time2);
//        for(long i=time1_long;i<=time2_long; i+=30*60*1000){
//            HourModel hm=new HourModel();
//            hm.time=dateFormat("HH:mm",i);
//            hm.time_stamp=i;
//            list.add(hm);
//        }
//
//        return list;
//    }
    /**
     * 字符串日期转时间戳
     */
    public static long dateFormat(String pattern, String dateStr)
    {
        try
        {
            SimpleDateFormat sFormat = new SimpleDateFormat(pattern);
            long timeMillis = sFormat.parse(dateStr).getTime();
            return timeMillis;
        } catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * 将剩余秒转换为  12:23  格式
     * @param lastSeconds
     * @return
     */
    public static String ConvertLastTime (int lastSeconds){
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("%02d",lastSeconds/60)+":");
        int second = lastSeconds%60;
        buffer.append(String.format("%02d",second));
        return  buffer.toString();
    }
    /**
     * 获得时间戳对应字符串日期
     */
    public static String dateFormat(String pattern, long timeMillis)
    {
        SimpleDateFormat sFormat = new SimpleDateFormat(pattern);
        String date = sFormat.format(new Date(timeMillis));
        return date;
    }
    /**
     * 获得时间戳对应字符串日期
     */
    public static String dateFormatFromStr(String pattern, String time)
    {
        try{
            long timeMillis = Long.parseLong(time);
            SimpleDateFormat sFormat = new SimpleDateFormat(pattern);
            String date = sFormat.format(new Date(timeMillis));
            return date;
        } catch (Exception e)
        {
            return "";
        }
    }
//    评论
    public static String convertTime(long time) {
        //获取time距离当前的秒数
        int ct = (int)((System.currentTimeMillis() - time)/1000);
        if(ct <= 0) {
            return "刚刚";
        }

        if(ct > 0 && ct < 60) {
            return ct + "秒前";
        }

        if(ct >= 60 && ct < 3600) {
            return Math.max(ct / 60, 1) + "分钟前";
        }
        if(ct >= 3600 && ct < 86400)
            return ct / 3600 + "小时前";
        if(ct >= 86400 && ct < 2592000){ //86400 * 30
            int day = ct / 86400 ;
            return day + "天前";
        }
        if(ct >= 2592000 && ct < 31104000) { //86400 * 30
            return ct / 2592000 + "月前";
        }
        return ct / 31104000 + "年前";
    }
//    聊天记录
    public static String convert2DateTime(long time){
        int ct = (int)((System.currentTimeMillis() - time)/1000);
        String temp="";
        if(time==0) return temp;
        if(ct<=86400){
            temp=dateFormat(pattern4,time);
        }else if(ct>=86400&&ct<2592000){
            temp=dateFormat(pattern5,time);
        }else {
            temp=dateFormat(pattern2,time);
        }
        return temp;
    }


}
