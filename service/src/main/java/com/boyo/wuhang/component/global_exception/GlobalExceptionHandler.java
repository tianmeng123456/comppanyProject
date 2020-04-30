package com.boyo.wuhang.component.global_exception;

import com.boyo.wuhang.ultity.JsonBuilder;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
	private static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	//运行时异常
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JSONObject runtimeExceptionHandler(RuntimeException ex) {
		ex.printStackTrace();
		String msg = ex.getMessage();
		if (msg.toLowerCase().contains("reference 约束") && msg.toLowerCase().contains("fk")) {
			log.error("记录已被引用", ex);
			return JsonBuilder.build(1, "记录已被引用", null);
		}
		log.error("运行时异常", ex);
		return JsonBuilder.build(1, "运行时异常", ex.getMessage());
	}

	//空指针异常
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public String nullPointerExceptionHandler(NullPointerException ex) {
		log.error("空指针异常", ex);
		System.err.println("NullPointerException:");
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "空指针异常");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//类型转换异常
	@ExceptionHandler(ClassCastException.class)
	@ResponseBody
	public String classCastExceptionHandler(ClassCastException ex) {
		log.error("类型转换异常", ex);
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "类型转换异常");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//IO异常
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public String iOExceptionHandler(IOException ex) {
		log.error("IO异常", ex);
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "IO异常");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//未知方法异常
	@ExceptionHandler(NoSuchMethodException.class)
	@ResponseBody
	public String noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		log.error("未知方法异常", ex);
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "未知方法异常");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseBody
	public String indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		log.error("数组越界异常", ex);
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "数组越界异常");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//400错误
	@ExceptionHandler({HttpMessageNotReadableException.class})
	@ResponseBody
	public String requestNotReadable(HttpMessageNotReadableException ex) {
		log.error("Http消息不可读", ex);
		System.out.println("400..requestNotReadable");
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "Http消息不可读");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//400错误
	@ExceptionHandler({TypeMismatchException.class})
	@ResponseBody
	public String requestTypeMismatch(TypeMismatchException ex) {
		log.error("类型不匹配", ex);
		System.out.println("400..TypeMismatchException");
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "类型不匹配");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//400错误
	@ExceptionHandler({MissingServletRequestParameterException.class})
	@ResponseBody
	public String requestMissingServletRequest(MissingServletRequestParameterException ex) {
		log.error("缺少Servlet请求参数", ex);
		System.out.println("400..MissingServletRequest");
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "缺少Servlet请求参数");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//404错误
	@ExceptionHandler({NoHandlerFoundException.class})
	@ResponseBody
	public String requestNoHandlerFoundExceptionRequest(NoHandlerFoundException ex) {
		log.error("url not found", ex);
		System.out.println("404 url not found");
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "404 url not found");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//405错误
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	@ResponseBody
	public String request405(HttpRequestMethodNotSupportedException ex) {
		log.error("不支持Http请求方法", ex);
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "不支持Http请求方法");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//406错误
	@ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
	@ResponseBody
	public String request406(HttpMediaTypeNotAcceptableException ex) {
		log.error("Http媒体类型不可接受", ex);
		System.out.println("406...");
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "Http媒体类型不可接受");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//500错误
	@ExceptionHandler({ConversionNotSupportedException.class, HttpMessageNotWritableException.class})
	@ResponseBody
	public String server500(RuntimeException ex) {
		log.error("500", ex);
		System.out.println("500...");
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "ConversionNotSupported");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//栈溢出
	@ExceptionHandler({StackOverflowError.class})
	@ResponseBody
	public String requestStackOverflow(StackOverflowError ex) {
		log.error("栈溢出", ex);
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "栈溢出");
		r.put("detail", ex.getMessage());
		return r.toString();
	}

	//除数不能为0
	@ExceptionHandler({ArithmeticException.class})
	@ResponseBody
	public String arithmeticException(ArithmeticException ex) {
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("msg", "除数不能为0");
		r.put("detail", ex.getMessage());
		return r.toString();
	}


	//其他错误
	@ExceptionHandler({Exception.class})
	@ResponseBody
	public JSONObject exception(Exception ex) {
		log.error("未知错误", ex);
		ex.printStackTrace();
		Map<String, Object> r = new HashMap<>();
		r.put("detail", ex.getMessage());
		return JsonBuilder.build(1, "未知错误", r);
	}

}
