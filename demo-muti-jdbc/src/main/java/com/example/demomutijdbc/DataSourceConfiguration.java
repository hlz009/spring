package com.example.demomutijdbc;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 严格按着命名区分
 * 用@Resource 
 * 则@Qualifier可省略
 * @author xiaozhi009
 *
 */
@Configuration
public class DataSourceConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfiguration.class);

	@Bean
    @ConfigurationProperties("wx.datasource")
    public DataSourceProperties wxDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name="wxDataSource")
    public DataSource wxDataSource() {
        DataSourceProperties dataSourceProperties = wxDataSourceProperties();
        logger.info("wx datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name="wxTxManager")
    @Resource
    public PlatformTransactionManager wxTxManager(@Qualifier("wxDataSource") 
    	DataSource wxDataSource) {
        return new DataSourceTransactionManager(wxDataSource);
    }

    @Bean
    @ConfigurationProperties("guessidiom.datasource")
    public DataSourceProperties guessIdiomDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name="guessIdiomDataSource")
    public DataSource guessIdiomDataSource() {
        DataSourceProperties dataSourceProperties = guessIdiomDataSourceProperties();
        logger.info("guessidiom datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean(name="guessIdiomTxManager")
    @Resource
    public PlatformTransactionManager guessIdiomTxManager(@Qualifier("guessIdiomDataSource") 
    	DataSource guessIdiomDataSource) {
        return new DataSourceTransactionManager(guessIdiomDataSource);
    }

    @Bean(name = "primaryJdbcTemplate")
    public JdbcTemplate primaryJdbcTemplate(@Qualifier("wxDataSource") 
    	DataSource wxDataSource) {
        return new JdbcTemplate(wxDataSource);
    }
    @Bean(name = "secondaryJdbcTemplate")
    public JdbcTemplate secondaryJdbcTemplate(@Qualifier("guessIdiomDataSource") 
    	DataSource guessIdiomDataSource) {
        return new JdbcTemplate(guessIdiomDataSource);
    }
}
