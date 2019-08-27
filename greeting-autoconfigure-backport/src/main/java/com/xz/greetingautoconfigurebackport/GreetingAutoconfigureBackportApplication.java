package com.xz.greetingautoconfigurebackport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GreetingAutoconfigureBackportApplication {

	@Bean
	public static GreetingBeanFactoryPostProcessor greetingBeanFactoryPostProcessor() {
        return new GreetingBeanFactoryPostProcessor();
    }

}
