package com.example.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.example.component.GoogleUtils;
import com.example.model.User;
import com.example.service.CustomUserDetailsService;
import com.example.service.SecurityService;
import com.example.service.UserService;

@Service
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	UserService userService;

	@Autowired
	GoogleUtils googleUtils;

	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Override
	public boolean isAuthenticated() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
			return false;
		}
		return authentication.isAuthenticated();
	}

	@Override
	public void autoLogin(String username, String password) {
		User user = userService.findByUsername(username).get();
		userService.updateLastLoginDate(user);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			logger.debug(String.format("Auto login %s successfully!", username));
		}
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
	}

	@Override
	public String username() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetailsService userDetails = (CustomUserDetailsService) authentication.getPrincipal();
		return userDetails.getUsername();
	}

//	@Override
//	public GoogleDto loginGoogle(HttpServletRequest request, String accessToken)
//			throws ClientProtocolException, IOException {
//		GoogleDto google = googleUtils.getUserInfo(accessToken);
//		UserDetails userDetail = googleUtils.buildUser(google);
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail,
//				google.getId(), userDetail.getAuthorities());
//		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		if (userService.existsByEmail(google.getEmail())) {
//			User user = userService.findByEmail(google.getEmail()).get();
//			userService.updateLastLoginDate(user);
//			return null;
//		}
//		return google;
//	}
}