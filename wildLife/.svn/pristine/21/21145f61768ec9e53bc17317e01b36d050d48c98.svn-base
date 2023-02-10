package com.globits.wl.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.globits.security.filter.CorsFilter;
import com.globits.security.filter.JsonToUrlEncodedAuthenticationFilter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JsonToUrlEncodedAuthenticationFilter jsonFilter;

	@Autowired
	private CorsFilter corsFilter;

	@Override
	public void configure(final HttpSecurity http) throws Exception {
		// Filters

		http.addFilterBefore(jsonFilter, ChannelProcessingFilter.class)

				.addFilterBefore(corsFilter, ChannelProcessingFilter.class)

				.sessionManagement()

				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)

				.and().httpBasic().disable()

				.anonymous()

				.and().authorizeRequests()

				// .antMatchers("/public").permitAll()

				// .antMatchers("/oauth/**").permitAll()

				.antMatchers("/api/**").authenticated()

				.anyRequest().authenticated()

				.and().csrf().disable();

	}

	@Override
	public void configure(final ResourceServerSecurityConfigurer config) {
		config.tokenServices(tokenServices());
	}

	@Bean
	@Primary
	public DefaultTokenServices tokenServices() {
		final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		return defaultTokenServices;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

}
