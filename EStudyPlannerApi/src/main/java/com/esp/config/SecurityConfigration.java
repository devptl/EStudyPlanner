package com.esp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.esp.service.Decoder;

/**
 * Configuration class for the spring security on the admin pages
 * 
 * @author mindfire
 *
 */
@EnableWebSecurity
@Configuration
public class SecurityConfigration extends WebSecurityConfigurerAdapter {

	@Autowired
	private Decoder decoder;

	/**
	 * To set the authorised user for the admin page
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(decoder.decodePassword("QWRtaW5AMTIz")).roles("ADMIN");

	}

	/**
	 * Checking the authentication when hits the admin page
	 */
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().and().authorizeRequests().antMatchers("/admin").hasRole("ADMIN").and().formLogin()
				.loginPage("/").usernameParameter("userName").passwordParameter("password").and().csrf().disable()
				.headers().frameOptions().disable();

	}

}
