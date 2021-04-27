package com.demo.socialnetwork.configs;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication().dataSource(dataSource);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/").hasRole("EMPLOYEE")
			.antMatchers("/users/**").hasRole("EMPLOYEE")
			.antMatchers("/deps/**").hasRole("EMPLOYEE")
			.antMatchers("/messages").hasRole("EMPLOYEE")
			.antMatchers("/user_edit").hasRole("EMPLOYEE")
			.antMatchers("/user_edit_submit").hasRole("EMPLOYEE")
			.antMatchers("/dep_edit").hasRole("MANAGER")
			.antMatchers("/dep_edit_submit").hasRole("MANAGER")
			.antMatchers("/admin_panel").hasRole("ADMIN")
			.antMatchers("/user_delete/**").hasRole("ADMIN")
			.antMatchers("/user_promote/**").hasRole("ADMIN")
			.antMatchers("/user_demote/**").hasRole("ADMIN")
			.and()
			.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/authenticate")
				.permitAll()
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/login").permitAll()
			.and()
			.exceptionHandling().accessDeniedPage("/access_denied");
		
	}
}
