package cn.gzccc.util;

import java.text.*;
import java.util.*;

public class DateUtil {
	private static SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat unformatDate = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat unformatTime = new SimpleDateFormat("HHmmss");
	private static String flowDate = "00000000000000";
	private static String actionDate = "00000000000000";
	private static int flowSequ = 0;
	private static int actionSequ = 0;

	/**
	 * 取得当前日期
	 * yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		Date d = new Date();
		return formatDate.format(d);
	}
	
	/**
	 * 取得当前日期
	 * yyyyMMdd
	 */
	public static String getUFCurrentDate() {
		Date d = new Date();
		return unformatDate.format(d);
	}
	
	/**
	 * 取得当前时间
	 * HH:mm:ss
	 */
	public static String getCurrentTime() {
		Date d = new Date();
		return formatTime.format(d);
	}
	
	/**
	 * 取得当前时间
	 * HHmmss
	 */
	public static String getUFCurrentTime() {
		Date d = new Date();
		return unformatTime.format(d);
	}
	
	/**
	 * time format<br>
	 * HHmmss change to HH:mm:ss
	 */
	public static String getFormatTime(String fTimeStr){
		try {
			if(fTimeStr.equals("")||fTimeStr==null)return "";
			String h = fTimeStr.substring(0, 2);
			String m = fTimeStr.substring(2, 4);
			String s = fTimeStr.substring(4, 6);
			return h + ":" + m + ":" + s;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * date unformat<br>
	 * yyyy-mm-dd change to yyyymmdd
	 */
	public static String getUnFormatDate(String fDateStr){
		try {
			if(fDateStr.equals("")||fDateStr==null)return "";
			String year = fDateStr.substring(0, 4);
			String month = fDateStr.substring(5, 7);
			String day = fDateStr.substring(8, 10);
			return year + month + day;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * date format<br>
	 * yyyymmdd change to yyyy-mm-dd
	 * @param fDateStr
	 * @return
	 * @throws Exception
	 */
	public static String getFormatDate(String fDateStr){
		try {
			if(fDateStr.equals("")||fDateStr==null)return "";
			String year = fDateStr.substring(0, 4);
			String month = fDateStr.substring(4, 6);
			String day = fDateStr.substring(6, 8);
			return year + "-" + month + "-" + day;
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 获取两个日期之间的天<br>
	 * 格式为yyyyMMdd
	 * @param beginDate 
	 * @param endDate
	 */
	public static int getPeriodDays(String beginDate, String endDate) throws Exception {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(formatDate.parse(beginDate));
		calendar2.setTime(formatDate.parse(endDate));
		return (int) ((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) / 1000 / 60 / 60 / 24);
	}
	
	/**
	 * 取得格式日期的月
	 */
	public String getMonthByFormatDate(String dateStr){
		return dateStr.substring(5, 7);
	}
	
	/**
	 * 某年某月的最大天
	 */
	public static int getMaxDays(String year,String month){
		int m=Integer.parseInt(month);
		int y=Integer.parseInt(year);
		int day=0;
		 switch(m){
         	case 1:
         	case 3:
         	case 5:
         	case 7:
         	case 8:
         	case 10:
         	case 12:
         		day=31;
         		break;
         	case 2:
         		day=((y%4)==0)?29:28;
         		break;
         	case 4:
         	case 6:
         	case 9:
         	case 11:
         		day=30;
         		break;
         }
		 return day;
	}
	
	/**
	 * 获取当前时间戳<br>
	 * @param formatType
	 * 0-->yyyyMMddHHmmssxxx<br>
	 * 1-->yyyy-MM-dd HH:mm:ss xxx<br>
	 * 2-->yyyy-MM-dd HH:mm:ss<br>
	 * 3-->yyyy-MM-dd<br>
	 * 4-->yyyy-M-d<br>
	 * 5-->yyyy年MM月dd日<br>
	 * 6-->yyyy年M月d日<br>
	 */
	public static String fullDateTime(int formatType){
		Calendar dt = Calendar.getInstance();
		String year,month,day,hour,minute,second,millis;
		year   = String.valueOf(dt.get(Calendar.YEAR));
		month  = String.valueOf(dt.get(Calendar.MONTH)+1);
		day    = String.valueOf(dt.get(Calendar.DAY_OF_MONTH));
		hour   = String.valueOf(dt.get(Calendar.HOUR_OF_DAY));
		minute = String.valueOf(dt.get(Calendar.MINUTE));
		second = String.valueOf(dt.get(Calendar.SECOND));
		millis = String.valueOf(dt.get(Calendar.MILLISECOND));
		if(month.length()==1) month="0"+month;
		if(day.length()==1) day="0"+day;
		if(hour.length()==1) hour="0"+hour;
		if(minute.length()==1) minute="0"+minute;
		if(second.length()==1) second="0"+second;
		if(millis.length()==1)millis="00"+millis; else if(millis.length()==2)millis="0"+millis;
		if(formatType==1){
			return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+" "+millis;
		}else if(formatType==2){
			return year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
		}else if(formatType==3){
			return year+"-"+month+"-"+day;
		}else if(formatType==4){
			return year+"-"+((month.length()>1&&month.charAt(0)=='0')?month.charAt(1):month)+"-"+((day.length()>1&&day.charAt(0)=='0')?day.charAt(1):day);
		}else if(formatType==5){
			return year+"年"+month+"月"+day+"日";
		}else if(formatType==6){
			return year+"年"+((month.length()>1&&month.charAt(0)=='0')?month.charAt(1):month)+"月"+((day.length()>1&&day.charAt(0)=='0')?day.charAt(1):day)+"日";
		}else{
			return year+month+day+hour+minute+second+millis;
		}
	}
	
	/**
	 * 获取日期所在周的日期集<br>
	 * @param yyyyMMdd<br>
	 * @return datesInWeek<br>
	 */
	public static String[] datesInWeek(String xDate){
		String[] datesInWeek = new String[7];
		Calendar calendar = Calendar.getInstance();
		Date d = new Date();
		int dayOfWeek=0;
		try{
			calendar.setTime(unformatDate.parse(xDate));
			d.setTime(calendar.getTimeInMillis());
			dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);//一周里的第几天,星期日为1,星期六为7
			dayOfWeek = dayOfWeek - 1;//转换成星期几,星期日为0,星期六为6
		}catch(Exception e){
			return null;
		}
		for(int i=0; i<7; i++){
			d.setTime(calendar.getTimeInMillis()+(i-dayOfWeek)*(1000*60*60*24));
			datesInWeek[i] = formatDate.format(d);
		}
		return datesInWeek;
	}
	
	/**
	 * 获取日期对应的星期几<br>
	 * @param yyyyMMdd<br>
	 * @param type EN CN NUM<br>
	 * @return dayOfWeek<br>
	 */
	public static String dayOfWeek(String xDate, String type){
		String[] enWeeks = "Sun,Mon,Tues,Wed,Thurs,Fri,Sat".split(",");
		String[] cnWeeks = "星期日,星期一,星期二,星期三,星期四,星期五,星期六".split(",");
		Calendar calendar = Calendar.getInstance();
		try{calendar.setTime(unformatDate.parse(xDate));}catch(ParseException e){return null;}
		int i=0;
		i = calendar.get(Calendar.DAY_OF_WEEK);//一周里的第几天,星期日为1,星期六为7
		i--;//转换成星期几,星期日为0,星期六为6
		if("EN".equals(type)) return enWeeks[i];
		else if("CN".equals(type)) return cnWeeks[i];
		else return String.valueOf(i);
	}
	
	/**
	 * 获取日期、星期<br>
	 * @param
	 * @return
	 */
	public static String getFullCNDate(){
		Date d = new Date();
		DateFormat fmt = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA);
		return fmt.format(d);
	}
	
	/**
	 * 获取当前流水号<br>
	 * @return yyyyMMddHHmmssiiii<br>
	 */
	synchronized public static String getFlowNo(){
		Calendar dt = Calendar.getInstance();
		String year,month,day,hour,minute,second;
		String full,sequ;
		year   = String.valueOf(dt.get(Calendar.YEAR));
		month  = String.valueOf(dt.get(Calendar.MONTH)+1);
		day    = String.valueOf(dt.get(Calendar.DAY_OF_MONTH));
		hour   = String.valueOf(dt.get(Calendar.HOUR_OF_DAY));
		minute = String.valueOf(dt.get(Calendar.MINUTE));
		second = String.valueOf(dt.get(Calendar.SECOND));
		if(month.length()==1) month="0"+month;
		if(day.length()==1) day="0"+day;
		if(hour.length()==1) hour="0"+hour;
		if(minute.length()==1) minute="0"+minute;
		if(second.length()==1) second="0"+second;
		full = year+month+day+hour+minute+second;
		if(full.compareTo(flowDate)>0){
			flowDate = full;
			flowSequ = 1;
		}else if(flowSequ<9999){
			flowSequ++;
		}
		if(flowSequ<10) sequ="000"+String.valueOf(flowSequ);
		else if(flowSequ<100) sequ="00"+String.valueOf(flowSequ);
		else if(flowSequ<1000) sequ="0"+String.valueOf(flowSequ);
		else sequ=String.valueOf(flowSequ);
		return full+sequ;
	}
	
	/**
	 * 获取当前流水号<br>
	 * @return yyyyMMddHHmmssiiii<br>
	 */
	synchronized public static String getActionNo(){
		Calendar dt = Calendar.getInstance();
		String year,month,day,hour,minute,second;
		String full,sequ;
		year   = String.valueOf(dt.get(Calendar.YEAR));
		month  = String.valueOf(dt.get(Calendar.MONTH)+1);
		day    = String.valueOf(dt.get(Calendar.DAY_OF_MONTH));
		hour   = String.valueOf(dt.get(Calendar.HOUR_OF_DAY));
		minute = String.valueOf(dt.get(Calendar.MINUTE));
		second = String.valueOf(dt.get(Calendar.SECOND));
		if(month.length()==1) month="0"+month;
		if(day.length()==1) day="0"+day;
		if(hour.length()==1) hour="0"+hour;
		if(minute.length()==1) minute="0"+minute;
		if(second.length()==1) second="0"+second;
		full = year+month+day+hour+minute+second;
		if(full.compareTo(actionDate)>0){
			actionDate = full;
			actionSequ = 1;
		}else if(actionSequ<9999){
			actionSequ++;
		}
		if(actionSequ<10) sequ="000"+String.valueOf(actionSequ);
		else if(actionSequ<100) sequ="00"+String.valueOf(actionSequ);
		else if(actionSequ<1000) sequ="0"+String.valueOf(actionSequ);
		else sequ=String.valueOf(actionSequ);
		return full+sequ;
	}
}
