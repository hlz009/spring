package com.xz.hateoascustom;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.xz.hateoascustom.support.CustomConnectionKeepAliveStrategy;

@SpringBootApplication
public class HateoasCustomApplication {

	public static void main(String[] args) {
		SpringApplication.run(HateoasCustomApplication.class, args);
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory requestFactory() {
		PoolingHttpClientConnectionManager connectionManager = 
				new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);
		
		CloseableHttpClient httpClient = HttpClients.custom()
										.setConnectionManager(connectionManager)
										.evictIdleConnections(30, TimeUnit.SECONDS)
										.disableAutomaticRetries()
										.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
										.build();
		HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory(httpClient);
		return requestFactory;
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(100))
				.setReadTimeout(Duration.ofMillis(500))
				.requestFactory(this::requestFactory)
				.build();
	}
}
