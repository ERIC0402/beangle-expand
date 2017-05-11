/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.beangle.website.cms.template.util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.SecurityUtils;

/**
 *
 * @author CHII
 */
public class UserFilter implements Filter {
	
	private String loginUrl = "/login.action";
	private String _contextPath;


	public void init(FilterConfig fc) throws ServletException {
		String loginUrl = fc.getInitParameter("loginUrl");
		if(StringUtils.isNotEmpty(loginUrl)){
			this.loginUrl = loginUrl;
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain fc) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		appendContextPath(request);
		Long userid = null;
		try {
			userid = SecurityUtils.getUserId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(userid != null){
			String uri = request.getRequestURI();
			uri = uri.replace("/user", "");
			request.getRequestDispatcher(uri).forward(request, response);
		}else{
			String backurl = request.getParameter("backurl");
			if(StringUtils.isEmpty(backurl)){
				backurl = request.getRequestURL().toString();
				backurl = URLEncoder.encode(backurl, "UTF-8");
			}
			response.sendRedirect(loginUrl + "?backurl=" + backurl);
			return;
		}
	}

	public void destroy() {
	}
	
	private void appendContextPath(HttpServletRequest request){
			String contextPath = request.getContextPath();
			if(contextPath.length() > 1 && !loginUrl.startsWith(contextPath)){
				loginUrl = contextPath + loginUrl;
			}
	}

}
