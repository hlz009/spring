package com.xz.logincontrol;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xz.logincontrol.filter.CompareKickOutFilter;
import com.xz.logincontrol.filter.KickOutFilter;
import com.xz.logincontrol.filter.QueueKickOutFilter;

@Configuration
public class FilterConfig {
	@ConditionalOnProperty(value = {"queue-filter.enabled"})
	@Bean
	public KickOutFilter queueKickOutFilter() {
		return new QueueKickOutFilter();
	}

	@ConditionalOnMissingBean(KickOutFilter.class)
	@Bean
	public KickOutFilter compareKickOutFilter() {
		return new CompareKickOutFilter();
	}

	@Bean
	public FilterRegistrationBean testFilterRegistration(KickOutFilter kickOutFilter) {
		FilterRegistrationBean registration = new FilterRegistrationBean(kickOutFilter);
		registration.addUrlPatterns("/user/*");
		registration.setName("kickOutFilter");
		return registration;
	}

}
