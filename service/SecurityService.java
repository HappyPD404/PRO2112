package com.example.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SecurityService {

	String username();

	boolean isAuthenticated();

	void autoLogin(String username, String password);

	void logout(HttpServletRequest request, HttpServletResponse response);

	//GoogleDto loginGoogle(HttpServletRequest request, String accessToken) throws ClientProtocolException, IOException;
}