package com.demo.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.demo.mybatis.mapper.CoffeeMapper;
import com.demo.mybatis.model.Coffee;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * @EnableJpaRepositories 在同一个项目中springboot会自动配置，不需手动添加该注解
 * 如果我们需要定义Entity和Repository不在应用程序所在包及其子包，
 * 就需要使用@EntityScan和@EnableJpaRepositories。
 *
 * @author xiaozhi009
 *
 */

@Component
@Slf4j
public class DemoRun implements CommandLineRunner {
	
	@Autowired
	private CoffeeMapper coffeeMapper;

	@Override
	@Transactional
	public void run(String... args) throws Exception {
//		Coffee c = Coffee.builder().name("espresso")
//				.price(Money.of(CurrencyUnit.of("CNY"), 20.0)).build();
//		Long id = coffeeMapper.save(c);
//		log.info("Coffee {} => {}", id, c);
//
//		c = coffeeMapper.findById(id);
//		log.info("Coffee {}", c);
//		generateArtifacts();
		runPage();
	}

	private void runPage() {
		coffeeMapper.findAllWithRowBounds(new RowBounds(1, 3))
		.forEach(c -> log.info("Page(1) Coffee {}", c));
		coffeeMapper.findAllWithRowBounds(new RowBounds(2, 3))
				.forEach(c -> log.info("Page(2) Coffee {}", c));
		
		log.info("===================");
		
		coffeeMapper.findAllWithRowBounds(new RowBounds(1, 0))
				.forEach(c -> log.info("Page(1) Coffee {}", c));
		
		log.info("===================");
		
		coffeeMapper.findAllWithParam(1, 3)
				.forEach(c -> log.info("Page(1) Coffee {}", c));
		List<Coffee> list = coffeeMapper.findAllWithParam(2, 3);
		PageInfo<Coffee> page = new PageInfo<Coffee>(list);
		log.info("PageInfo: {}", page);

		PageHelper.startPage(1, 3);
		coffeeMapper.findAll()
				.forEach(c -> log.info("Page(3) Coffee {}", c));
	}
	
	/**
	 * mybatis代码自动生成
	 * 不需要maven执行generator
	 * @throws Exception
	 */
	private void generateArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
}
