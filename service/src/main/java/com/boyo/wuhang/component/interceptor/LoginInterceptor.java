package com.boyo.wuhang.component.interceptor;

import com.boyo.wuhang.entity.base.BaseUser;
import com.boyo.wuhang.service.RedisService;
import com.boyo.wuhang.ultity.JSONTool;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
	/**
	 * Token
	 */
	private static final ThreadLocal<String> token = new ThreadLocal<>();
	/**
	 * 所属系统
	 */
	public static final ThreadLocal<String> system = new ThreadLocal<>();
	/**
	 * 保存这次请求的用户
	 */
	public static final ThreadLocal<BaseUser> user = new ThreadLocal<>();


	@Autowired
	private RedisService redisService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String tokenCode = request.getHeader("Token");
		String client = request.getHeader("Client");
		if (client == null) {
			request.getRequestDispatcher("/sso/no_login").forward(request, response);
			return false;
		}
		if (!redisService.exists(client)) {
			request.getRequestDispatcher("/sso/no_login").forward(request, response);
			return false;
		}
		if (redisService.get(client) == null) {
			request.getRequestDispatcher("/sso/no_login").forward(request, response);
			return false;
		}
		JSONObject login = JSONObject.fromObject(redisService.get(client));
		if (login == null || login.get("user") == null) {
			request.getRequestDispatcher("/sso/no_login").forward(request, response);
			return false;
		}
		if (!("true").equals(login.getString("flag"))) {
			request.getRequestDispatcher("/sso/no_login").forward(request, response);
			return false;
		}
		BaseUser t_user = JSONTool.getObject(login.get("user").toString(),BaseUser.class);
		if (t_user != null) {
			user.set(t_user);
		}
		system.set(login.getString("system"));
		token.set(tokenCode);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		user.remove();
		system.remove();
		token.remove();
	}
}
