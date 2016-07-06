package com.jzwgj.management.http.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jzwgj.management.constant.SessionConstant;

public class LoginFifter implements Filter{
	
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		String servletPath = request.getServletPath();
		
		if (servletPath.indexOf("index.htm")==-1 && servletPath.indexOf("login.jsp")==-1 && servletPath.indexOf("login.htm")==-1 
													&& servletPath.indexOf("/admin/") != -1 && !servletPath.startsWith("/resource/")
															&& null == request.getSession().getAttribute(SessionConstant.SESSION_USER)) {
			
			HttpServletResponse response = (HttpServletResponse)servletResponse;
			response.sendRedirect(request.getContextPath()+"/admin/index/index.htm");
			return ;
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
