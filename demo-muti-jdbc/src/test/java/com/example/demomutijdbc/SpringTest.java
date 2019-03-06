package com.example.demomutijdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.demomutijdbc.exception.sqlException.CustomDuplicatedKeyException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringTest {
	
	@Autowired
	@Qualifier("primaryJdbcTemplate")
	private JdbcTemplate jdbcTemplate;

	@Test(expected = CustomDuplicatedKeyException.class)
	public void testThrowingCustomException() {
		jdbcTemplate.execute("INSERT INTO test (id, name) VALUES (1, 'a')");
		jdbcTemplate.execute("INSERT INTO test (id, name) VALUES (1, 'b')");
	}

}
