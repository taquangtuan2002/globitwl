package com.globits.wl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.globits.security.CustomUserDetailsService;
import com.globits.security.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userService")
	private CustomUserDetailsService userServiceDetails;

	@Autowired
	private CorsFilter corsFilter;
	
	@Autowired
	public void globalUserDetails(final AuthenticationManagerBuilder auth) throws Exception {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(new BCryptPasswordEncoder());
		provider.setUserDetailsService(userServiceDetails);

		auth.authenticationProvider(provider);

	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/public/**");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {

		// Filters
		http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);

		http.authorizeRequests()

				.antMatchers("/login").permitAll()

				.antMatchers("/tokens/**").permitAll()

				.antMatchers("/admin/**").fullyAuthenticated()

				.antMatchers("/api/**").authenticated()

				.anyRequest().authenticated()

				.and().formLogin().permitAll()

				.and().csrf().disable();

	}

}
