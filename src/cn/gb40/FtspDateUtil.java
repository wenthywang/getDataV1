package cn.gb40;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <pre>
 * FTSP平台工具。
 * </pre>
 * @author 周家鑫 zhoujiaxin@kungeek.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FtspDateUtil {
	
	private static final Log log = LogFactory.getLog(FtspDateUtil.class);
	
	/** yyyy-MM-dd HH:mm:ss */ 
	public static String FORMA_YMD_H24MS = "yyyy-MM-dd HH:mm:ss" ;
	/** yyyy-MM-dd  */ 
	public static String FORMAT_YMD = "yyyy-MM-dd" ;
	
	/** yyyyMMdd  */ 
	public static String FORMAT_YMD_TI = "yyyyMMdd" ;
	
	
	private FtspDateUtil(){}
	
	/**
	 * 获取会计期间第一天。
	 * @param kjQj
	 * @return Date
	 */
	public static Date getFirstDateOfMonth(String kjQj) {
		if(StringUtils.isEmpty(kjQj) || kjQj.length() != 6) {
			return null;
		}
		int[] time = getYearAndMonth(kjQj);
		return FtspDateUtil.getFirstDateOfMonth(time[0], time[1]);
	}
	
	/**
	 * 获取会计期间最后一天。
	 * @param kjQj
	 * @return Date
	 */
	public static Date getLastDateOfMonth(String kjQj) {
		if(StringUtils.isEmpty(kjQj) || kjQj.length() != 6) {
			return null;
		}
		int[] time = getYearAndMonth(kjQj);
		return FtspDateUtil.getLastDateOfMonth(time[0], time[1]);
	}
	
	/**
	 * 获取季度第一天。
	 * @param kjQj
	 * @return Date
	 */
	public static Date getFirstDateOfQuater(String kjQj) {
		if(StringUtils.isEmpty(kjQj) || kjQj.length() != 6) {
			return null;
		}
		int[] time = getYearAndMonth(kjQj);
		return FtspDateUtil.getFirstDateOfMonth(time[0], getFirstMonthOfQuater(time[1]));
	}
	
	/**
	 * 获取季度最后一天。
	 * @param kjQj
	 * @return Date
	 */
	public static Date getLastDateOfQuater(String kjQj) {
		if(StringUtils.isEmpty(kjQj) || kjQj.length() != 6) {
			return null;
		}
		int[] time = getYearAndMonth(kjQj);
		return FtspDateUtil.getLastDateOfMonth(time[0], getLastMonthOfQuater(time[1]));
	}
	
	/**
	 * 获取年度第一天
	 * @param kjQj
	 * @return Date
	 */
	public static Date getFirstDateOfYear(String kjQj) {
		if(StringUtils.isEmpty(kjQj) || kjQj.length() != 6) {
			return null;
		}
		int[] time = getYearAndMonth(kjQj);
		return FtspDateUtil.getFirstDateOfMonth(time[0], 1);
	}
	
	/**
	 * 获取年度最后一天
	 * @param kjQj
	 * @return Date
	 */
	public static Date getLastDateOfYear(String kjQj) {
		if(StringUtils.isEmpty(kjQj) || kjQj.length() != 6) {
			return null;
		}
		int[] time = getYearAndMonth(kjQj);
		return FtspDateUtil.getLastDateOfMonth(time[0], 12);
	}
	
	/**
	 * 获取会计期间第一天。
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getFirstDateOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1, 0, 0, 0);
		return calendar.getTime();
	}
	 
	/**
	 * 获取会计期间最后一天。
	 * @param year
	 * @param month
	 * @return Date
	 */
	public static Date getLastDateOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1, 1, 0, 0, 0);
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return calendar.getTime();
	}
	
	/**
	 * 获取月份所在季度所在第一个月
	 * @param month
	 * @return int
	 */
	public static int getFirstMonthOfQuater(int month){
		if(month > Calendar.SEPTEMBER+1) {
			return Calendar.OCTOBER+1;
		} else if(month > Calendar.JUNE+1) {
			return Calendar.JULY+1;
		} else if(month > Calendar.MARCH+1) {
			return Calendar.APRIL+1;
		} 
		return Calendar.JANUARY+1;
	}
	
	/**
	 * 获取月份所在季度所在最后一个月
	 * @param month
	 * @return int
	 */
	public static int getLastMonthOfQuater(int month){
		if(month < Calendar.APRIL+1) {
			return Calendar.MARCH+1;
		} else if(month < Calendar.JULY+1) {
			return Calendar.JUNE+1;
		} else if(month < Calendar.OCTOBER+1) {
			return Calendar.SEPTEMBER+1;
		} 
		return Calendar.DECEMBER+1;
	}
	
	/**
	 * 获取会计期间的年份和月份。
	 * @param kjQj
	 * @return int[]
	 */
	public static int[] getYearAndMonth(String kjQj) {
		int[] times = new int[2];
		times[0] = Integer.parseInt(kjQj.substring(0, 4));
		times[1] = Integer.parseInt(kjQj.substring(4));;
		return times;
	}
	
	/**
	 * 日期转换字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String parseDate(Date date, String format) {
		return date==null ? null : new SimpleDateFormat(format).format(date) ;
	}
	
	/**
	 * 日期转换字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String parseDate(Date date) {
		return parseDate(date, FORMA_YMD_H24MS) ;
	}
	/**
	 * 日期转换字符串
	 * @param date
	 * @param format
	 * @return
	 */
	public static String parseToDate(Date date) {
		return parseDate(date, FORMAT_YMD) ;
	}
	/**
	 * 字符串转换为Date类型。
	 * @param date String
	 * @return Date
	 */
	public static Date parseToDate(String date) {
		return parseToDate(date, FORMAT_YMD) ;
	}
	
	/**
	 * 字符串转换为Date类型。
	 * @param date String
	 * @return Date
	 */
	public static Date parseToDate(String date, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (date == null || date.equals("")) {
			return null ;
		}
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取当前年份的第一个会计区间
	 * @return
	 */
	public static String getFirstKjQjOfYear() {
		Calendar cal = Calendar.getInstance() ;
		String firstKjQj = cal.get(Calendar.YEAR) + "01" ;
		return firstKjQj ;
	}
	
	/**
	 * 获取当前会计区间所属年份的第一个会计区间
	 * @param kjQj
	 * @return
	 */
	public static String getFirstKjQjOfYear(String kjQj) {
		int[] yearMonth = getYearAndMonth(kjQj) ;
		String firstKjQj = yearMonth[0] + "01" ;
		return firstKjQj ;
	}
	
	/**
	 * 获取当前区间
	 * @return
	 */
	public static String getCurrentKjQj() {
		return getCurrentKjQj(null) ;
	}
	
	/**
	 * 获取当前区间
	 * @param date 日期
	 * @return
	 */
	public static String getCurrentKjQj(Date date) {
		if(date != null) {
			return parseDate(date, "yyyyMM") ;
		} else {
			return parseDate(Calendar.getInstance().getTime(), "yyyyMM") ;
		}
	}
	
	/**
	 * 根据本期查询下一季度，如本季度 201506，则下一季度 201509
	 * 下一季度所属期间
	 * @param kjQj
	 * @return
	 */
	public static String nextQuarterKjQj(String kjQj) {
		String nextKjQj = null ;
		kjQj = StringUtils.trimToNull(kjQj) ;
		if(StringUtils.isNumeric(kjQj) && kjQj.length() ==6 ){
			int year = Integer.parseInt(kjQj.substring(0, 4)) ;
			int month = Integer.parseInt(kjQj.substring(4, 6)) ;
			int nextQuarterMonth = (month + 3) % 12 ;
			if(month >= 10) {
				year++ ;
			}
			if(nextQuarterMonth == 0) {
				nextQuarterMonth = 12 ;
			}
			nextKjQj = String.valueOf(year) ;
			if(nextQuarterMonth <= 9) {
				nextKjQj = nextKjQj + "0" + nextQuarterMonth ;
			} else {
				nextKjQj = nextKjQj + nextQuarterMonth ;
			}
		}
		return nextKjQj ;
	}
	
	/**
	 * 根据本期查询上一季度，如本季度 201506，则上一季度 201503
	 * 上一季度所属期间
	 * @param kjQj
	 * @return
	 */
	public static String lastQuarterKjQj(String kjQj) {
		String lastKjQj = null ;
		kjQj = StringUtils.trimToNull(kjQj) ;
		if(StringUtils.isNumeric(kjQj) && kjQj.length() ==6 ){
			int year = Integer.parseInt(kjQj.substring(0, 4)) ;
			int month = Integer.parseInt(kjQj.substring(4, 6)) ;
			int lastQuarterMonth = (month + 12 - 3) % 12 ;
			if(month <= 3) {
				year-- ;
			}
			if(lastQuarterMonth == 0) {
				lastQuarterMonth = 12 ;
			}
			lastKjQj = String.valueOf(year) ;
			if(lastQuarterMonth <= 9) {
				lastKjQj = lastKjQj + "0" + lastQuarterMonth ;
			} else {
				lastKjQj = lastKjQj + lastQuarterMonth ;
			}
		}
		return lastKjQj ;
	}
	
	
	/**
	 * 转换为会计区间，带位移量
	 * @param year
	 * @param month
	 * @param amount
	 * @return
	 */
	public static String parseToKiQj(int year, int month, int amount) {
		int currMonth = month + amount ;
		int currYear = year ;
		if(currMonth <= 0) {
			currYear = year - 1 ;
			currMonth = currMonth + 12 ;
		} else if (currMonth >12){
			currYear = year + 1 ;
			currMonth = currMonth - 12 ;
		} 
		String kjQj = null ;
		if(currMonth <= 9) {
			kjQj = currYear + "0" + currMonth  ;
		} else {
			kjQj = currYear + "" + currMonth ;
		}
		return kjQj ;
	}
	
	/**
	 *  获取会计期间。
	 * @param kjQj 会计期间
	 * @param iCount  当值为0时，则取当期。否则从当期加或者减
	 * @return String
	 */
	public static String getKjqj(String kjQj, int iCount){
		kjQj  = StringUtils.trimToNull(kjQj);
		int iYear = 0 ;
		int iMonth = 0 ;
		if (StringUtils.isEmpty(kjQj)){
			iYear = Calendar.getInstance().get(Calendar.YEAR) ;
			iMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
		} else {
			iYear = Integer.valueOf(kjQj.substring(0, 4)) ;
			iMonth = Integer.valueOf(kjQj.substring(4, 6)) ;
		}
		kjQj = parseToKiQj(iYear, iMonth, iCount) ;
		return  kjQj ;
	}
	
	/**
	 * 获取下个期间
	 * @param kjQj
	 * @return String
	 */
	public static String nextKjQj(String kjQj) {
		return getKjqj(kjQj, 1) ;
	}
	
	/**
	 * 获取上个期间
	 * @param kjQj
	 * @return String
	 */
	public static String prevKjQj(String kjQj) {
		return getKjqj(kjQj, -1) ;
	}
	
	/**
	 * 查询本季全部所属期，如传入201501 201502 201503中任意一个，则返回第一季度，201501 201502 201503
	 * @param kjQj
	 * @return
	 */
	public static List<String> getQuarterKjQjList(String kjQj) {
		List<String> list = null ;
		kjQj = StringUtils.trimToNull(kjQj) ;
		if(StringUtils.isNumeric(kjQj) && kjQj.length()==6){
			list = new ArrayList<String>() ;
			int year = Integer.parseInt(kjQj.substring(0, 4)) ;
			int month = Integer.parseInt(kjQj.substring(4, 6)) ;
			int quarter = (month-1) / 3 + 1 ;
			String tempKjQj = null ;
			for(int i=2; i>=0; i--) {
				month = quarter * 3 - i ;
				if(month>=1 && month<=9) {
					tempKjQj = year + "0" + month ;
				} else if(month>=10 && month<=12) {
					tempKjQj = year + "" + month ;
				}
				list.add(tempKjQj) ;
			}
		}
		return list ;
	}
	
	/**
	 * 获取季度的月份组合，用于SQL查询
	 * @param kjQj
	 * @return
	 */
	public static String getQuarterKjQjForSql(String kjQj) {
		List<String> list = getQuarterKjQjList(kjQj) ;
		if(list!=null && list.size()>0) {
			StringBuffer sb = new StringBuffer() ;
			for(int i=0; i< list.size(); i++) {
				sb.append("'").append(list.get(i)).append("',") ;
			}
			return sb.substring(0, sb.length()-1) ;
		}
		return null ;
	}
	
	/**
	 * 所属期间处理。
	 * @param kjQj
	 * @param symbol
	 * @return String
	 */
	public static String formatKjQj(String kjQj, String format) {
		if(StringUtils.isEmpty(kjQj) || kjQj.length() < 4) {
			return null;
		}
		return parseDate(parseToDate(kjQj, "yyyyMM"), format) ;
	}
	
	/**
	 * 获取当前会计区间
	 * @param amount	位移量，为负数则为当前会计区间之前。
	 * @return
	 */
	public static String getCurrKiQj(int amount) {
		Calendar cal = Calendar.getInstance() ;
		int year = cal.get(Calendar.YEAR) ;
		int month = cal.get(Calendar.MONTH) + 1 ;
		return parseToKiQj(year, month, amount) ;
	}
	
	/**
	 * 比较两个会计区间相隔月份
	 * @param kjQj1
	 * @param kjQj2
	 * @return
	 */
	public static int countMonth(String kjQj1, String kjQj2) {
		int year1 = Integer.parseInt(kjQj1.substring(0, 4)) ;
		int month1 = Integer.parseInt(kjQj1.substring(4, 6)) ;
		int year2 = Integer.parseInt(kjQj2.substring(0, 4)) ;
		int month2 = Integer.parseInt(kjQj2.substring(4, 6)) ;
		int month = 12 * (year1 - year2) + month1 - month2 ;
		return month ;
	}
	
	public static void main(String[] args) {
		// 测试
		log.info("月份第一天");
		log.info(FtspDateUtil.getFirstDateOfMonth("201502"));
		log.info("月份最后一天");
		log.info(FtspDateUtil.getLastDateOfMonth("201502"));
		log.info("季度第一天");
		log.info(FtspDateUtil.getFirstDateOfQuater("201503"));
		log.info("季度最后一天");
		log.info(FtspDateUtil.getLastDateOfQuater("201503"));
		log.info("年度第一天");
		log.info(FtspDateUtil.getFirstDateOfYear("201502"));
		log.info("年度最后一天");
		log.info(FtspDateUtil.getLastDateOfYear("201502"));
		
		log.info("201412下个会计期间：" + FtspDateUtil.nextKjQj("201412"));
		log.info("201409下个会计期间：" + FtspDateUtil.nextKjQj("201409"));
		log.info("201501下个会计期间：" + FtspDateUtil.nextKjQj("201501"));
		log.info("201412上个会计期间：" + FtspDateUtil.prevKjQj("201412"));
		log.info("201410上个会计期间：" + FtspDateUtil.prevKjQj("201410"));
		log.info("201501上个会计期间：" + FtspDateUtil.prevKjQj("201501"));
		
		log.info("格式化201512==> " + FtspDateUtil.formatKjQj("201512", "yyyy-MM")) ;
		log.info("格式化201512==> " + FtspDateUtil.formatKjQj("201512", "yyyy年MM月")) ;
		System.out.println(FtspDateUtil.getCurrentKjQj());
		System.out.println(nextQuarterKjQj("201503"));
		System.out.println(lastQuarterKjQj("201506"));
		System.out.println(getQuarterKjQjForSql("201512"));
		
		String minJxFpDate = FtspDateUtil.parseDate(FtspDateUtil.parseToDate(FtspDateUtil.getCurrKiQj(-6), "yyyyMM"), "yyyy-M-d") ;
		System.out.println("进项发票最小开票日期：" + minJxFpDate) ;
		
		System.out.println("上年同期：" + FtspDateUtil.getKjqj("201501", -12)) ;
		System.out.println("会计区间相隔月份：" + FtspDateUtil.countMonth("201501", "201501")) ;
	}

}
