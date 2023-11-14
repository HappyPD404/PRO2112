package com.poly;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.poly.entity.Account;
import com.poly.service.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	AccountService accountService;

	@Autowired
	BCryptPasswordEncoder pe;

	@Autowired
	HttpSession session;

	/* Cơ chế mã hóa mật khẩu */
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/* Cho phép truy xuất REST API từ domain khác */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

	/* Quản lý dữ liệu người sử dụng */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(username -> {
			try {
				Account user = accountService.findById(username);
				String password = pe.encode(user.getPassword()); // Mã hóa mật khấu
				String[] roles = user.getAuthorities().stream().map(er -> er.getRole().getId())
						.collect(Collectors.toList()).toArray(new String[0]);
				Map<String, Object> authentication = new HashMap<>();
				authentication.put("user", user);
				byte[] token = (username + ":" + user.getPassword()).getBytes();
				authentication.put("token", "Basic " + Base64.getEncoder().encodeToString(token));
				session.setAttribute("authentication", authentication);
				return User.withUsername(username).password(password).roles(roles).build();
			} catch (NoSuchElementException e) {
				throw new UsernameNotFoundException(username + " not found!");
			}
		});
	}

	/* Phân quyền sử dụng */
	protected void configure(HttpSecurity http) throws Exception {
		// Tắt thuật tấn công giả mạo
		http.csrf().disable();
		// Quyền yêu cầu truy cập
		http.authorizeRequests().antMatchers("/order/**", "/auth/change-password").authenticated()
				.antMatchers("/admin/**").hasAnyRole("MNG", "STA").antMatchers("/rest/authorities").hasRole("CTM")
				.anyRequest().permitAll();
		// Đăng nhập
		http.formLogin().loginPage("/auth/login/form").loginProcessingUrl("/auth/login")
				.defaultSuccessUrl("/auth/login/success", false).failureUrl("/auth/login/error");
		http.rememberMe().tokenValiditySeconds(86400); // remember me
		// Điều khiển lỗi truy cập không đúng quyền
		http.exceptionHandling().accessDeniedPage("/auth/unauthoried");
		// Đăng xuất
		http.logout().logoutUrl("/auth/logout").logoutSuccessUrl("/auth/logout/success");
		// OAuth2 - Đăng nhâp từ mang xã hôi
		http.oauth2Login().loginPage("/auth/login/form").defaultSuccessUrl("/oauth2/login/success", true)
				.failureUrl("/auth/login/error").authorizationEndpoint().baseUri("/oauth2/authorization");
	}
}
