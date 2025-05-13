package com.hotelbookingsystem.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/customer/*", "/admin/*" })
public class AuthenticationFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Initialization code if required (for example: reading init parameters)
//		System.out.println("AuthenticationFilter initialized");
	}

	@Override
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();

		if (uri.endsWith("login.jsp") || uri.endsWith("register.jsp") || uri.contains("LoginController")) {
			chain.doFilter(request, response); // Allow access
			return;
		}

		// Check if logged in
		HttpSession session = req.getSession(false);
		boolean loggedIn = session != null && session.getAttribute("role") != null;

		String role = (String) session.getAttribute("role");

		if (uri.contains("/admin/") && !"admin".equalsIgnoreCase(role)) {
		    res.sendRedirect(req.getContextPath() + "error.jsp");
		    return;
		}

		if (uri.contains("/customer/") && !"customer".equalsIgnoreCase(role)) {
		    res.sendRedirect(req.getContextPath() + "error.jsp");
		    return;
		}

		if (!loggedIn && (uri.endsWith("login.jsp") || uri.endsWith("LogInController"))) {
			chain.doFilter(request, response);
			return;
		}
//		 Skipping filter for login page and login controller
		if (loggedIn) {
			if (uri.endsWith("login.jsp") || uri.endsWith("LogInController")) {

				res.sendRedirect(req.getContextPath() + "/admin/dashboard.jsp");
			} else {
				chain.doFilter(request, response);
				return;
			}
		} else {
			res.sendRedirect(req.getContextPath() + "/access/login.jsp");
		}

	}

}
