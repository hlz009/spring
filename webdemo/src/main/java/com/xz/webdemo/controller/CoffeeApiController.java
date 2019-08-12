package com.xz.webdemo.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xz.webdemo.mode.Coffee;
import com.xz.webdemo.service.CoffeeService;
import com.xz.webdemo.vo.NewCoffeeRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/coffee")
@Slf4j
public class CoffeeApiController {
	@Autowired
	private CoffeeService coffeeService;

	@PostMapping(path="/", consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	// consumes 指定请求的参数类型  对应的是produces
	// consumes 不同也认为是不同的请求url
	@ResponseStatus(HttpStatus.CREATED)// 返回指定的状态码
	public Coffee addCoffee(@Valid NewCoffeeRequest coffee, 
			BindingResult result) {
		if (result.hasErrors()) {
			log.warn("Binding Errors:{}", result);
			return null;
		}
		return coffeeService.saveCoffee(coffee.getName(), coffee.getPrice());
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest newCoffee) {
		return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
	}

	@PostMapping(path = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
		List<Coffee> coffees = new ArrayList<>();
		if (!file.isEmpty()) {
			BufferedReader reader = null;
			try {
	          reader = new BufferedReader(
	                  new InputStreamReader(file.getInputStream()));
	          String str;
	          while ((str = reader.readLine()) != null) {
	              String[] arr = StringUtils.split(str, " ");
	              if (arr != null && arr.length == 2) {
	                  coffees.add(coffeeService.saveCoffee(arr[0],
	                          Money.of(CurrencyUnit.of("CNY"),
	                                  NumberUtils.createBigDecimal(arr[1]))));
	              }
	          }
			} catch (IOException e) {
				log.error("exception", e);
	          } finally {
	          		IOUtils.closeQuietly(reader);
	          }
		}
	    return coffees;
	}

	@GetMapping(path = "/", params = "!name")
	public List<Coffee> getAll() {
		return coffeeService.getAllCoffee();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Coffee getById(@PathVariable Long id) {
		Coffee coffee = coffeeService.getCoffee(id);
		log.info("Coffee {}:", coffee);
		return coffee;
	}

  @GetMapping(path = "/", params = "name")
  @ResponseBody
  public Coffee getByName(@RequestParam String name) {
      return coffeeService.getCoffee(name);
  }
}
