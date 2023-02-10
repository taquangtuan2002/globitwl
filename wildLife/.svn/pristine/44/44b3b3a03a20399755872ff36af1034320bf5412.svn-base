package com.globits.wl.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.core.Ordered;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInit implements WebApplicationInitializer, Ordered {

	@Override
	public void onStartup(ServletContext container) throws ServletException {
		// TODO Auto-generated method stub

		// Creates the root application context
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();

		appContext.setDisplayName("WL application API");

		// Registers the application configuration with the root context
		appContext.register(ApplicationConfig.class);

		// Creates the Spring Container shared by all Servlets and Filters
		container.addListener(new ContextLoaderListener(appContext));

		// This is to make sure we can obtain the HttpServletRequest in the
		// RequestContextHolder
		container.addListener(new RequestContextListener());
		container.addListener(new HttpSessionEventPublisher());

		// Character encoding filter
		FilterRegistration.Dynamic characterEncodingFilter = container.addFilter("CharacterEncodingFilter",
				CharacterEncodingFilter.class);
		characterEncodingFilter.setInitParameter("encoding", "UTF-8");
		characterEncodingFilter.setInitParameter("forceEncoding", Boolean.TRUE.toString());
		characterEncodingFilter.addMappingForUrlPatterns(null, false, "/*");

		// Enabling other HTTP methods other than just GET and POST for forms
		FilterRegistration.Dynamic httpMethodFilter = container.addFilter("HiddenHttpMethodFilter",
				HiddenHttpMethodFilter.class);
		httpMethodFilter.addMappingForUrlPatterns(null, false, "/*");

		// Gzip compression filter
		// FilterRegistration.Dynamic gzipFilter = container.addFilter("Gzip Compressor
		// Filter",
		// DelegatingFilterProxy.class);
		// gzipFilter.setInitParameter("targetBeanName", "gzipCompressionFilterBean");
		// gzipFilter.addMappingForUrlPatterns(null, false, "/*");

		// Entity Manager In View Filter
		FilterRegistration.Dynamic entityManagerInViewFilter = container.addFilter("EntityManagerInViewFilter",
				OpenEntityManagerInViewFilter.class);
		entityManagerInViewFilter.setInitParameter("singleSession", Boolean.TRUE.toString());
		entityManagerInViewFilter.setInitParameter("entityManagerFactoryBeanName", "entityManagerFactory");
		entityManagerInViewFilter.setInitParameter("flushMode", "AUTO");
		entityManagerInViewFilter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");

		// Creates the dispatcher servlet contex
		AnnotationConfigWebApplicationContext servletContext = new AnnotationConfigWebApplicationContext();

		// Registers the servlet configuraton with the dispatcher servlet
		// context
		servletContext.register(WebConfig.class);

		// Config for Restful servlet dispatcher
		ServletRegistration.Dynamic restfulDispatcher = container.addServlet("restful",
				new DispatcherServlet(servletContext));
		restfulDispatcher.setLoadOnStartup(1);
		restfulDispatcher.addMapping("/api/*");

		// Config for Website servlet dispatcher
		// ServletRegistration.Dynamic servletDispatcher =
		// container.addServlet("website",
		// new DispatcherServlet(servletContext));
		// servletDispatcher.setLoadOnStartup(2);
		// servletDispatcher.addMapping("*.html");
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}
}
