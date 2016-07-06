package com.jzwgj.management.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;

public class DateUtils
{
  private static final Logger log = Logger.getLogger(DateUtils.class);

  private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  /**start
   * zwq 20150326 
   * @return
   */
  public static String getChar4(){
	  return DateFormatUtils.format(new Date(), "yyyy");
  }
  //end
  
  /**start
   * zwq 20150403
   * @return
   */
  public static boolean comparenow(String time){
		try {
			Timestamp now = new Timestamp(new Date().getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = sdf.parse(time);
			Timestamp targettime = new Timestamp(date.getTime());
			if(now.before(targettime)){
				return true;
			}else {
				return false;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	  
  }
  //end
  

  
  public static String getChar7(){
	  return DateFormatUtils.format(new Date(), "yyyy-MM");
  }
  public static String getChar8()
  {
    return DateFormatUtils.format(new Date(), "yyyyMMdd");
  }
  public static String getChar10()
  {
    return DateFormatUtils.format(new Date(), "yyyy-MM-dd");
  }
  public static String getChar14() {
    return DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
  }
  public static String getChar19() {
    return DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
  }
  public static String getChar12()
  {
    return DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
  }

  public static String getChar6() {
    return DateFormatUtils.format(new Date(), "HHmmss");
  }

  public static String formatChar14(String char14) {
    if ((char14 == null) || (char14.length() == 0))
      return char14;
    return char14.substring(0, 4) + "-" + char14.substring(4, 6) + "-" + char14.substring(6, 8) + " " + char14.substring(8, 10) + ":" + char14.substring(10, 12) + ":" + char14.substring(12, 14) + " ";
  }

  public static String formatChar12(String char14) {
    if ((char14 == null) || (char14.length() == 0))
      return char14;
    return char14.substring(0, 4) + "-" + char14.substring(4, 6) + "-" + char14.substring(6, 8) + " " + char14.substring(8, 10) + ":" + char14.substring(10, 12);
  }

  public static boolean judgeIfExceedEndDate(String srcBeginDate, String srcEndDate, int relatetivelyFlag)
  {
    if (srcEndDate.trim().length() != 8) {
      Calendar cal = Calendar.getInstance();
      cal.set(Integer.parseInt(srcBeginDate.substring(0, 4)), Integer.parseInt(srcBeginDate.substring(4, 6)), Integer.parseInt(srcBeginDate.substring(6, 8)));
      if (relatetivelyFlag == 1) {
        cal.roll(6, Integer.parseInt(srcEndDate));
        cal.roll(1, Integer.parseInt(srcEndDate) / 365);
      }
      else if (relatetivelyFlag == 2) {
        cal.roll(2, Integer.parseInt(srcEndDate));
        cal.roll(1, Integer.parseInt(srcEndDate) / 12);
      }
      srcEndDate = formatToChar8(cal);
    }

    return Long.parseLong(getChar8()) >= Long.parseLong(srcEndDate);
  }

  public static String rollMonth(String srcDate, String rollMonth)
  {
    Calendar cal = Calendar.getInstance();
    cal.set(Integer.parseInt(srcDate.substring(0, 4)), Integer.parseInt(srcDate.substring(4, 6)) - 1, Integer.parseInt(srcDate.substring(6, 8)));
    cal.roll(2, Integer.parseInt(rollMonth));
    cal.roll(1, Integer.parseInt(rollMonth) / 12);
    return formatToChar8(cal);
  }

  public static String formatToChar8(Calendar tmpcal) {
    String tmpYear = Integer.toString(tmpcal.get(1));
    String tmpMonth = Integer.toString(tmpcal.get(2) + 1);
    String tmpDay = Integer.toString(tmpcal.get(5));
    String tmpDate = tmpYear + ((tmpMonth.length() == 1) ? "0" + tmpMonth : tmpMonth) + ((tmpDay.length() == 1) ? "0" + tmpDay : tmpDay);
    return tmpDate;
  }

  public static String formatChar8(String char8) {
    if ((char8 == null) || (char8.length() == 0))
      return char8;
    return char8.substring(0, 4) + "-" + char8.substring(4, 6) + "-" + char8.substring(6, 8) + " ";
  }

  public static String formatCha6(String char6)
  {
    if ((char6 == null) || (char6.length() == 0))
      return char6;
    return char6.substring(0, 2) + ":" + char6.substring(2, 4) + ":" + char6.substring(4, 6);
  }

  public static String rollHour(String dateNow, int rollDate)
  {
    String dateReturn = "";

    if ((dateNow == null) || (dateNow.trim().length() < 14)) {
      return dateReturn;
    }
    dateNow = dateNow.trim();
    Calendar cal = Calendar.getInstance();
    int nYear = Integer.parseInt(dateNow.substring(0, 4));
    int nMonth = Integer.parseInt(dateNow.substring(4, 6));
    int nDate = Integer.parseInt(dateNow.substring(6, 8));
    int nHour = Integer.parseInt(dateNow.substring(8, 10));
    int nMinute = Integer.parseInt(dateNow.substring(10, 12));
    int nSecond = Integer.parseInt(dateNow.substring(12, 14));
    cal.set(nYear, nMonth - 1, nDate, nHour, nMinute, nSecond);

    cal.add(11, rollDate);

    String strYear = String.valueOf(cal.get(1));
    String strMonth = String.valueOf(cal.get(2) + 1);
    String strDay = String.valueOf(cal.get(5));
    String strHour = String.valueOf(cal.get(11));
    String strMinute = String.valueOf(cal.get(12));
    String strSecond = String.valueOf(cal.get(13));
    strMonth = (strMonth.length() == 1) ? "0" + strMonth : strMonth;
    strDay = (strDay.length() == 1) ? "0" + strDay : strDay;
    strHour = (strHour.length() == 1) ? "0" + strHour : strHour;
    strMinute = (strMinute.length() == 1) ? "0" + strMinute : strMinute;
    strSecond = (strSecond.length() == 1) ? "0" + strSecond : strSecond;
    dateReturn = strYear + strMonth + strDay + strHour + strMinute + strSecond;

    return dateReturn;
  }

  @SuppressWarnings("deprecation")
public static void main(String[] args) {
//    System.out.print("" + getTimeToshow());
//    DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");  
//    List<String> list=null;  
//    try{  
//        String beginTime="2013-01-01";  
//        String endTime="2013-01-21";  
//        Calendar startDay = Calendar.getInstance();  
//        Calendar endDay = Calendar.getInstance();  
//        startDay.setTime(FORMATTER.parse(beginTime));  
//        endDay.setTime(FORMATTER.parse(endTime));  
//        // 给出的日期开始日比终了日大则不执行打印  
//        list=new ArrayList<String>();  
//        list.add(beginTime);  
//        if(!beginTime.equals(endTime)){  
//            if(startDay.compareTo(endDay)<=0){  
//                //现在打印中的日期  
//                Calendar currentPrintDay = startDay;  
//                while (true){  
//                    // 日期加一  
//                    currentPrintDay.add(Calendar.DATE, 1);  
//                    // 日期加一后判断是否达到终了日，达到则终止打印  
//                    if (currentPrintDay.compareTo(endDay) == 0) {  
//                        break;  
//                    }  
//                    list.add(FORMATTER.format(currentPrintDay.getTime()));  
//                }  
//                list.add(endTime);  
//            }  
//        }  
//        for (String customer : list)  
//        {  
//            System.out.println(customer);  
//        }  
//    }catch (Exception e){  
//        e.printStackTrace();  
//    }
	  System.out.println(DateFormatUtils.format(new Date(), "yyyy/MM"));
	  getDateMonthList("2014/08/15","2014/12/01");
  }
  
/**
 * 
 * Description: 按照开始日期和结束日期 返回日期结果集
 * @Version2.0 Jun 27, 2014 11:06:41 AM by 刘晋江（ljj@sinontech.com）创建
 * @param beginTime yyyy-MM-dd
 * @param endTime yyyy-MM-dd
 * @return 
 * List<String>
 */
  public static List<String> getDateList(String beginTime,String endTime){
	  DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");  
	    List<String> list=null;  
	    try{  
//	        beginTime="2013-01-01";  
//	        endTime="2013-01-21";  
	        Calendar startDay = Calendar.getInstance();  
	        Calendar endDay = Calendar.getInstance();  
	        startDay.setTime(FORMATTER.parse(beginTime));  
	        endDay.setTime(FORMATTER.parse(endTime));  
	        // 给出的日期开始日比终了日大则不执行打印  
	        list=new ArrayList<String>();  
	        list.add(beginTime);  
	        if(!beginTime.equals(endTime)){  
	            if(startDay.compareTo(endDay)<=0){  
	                //现在打印中的日期  
	                Calendar currentPrintDay = startDay;  
	                while (true){  
	                    // 日期加一  
	                    currentPrintDay.add(Calendar.DATE, 1);  
	                    // 日期加一后判断是否达到终了日，达到则终止打印  
	                    if (currentPrintDay.compareTo(endDay) == 0) {  
	                        break;  
	                    }  
	                    list.add(FORMATTER.format(currentPrintDay.getTime()));  
	                }  
	                list.add(endTime);  
	            }  
	        }  
	        for (String customer : list)  
	        {  
	            System.out.println(customer);  
	        }  
	    }catch (Exception e){  
	        e.printStackTrace();  
	    }
	    return list;
  }
  public static String rollDate(String dateNow, int rollDate)
  {
    String dateReturn = "";
    if ((dateNow == null) || (dateNow.trim().length() < 8)) {
      return dateReturn;
    }
    dateNow = dateNow.trim();
    Calendar cal = Calendar.getInstance();
    int nYear = Integer.parseInt(dateNow.substring(0, 4));
    int nMonth = Integer.parseInt(dateNow.substring(4, 6));
    int nDate = Integer.parseInt(dateNow.substring(6, 8));
    cal.set(nYear, nMonth - 1, nDate);
    cal.add(5, rollDate);
    String strYear = String.valueOf(cal.get(1));
    String strMonth = String.valueOf(cal.get(2) + 1);
    String strDay = String.valueOf(cal.get(5));
    strMonth = (strMonth.length() == 1) ? "0" + strMonth : strMonth;
    strDay = (strDay.length() == 1) ? "0" + strDay : strDay;
    dateReturn = strYear + strMonth + strDay;
    return dateReturn;
  }

  public static String formatFullTimeToChar14(String fullTime)
  {
    return StringUtils.replace(StringUtils.replace(StringUtils.replace(StringUtils.replace(fullTime, "-", ""), ":", ""), " ", ""), ".", "");
  }

  public static String getYearByCurrentDate()
  {
    return getChar8().substring(0, 4);
  }

  public static String getMonthByCurrentDate()
  {
    return getChar8().substring(4, 6);
  }

  public static String formatDate(String date, String formatter) {
    SimpleDateFormat myFormatter = null;
    Date da = null;
    if (date.length() < 15)
      myFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    else
      myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      da = myFormatter.parse(date);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return DateFormatUtils.format(da, formatter);
  }

  public static String formatDate(String date, String informatter, String outformatter)
  {
    Date da = null;
    try {
      SimpleDateFormat myFormatter = new SimpleDateFormat(informatter);
      da = myFormatter.parse(date);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return DateFormatUtils.format(da, outformatter);
  }

  public static final Date getDate(Object obj)
  {
    return getDate(obj, format, null);
  }

  public static final Date getDate(Object obj, Date defValue) {
    return getDate(obj, format, defValue);
  }

  public static final Date getDate(Object obj, DateFormat format) {
    return getDate(obj, format, null);
  }

  public static final Date getDate(Object obj, DateFormat format, Date defValue) {
    if (obj instanceof Date) {
      return (Date)obj;
    }

    if (obj instanceof Timestamp) {
      Timestamp timestamp = (Timestamp)obj;
      return new Date(timestamp.getTime() + timestamp.getNanos() / 1000000);
    }
    if (obj instanceof Long) {
      return new Date(((Long)obj).longValue());
    }
    if (obj instanceof String)
    {
//      Object Ljava/lang/Object;;
//      monitorenter;
      try {
        return format.parse((String)obj);
      }
      catch (Exception e) {
        return defValue;
      } finally {
//        monitorexit;
      }
    }
    return defValue;
  }

  public static String rollMonth(int rollMonth)
  {
    Calendar cal = Calendar.getInstance();
    cal.add(2, rollMonth);
    return formatToChar8(cal);
  }

  public static String getTimeToshow() {
    Calendar cal = Calendar.getInstance();
    int hour = cal.get(11);
    if (hour < hour) {
      return "凌晨好!";
    }
    if (hour < 9) {
      return "早上好!";
    }
    if (hour < 12) {
      return "上午好!";
    }
    if (hour < 14) {
      return "中午好!";
    }
    if (hour < 17) {
      return "下午好!";
    }
    if (hour < 19) {
      return "傍晚好!";
    }
    if (hour < 22) {
      return "晚上好!";
    }

    return "夜里好!";
  }

  public static final String getDateBeforeMonthLastDay()
  {
    String str = "";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar lastDate = Calendar.getInstance();
    lastDate.add(2, -1);
    lastDate.set(5, 1);

    lastDate.roll(5, -1);

    str = sdf.format(lastDate.getTime());
    return str;
  }

  public static final boolean judgeIfPastDue(String date, int daysOfPastDue)
  {
    if ((StringUtils.isEmpty(date)) || (date.length() != 8)) {
      log.error("不能判断是否过期,可能日期为空,或者日期格式错误!");
      return true;
    }
    if (daysOfPastDue < 0) {
      log.error("不能判断是否过期,过期日必须为非负数!");
      return true;
    }

    String now = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());

    String validDateLimit = (daysOfPastDue > 0) ? rollDate(now, daysOfPastDue) : now;
    try
    {
      if (Integer.parseInt(date) >= Integer.parseInt(validDateLimit))
        return false;
    }
    catch (NumberFormatException nfe)
    {
      log.error("不能判断是否过期,日期格式非法!\n" + nfe);
    }

    return true;
  }

  public static final boolean judgeIfPastDue(Date date, int daysOfPastDue)
  {
    return judgeIfPastDue(new SimpleDateFormat("yyyyMMdd").format(date), daysOfPastDue);
  }

  public static final boolean judgeIfPastDue(String date)
  {
    return judgeIfPastDue(date, 0);
  }

  public static final boolean judgeIfPastDue(Date date)
  {
    return judgeIfPastDue(date, 0);
  }
  public static Date[] getDateArrays(Date start, Date end, int calendarType) {
      ArrayList ret = new ArrayList();
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(start);
      Date tmpDate = calendar.getTime();
      long endTime = end.getTime();
      while (tmpDate.before(end) || tmpDate.getTime() == endTime) {
        ret.add(calendar.getTime());
        calendar.add(calendarType, 1);
        tmpDate = calendar.getTime();
      }
      Date[] dates = new Date[ret.size()];
      return (Date[]) ret.toArray(dates);
    }
  
  
  /**
   * 	
   * Description: 按照开始日期和结束日期 返回月份结果集
   * @Version2.0 2014-12-9 下午02:13:46 by 熊斌（xiongbin@sinontech.com）创建
   * @param beginTime 	yyyy/MM/dd
   * @param endTime		yyyy/MM/dd
   * @return 
   * List<String>
   */
    public static List<String> getDateMonthList(String beginTime,String endTime){
  	  	DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");  
  	    List<String> list = null;  
  	    try{  
  	        Calendar startDay = Calendar.getInstance();  
  	        Calendar endDay = Calendar.getInstance();  
  	        startDay.setTime(FORMATTER.parse(beginTime));  
  	        endDay.setTime(FORMATTER.parse(endTime));  
  	        startDay.set(Calendar.DAY_OF_MONTH, 1);
  	        endDay.set(Calendar.DAY_OF_MONTH, 1);
  	        list = new ArrayList<String>();
  	        int startYear = startDay.get(Calendar.YEAR);
			int startMonth = startDay.get(Calendar.MONTH) + 1;
  	        int endYear = endDay.get(Calendar.YEAR);
			int endMonth = endDay.get(Calendar.MONTH) + 1;
  	        list.add(startYear + "-" + startMonth);
  	        
  	        if(!beginTime.equals(endTime)){
  	            if(startDay.compareTo(endDay)<=0){  
  	                Calendar currentPrintDay = startDay;  
  	                while(true){  
  	                    currentPrintDay.add(Calendar.MONTH, 1);  
  	                    if (currentPrintDay.compareTo(endDay) == 0) {  
  	                        break;  
  	                    }  
  	                    int year = startDay.get(Calendar.YEAR);
  	                    int month = startDay.get(Calendar.MONTH) + 1;
  	                    list.add(year + "-" + month);  
  	                }  
  	                list.add(endYear + "-" + endMonth);  
  	            }  
  	        }  
  	        for (String customer : list)  
  	        {  
  	            System.out.println(customer);  
  	        }  
  	    }catch (Exception e){  
  	        e.printStackTrace();  
  	    }
  	    return list;
    }
}