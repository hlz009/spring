package com.example.demojdbc;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class MyCommandLineRunner implements CommandLineRunner{

	private static final Logger logger = LoggerFactory.getLogger(MyCommandLineRunner.class);

	@Autowired
	private DataSource dataSource;
	
	@Override
    public void run(String... var1) throws Exception{
		// 不指定默认采用的是HikariDataSource (HikariPool-1)
		logger.info("springboot自动采用的datasource-->" + dataSource.toString());
    }

}
