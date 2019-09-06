package com.xz.commandlinedemo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(1)
@Slf4j
public class FooCommandLineRunner implements CommandLineRunner {@Override
	public void run(String... args) throws Exception {
		log.info("Foo");
	}
}
