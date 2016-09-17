package com.cpkf.notpad.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

import com.cpkf.notpad.commons.constants.DateFormatConstants;
/**  
 * Filename:    DateUtil.java
 * Description: 时间工具类
 * Company:     
 * @author:     
 * @version:    1.0
 * Create at:   2011-5-7 下午10:51:01
 * modified:    
 */
public class DateUtil {
	public static String dateFormat(Date date,String pattern){
		if(StringUtils.isBlank(pattern)){
			pattern = DateFormatConstants.DAY_MONTH_YEAR;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	public static void main(String[] args) {
		System.out.println(DateUtil.dateFormat(new Date(), DateFormatConstants.DAY_MONTH_YEAR_HOUR_MIN_SEC));
	}
}
