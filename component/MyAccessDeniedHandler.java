package com.example.component;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.example.service.SecurityService;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {

	@Autowired
	SecurityService securityService;

	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AccessDeniedException e) throws IOException, ServletException {
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// CustomUserDetailsService userDetails = (CustomUserDetailsService)
		// auth.getPrincipal();
		// System.out.println(userDetails.getPassword() + userDetails.getUsername()+
		// userDetails.getAuthorities());
		securityService.logout(httpServletRequest, httpServletResponse);
		httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "error/admin/403");
	}
}