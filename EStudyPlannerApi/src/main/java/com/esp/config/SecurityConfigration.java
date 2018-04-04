package com.esp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.esp.service.Decoder;

@EnableWebSecurity
@Configuration
public class SecurityConfigration extends WebSecurityConfigurerAdapter {

	@Autowired
	private Decoder decoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(decoder.decodePassword("QWRtaW5AMTIz")).roles("ADMIN");

	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.httpBasic().and().authorizeRequests().antMatchers("/admin").hasRole("ADMIN")
					.and()
					.formLogin().loginPage("/")
					.usernameParameter("userName").passwordParameter("password")
					.and().csrf()
					.disable().headers().frameOptions().disable();

	}

}
