package com.szzaq.jointdefence.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ����ʱ�乤����
 * 
 * @author Sean
 * 
 */

public class DateUtils {
	private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
			"yyyy��MM��dd�� HH:mm");
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy��MM��dd��");
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
			"HH:mm");
	private static final SimpleDateFormat timeFormat2 = new SimpleDateFormat(
			"hh:mm");

	/**
	 * ��õ�ǰ����ʱ��
	 * <p>
	 * ����ʱ���ʽyyyy��MM��dd�� HH:mm:ss
	 * 
	 * @return
	 */
	public static String currentDatetime() {
		return datetimeFormat.format(now());
	}

	/**
	 * ��ʽ������ʱ��
	 * <p>
	 * ����ʱ���ʽyyyy��MM��dd�� HH:mm:ss
	 * 
	 * @return
	 */
	public static String formatDatetime(Date date) {
		return datetimeFormat.format(date);
	}

	/**
	 * ��ʽ������ʱ��
	 * 
	 * @param date
	 * @param pattern ��ʽ��ģʽ
	 * @return
	 */
	public static String formatDatetime(Date date, String pattern) {
		SimpleDateFormat customFormat = (SimpleDateFormat) datetimeFormat
				.clone();
		customFormat.applyPattern(pattern);
		return customFormat.format(date);
	}

	/**
	 * ��õ�ǰ����
	 * <p>
	 * ���ڸ�ʽyyyy��MM��dd��
	 * 
	 * @return
	 */
	public static String currentDate() {
		return dateFormat.format(now());
	}

	/**
	 * ��ʽ������
	 * <p>
	 * ���ڸ�ʽyyyy��MM��dd��
	 * 
	 * @return
	 */
	public static String formatDate(Date date) {
		return dateFormat.format(date);
	}

	/**
	 * ��õ�ǰʱ��
	 * <p>
	 * ʱ���ʽHH:mm:ss
	 * 
	 * @return
	 */
	public static String currentTime() {
		return timeFormat.format(now());
	}

	/**
	 * ��ʽ��ʱ��
	 * <p>
	 * ʱ���ʽ HH:mm:ss
	 * @return
	 */
	public static String formatTime(Date date) {
		return timeFormat.format(date);
	}
	/**
	 * ��ʽ��ʱ��
	 * <p>
	 * ʱ���ʽ hh:mm:ss
	 * @return
	 */
	public static String formatTime2(Date date) {
		return timeFormat2.format(date);
	}

	/**
	 * ��õ�ǰʱ���Date����
	 * @return
	 */
	public static Date now() {
		return new Date();
	}

	/**
	 * ��õ�ǰʱ��ĺ�����
	 * @return 
	 */
	public static long millis() {
		return System.currentTimeMillis();
	}

	/**
	 * ���ַ�����ʱ��ת����
	 * ����ʱ���ʽyyyy��MM��dd�� HH:mm:ss
	 * @param datetime
	 * @return
	 */
	public static Date parseDatetime(String datetime) throws ParseException {
		return datetimeFormat.parse(datetime);
	}
	
	public static Date paserDate(long milliseconds){
		return new Date(milliseconds);
	}

    /**
     * ����Ŀ�ʼʱ��
     * @return
     */
    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date=calendar.getTime();
        return date.getTime();
    }
    /**
     * ��������翪ʼʱ��
     * @return
     */
    public static long startOfTodDayMid() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR_OF_DAY, 12);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 0);
    	Date date=calendar.getTime();
    	return date.getTime();
    }

    /**
     * ����Ŀ�ʼʱ��
     * @return
     */
    public static long startOfyesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date=calendar.getTime();
        return date.getTime();
    }
    /**
     * ����Ľ���ʱ��
     * @return
     */
    public static long endOfYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.add(Calendar.DATE, -1);
        Date date=calendar.getTime();
        return date.getTime();
    }
    
    /**
     * �������ĺ���ֵ�ж�ʱ��
     * @param millis ������
     * @return 
     */
    public static String parserTime(long millis){
		String result;
		if (millis < startOfTodDay() && millis > startOfyesterday()) {
			//�ж�ʱ���Ƿ������췶Χ��
			result = "���� "+formatTime(paserDate(millis));
		}else if (millis > startOfTodDay() && millis <= millis()) {
			//�ж�ʱ���Ƿ��ڽ���
			if (millis > startOfTodDayMid()) {
				result = "���� "+formatTime2( paserDate(millis));
			}else {
				result = "���� "+ formatTime2(paserDate(millis));
			}
		}else if (millis < startOfyesterday()) {
			//������֮ǰ
			result = formatDatetime(paserDate(millis));
		}else {
			//���ڵ�ǰʱ��
			result = formatDatetime(paserDate(millis));
			//result = "ʱ���ڽ���";
		}
		return result;
    }
}
