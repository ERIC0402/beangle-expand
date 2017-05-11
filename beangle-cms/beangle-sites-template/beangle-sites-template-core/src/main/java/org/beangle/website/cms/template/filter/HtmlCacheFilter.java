package org.beangle.website.cms.template.filter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class HtmlCacheFilter implements Filter {

	private FilterConfig filterConfig;
	private String protDirPath;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		protDirPath = filterConfig.getServletContext().getRealPath("/");
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// String refererUrl = request.getRequestURL().toString();
		String servlet = request.getServletPath();
		servlet += "l";
		String queryString = request.getQueryString();
		File file = new File(protDirPath + servlet);

		// 过时删除文件
		if(file.exists()){
			//
		}

		if (!file.exists() && StringUtils.isEmpty(queryString)) {

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			String fileName = file.getAbsolutePath();

			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			HtmlResponseWrapper wrappedResponse = new HtmlResponseWrapper((HttpServletResponse) response);
			filterChain.doFilter(request, wrappedResponse);
			wrappedResponse.writeFile(fileName);
			wrappedResponse.writeResponse(out);

		} else {
			request.getRequestDispatcher(servlet).forward(request, response);
		}
	}

	public void destroy() {
	}

}
