package com.globits.wl.config;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.globits.core.rest",//"com.globits.cms.rest",
		"com.globits.security.rest", "com.globits.wl.rest", "com.globits.core.exceptionhandler"})
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(httpMessageConverter());
		converters.add(jackson2HttpMessageConverter());
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
		source.setBasename("WEB-INF/i18n/messages");
		source.setUseCodeAsDefaultMessage(true);
		source.setFallbackToSystemLocale(false);
		return source;
	}

	@Bean
	public CookieLocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("vi", "VN"));
		resolver.setCookieName("locale");
		resolver.setCookieMaxAge(2629740); // 1 month

		return resolver;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource());

		return validator;
	}

	@Bean
	public StringHttpMessageConverter httpMessageConverter() {
		return new StringHttpMessageConverter();
	}

	@Bean
	public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}

}
