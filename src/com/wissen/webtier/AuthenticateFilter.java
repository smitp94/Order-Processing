package com.wissen.webtier;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;


@WebFilter(filterName="AuthenticateFilter")
public class AuthenticateFilter implements Filter {

   
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		/*String email=request.getParameter("email");
		if(email.contains("wissen")){
		 chain.doFilter(request, response);
		}else{
			HttpServletResponse resp=(HttpServletResponse)response;
			resp.sendError(HttpServletResponse.SC_NOT_FOUND,"Un-Authorized User");
		}*/
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
