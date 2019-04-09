package com.xz.nosql.model;

import java.util.Date;

import org.joda.money.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 存于mongodb中
 * @author xiaozhi009
 *
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Coffee {
	@Id
	private String id;
	private String name;
	private Money price;
	private Date createTime;
	private Date updateTime;
}
