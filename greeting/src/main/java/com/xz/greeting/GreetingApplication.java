package com.xz.greeting;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GreetingApplication implements ApplicationRunner{
	private String name;
	public GreetingApplication() {
		this("xiaozhi");
	}
	public GreetingApplication(String name) {
		this.name = name;
        log.info("Initializing GreetingApplication for {}.", name);
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Hello everyone! We all like you!");
	}

}
