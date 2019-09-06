package com.xz.commandlinedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandlineDemoApplication {

	public static void main(String[] args) {
//		new SpringApplicationBuilder(CommandLineApplication.class)
//		.web(WebApplicationType.NONE)
//		.run(args);
		SpringApplication.run(CommandlineDemoApplication.class, args);
	}

}
