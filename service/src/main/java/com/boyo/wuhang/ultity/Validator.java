package com.boyo.wuhang.ultity;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 *
 * 文件名称：Validator.java
 * 创建时间：2018-06-06
 * 描述：提供项目所有校验工作
 */

public class Validator {

	/**
	 * 日志系统信息
	 */
	private static final Logger logger = LoggerFactory.getLogger(Validator.class);

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^((13[0-9])|(14[0-9])|(15[^4,\\D])|(16[0-9])|(17[0-9])|(19[0-9])|(18[0-9]))\\d{8}$";


	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[a-zA-Z0-9]{8,16}$";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}]{6,16}$";

	/**
	 * 正则表达式:验证日期
	 */
	public static final String REGEX_DATE = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))$";

	/**
	 * 正则表达式:车牌号
	 */
	public static final String REGEX_CARNUMBER = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";

	/**
	 * 正则表达式:图片
	 */
	public static final String REGEX_PICTURE = ".+(.JPEG|.jpeg|.JPG|.jpg|.GIF|.gif|.BMP|.bmp|.PNG|.png)$";

	/**
	 * 正则表达式:数字
	 */
	public static final String  REGEX_NUMBER = "^[0-9]*$";

	/**
	 * 校验 手机号
	 *
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		if (StringUtils.isBlank(mobile)) {return false;}
		return Pattern.matches(REGEX_MOBILE, mobile.trim());
	}
	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isEmail(String email) {
		return Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验用户名
	 * 
	 * @param userName
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isUserName(String userName) {
		return Pattern.matches(REGEX_USERNAME, userName);
	}

	/**
	 * 校验密码 --- MD5加密值
	 *
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassowrd(String password) {
		if (StringUtils.isBlank(password)) {return false;}
		password = password.toLowerCase().trim();//小写字母
		return password.length()==32?true:false;
		//return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验日期
	 * 
	 * @param date
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isDate(String date) {
		return Pattern.matches(REGEX_DATE, date);
	}

	/**
	 * 校验车牌号
	 * @param carNumber
	 * @return
	 */
	public static boolean isCarNumber(String carNumber){
		return Pattern.matches(REGEX_CARNUMBER,carNumber);
	}


	/**
	 * 校验数字
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number){
		return Pattern.matches(REGEX_NUMBER, number);
	}

	/**
	 * 校验图片
	 * @param picture
	 * @return
	 */
	public static boolean isPicture(String picture){
		return Pattern.matches(REGEX_PICTURE,picture);
	}
	/**
	 * 前端传入日期格式如下。
	 */
	private static final String[] dateFormatString = new String[]{
			"yyyy-MM-dd hh:mm:ss.SSS",
			"yyyy-MM-dd hh:mm:ss",
			"yyyy-MM-dd hh:mm",
			"yyyy-MM-dd hh",
			"yyyy-MM-dd",
			"yyyy-MM",
			"yyyy"
	};

	public static Date parse(String source, int index){
		if(index < dateFormatString.length){
			DateFormat dateFormat = new SimpleDateFormat(dateFormatString[index]);
			try{
				Date date = dateFormat.parse(source);
				logger.info("日期格式转换，匹配到["+dateFormatString[index]+"]");
				return date;
			}catch (ParseException e){
				if(++index <dateFormatString.length){
					return parse(source,index);
				}
			}
		}
		return null;
	}

	/**
	 *   获取秒级时间戳 --- 是指格林威治时间1970年01月01日00时00分00秒(北京时间1970年01月01日08时00分00秒)起至现在的总秒数
	 *      时间区域 --- 服务器系统时间的时间戳
	 */
	private static Long getSecondTimestamp(Date date) {

		if (date == null) {
			return 0L;
		} else {
			return Long.valueOf(date.getTime()/1000);
		}
	}

	public static String getStringByInteger(Integer id, Integer length) {
		if (id == null || id < 0) { id = 1; }
		if (length == null || length < 0) { length = 1; }

		//得到一个NumberFormat的实例
		NumberFormat nf = NumberFormat.getInstance();
		//设置是否使用分组
		nf.setGroupingUsed(false);
		//设置最大整数位数
		//nf.setMaximumIntegerDigits(4);
		//设置最小整数位数
		nf.setMinimumIntegerDigits(length);
		//输出测试语句
		return nf.format(id);
	}

	/**
	 * 序列编号
	 *  前缀 + 日期 + id
	 * @return
	 */
	public static String getNumber(String prefix, Long id, Integer length) {
		String result = prefix;
		DateFormat dateFormat = new SimpleDateFormat("MMdd");
		result = result + dateFormat.format(Calendar.getInstance().getTime());
		/* 补齐0 相同长度 */
		if (id <= 0) { id = 1L; }
		if (length < 1) {length = 6; }
		for (Integer i = 0; i < length - String.valueOf(id).length(); i++){
			result = result + "0";
		}
		result = result + String.valueOf(id);

		return result;
	}

	/**
	 * 方法名 getStringListByStrings
	 * @param strings
	 * 描述: 此方法主要进行查询 逗号集合 转 字符串列表。
	 */
	public static List<String> getStringListByStrings(String strings){
		List<String> result = new ArrayList<String>();

		if (StringUtils.isNotBlank(strings)) {
			strings = strings.trim();
			String[] tempList = strings.split(",");
			for(int i = 0; i < tempList.length; i++) {
				result.add(String.valueOf(tempList[i]));
			}
		}
		return result;
	}

	/**
	 * 方法名 getStringListByStrings
	 * @param strings
	 * 描述: 此方法主要进行查询 逗号集合 转 整数列表。
	 */
	public static List<Long> getLongListByStrings(String strings){
		List<Long> result = new ArrayList<Long>();

		List<String> tempList = getStringListByStrings(strings);
		if (tempList != null && tempList.size() > 0) {
			for (int i=0; i<tempList.size(); i++) {
				result.add(Long.valueOf(tempList.get(i)));
			}
		}
		return result;
	}

	/* 获取 hq_account_amount.number 生成规则 */
	public static String getAccountAmountNumber(){
		String result = new SimpleDateFormat("yyyyMMddhhmmssSSSS").format(new Date());
		return result;
	}

	/* 获取 hq_account_amount.flowno 生成规则 */
	public static String getAccountAmountFlowno(){
		String result = "804343" + new SimpleDateFormat("yyMMdd").format(new Date());
		result = result +"614"+ getRandomInteger(7);
		return result;
	}

	/**
	 * 方法名称：getRandomString
	 * @param length
	 * @return
	 * 描述：获取随机字符串
	 */
	public static String getRandomString(int length) { //length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 方法名称：getRandomInteger
	 * @param length
	 * @return
	 * 描述：获取随机字符串
	 */
	public static String getRandomInteger(int length) { //length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(String.format("%014d", 567));
		System.out.println(String.format("%0"+ String.valueOf(7) +"d", 567));
		System.out.println(String.format("%04d", 567));
		//待测试数据
		int i = 21;
		//得到一个NumberFormat的实例
		NumberFormat nf = NumberFormat.getInstance();
		//设置是否使用分组
		nf.setGroupingUsed(false);
		//设置最大整数位数
		//nf.setMaximumIntegerDigits(4);
		//设置最小整数位数
		nf.setMinimumIntegerDigits(4);
		//输出测试语句
		System.out.println(nf.format(i));

		String password = "   123456 ";
		System.out.println(isPassowrd(password));
		System.out.println(isUserName(password));

		System.out.println(getNumber("U", 31L, 6));

		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(dateFormat.format(calendar.getTime()));
		System.out.println(calendar.getTimeInMillis());
		System.out.println((long)(calendar.getTimeInMillis()/1000));
		System.out.println(Long.valueOf(calendar.getTime().getTime()/1000));

		//System.out.println(Validator.isUserName("11111aaaaaa"));
	}
}
