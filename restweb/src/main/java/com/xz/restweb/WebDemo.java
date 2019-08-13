package com.xz.restweb;

import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.xz.restweb.model.Coffee;
import com.xz.restweb.support.CustomConnectionKeepAliveStrategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebDemo implements CommandLineRunner {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * httpclient连接池
	 * @return
	 */
	@Bean
	public HttpComponentsClientHttpRequestFactory requestFactory() {
		PoolingHttpClientConnectionManager connectionManager = 
				new PoolingHttpClientConnectionManager(30, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(200);
		connectionManager.setDefaultMaxPerRoute(20);

		CloseableHttpClient httpClient = HttpClients.custom()
							.setConnectionManager(connectionManager)
							.evictIdleConnections(30, TimeUnit.SECONDS)
							.disableAutomaticRetries() // 舍弃自动重连
							.setKeepAliveStrategy(new CustomConnectionKeepAliveStrategy())
							.build();
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
//		RestTemplate restT = builder.build();
//		restT.getMessageConverters()
//				.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
//		return restT;
		return builder.setConnectTimeout(Duration.ofMillis(100))
				.setReadTimeout(Duration.ofMillis(500))
				.requestFactory(this::requestFactory)
				.build();
	}

	@Override
	public void run(String... args) throws Exception {
//		test1();
		URI uri = UriComponentsBuilder
				.fromUriString("http://localhost:8080/coffee/?name={name}")
				.build("mocha");
		RequestEntity<Void> req = RequestEntity.get(uri)
									.accept(MediaType.APPLICATION_XML)
									.build();
		ResponseEntity<String> resp = restTemplate.exchange(req, String.class);
		log.info("Response Status:{}, Response Headers:{}", resp.getStatusCode(), 
				resp.getHeaders().toString());
		log.info("Coffee:{}", resp.getBody());

		String coffeeUri = "http://localhost:8080/coffee/";
		Coffee request = Coffee.builder()
							.name("Americano")
							.price(Money.of(CurrencyUnit.of("CNY"), 25.00))
							.build();
		Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
		log.info("New Coffee:{}", response);

		// 解决泛型类型
		ParameterizedTypeReference<List<Coffee>> ptr =
				new ParameterizedTypeReference<List<Coffee>>() {};
		ResponseEntity<List<Coffee>> list = restTemplate.exchange(coffeeUri, 
				HttpMethod.GET, null, ptr);
		list.getBody().forEach(c -> log.info("Coffee:{}", c));
	}

//	private void test1() {
//		URI uri = UriComponentsBuilder
//					.fromUriString("http://localhost:8080/coffee/{id}")
//					.build(1);
//		ResponseEntity<Coffee> c = restTemplate.getForEntity(uri, Coffee.class);
//		log.info("Response Status:{}, Response Header:{}", c.getStatusCode(), 
//				c.getHeaders().toString());
//		log.info("Coffee:{}", c.getBody());
//
//		String coffeeUri = "http://localhost:8080/coffee/";
//		Coffee request = Coffee.builder()
//				.name("Americano")
//				.price(BigDecimal.valueOf(25.00))
//				.build();
//		Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
//		log.info("New Coffee:{}", response);
//
//		String s = restTemplate.getForObject(coffeeUri, String.class);
//		log.info("String:{}", s);
//	}
}
