package com.xz.propertysourcedemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class PropertySourceDemoApplication implements ApplicationRunner {

	@Value("${xz.greeting}")
	private String greeting;

	public static void main(String[] args) {
		SpringApplication.run(PropertySourceDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("greeting -> {}", greeting);
	}
}
