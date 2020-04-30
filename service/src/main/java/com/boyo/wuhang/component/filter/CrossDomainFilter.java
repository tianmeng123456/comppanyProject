package com.boyo.wuhang.component.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*", filterName = "CrossDomainFilter")
@Order(1)//指定过滤器的执行顺序,值越大越靠后执行
public class CrossDomainFilter implements Filter {

	@Value("${spring.profiles.active}")
	private String active;

	@Value("${Access.Control.Allow.Origin}")
	private String allowOrigin;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		System.out.println("当前环境配置(跨域): " + active); //OPTIONS预检请求 active=null
		String uri = request.getRequestURI();
		String requestMethod = request.getMethod();

		if ("product".equals(active)) {
			//生产环境 指定ip+端口 可以跨域，其他不行
			System.out.println("允许的跨域请求: " + allowOrigin);
			String tempUrl = request.getRequestURI();
			if (tempUrl != null && tempUrl.equals(allowOrigin)) {
				httpResponse.setHeader("Access-Control-Max-Age", "86400");
				httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Token, Client ,Content-Disposition,Content-Length");
				httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
				httpResponse.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));//允许跨域
			}
		} else {
			//开发环境 本机 + 公司服务器 --- 支持跨域
			httpResponse.setHeader("Access-Control-Max-Age", "86400");
			httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Token, Client, Content-Disposition,Content-Length");
			httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
			httpResponse.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));//允许跨域
			if ("localhost".equals(active)) {

			}
			if ("developer".equals(active)) {

			}
		}

		filterChain.doFilter(servletRequest, httpResponse); //指针 共同地址空间，修改数据
	}

	@Override
	public void destroy() {
	}
}
