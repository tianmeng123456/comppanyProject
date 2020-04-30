package com.boyo.wuhang.component.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class String2DateTimeConverter implements Converter<String, Date> {
	/**
	 * 前端传入日期格式如下。
	 */
	private static final String[] dateFormatString = new String[]{
			"yyyy-MM-dd HH:mm:ss.SSS",
			"yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd HH:mm",
			"yyyy-MM-dd HH",
			"yyyy-MM-dd",
			"yyyy-MM",
			"yyyy"
	};

	public Date convert(String source) {
		return parse(source, 0);
	}

	private Date parse(String source, int index) {
		if (index < dateFormatString.length) {
			DateFormat dateFormat = new SimpleDateFormat(dateFormatString[index]);
			try {
				Date date = dateFormat.parse(source);
				return date;
			} catch (ParseException e) {
				if (++index < dateFormatString.length) {
					return parse(source, index);
				}
			}
		}
		return null;
	}
}
