package com.example.demomutijdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.example.demomutijdbc.service.WxService;

/**
 * 测试事务回滚
 * @author xiaozhi009
 *
 */
@Component
public class DeclarativeTransactionDemo implements CommandLineRunner{

	private static final Logger log = LoggerFactory.getLogger(DeclarativeTransactionDemo.class);

	@Autowired
	private WxService wxService;
	@Autowired
	@Qualifier("primaryJdbcTemplate") // 取wx这个数据源
	private JdbcTemplate jdbcTemplate;

	@Override
	public void run(String... args) throws Exception {
		wxService.insertRecord();
		log.info("AAA {}",
				jdbcTemplate
						.queryForObject("SELECT COUNT(*) FROM test WHERE name='AAA'", Long.class));
		try {
			wxService.insertThenRollback();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM test WHERE name='BBB'", Long.class));
		}

		try {
			wxService.invokeInsertThenRollback();
		} catch (Exception e) {
			log.info("BBB {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM test WHERE name='BBB'", Long.class));
		}
	}
	
}
