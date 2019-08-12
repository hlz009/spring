package com.xz.webdemo.mode;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.money.Money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 如果实体名和表名一致（表名下划线，实体名满足驼峰命名）
 * table注解中的那么可省去
 * @ToString(callSuper = true) toString 输出父类字段
 * @author xiaozhi009
 *
 */

@Entity
@Table(name = "t_coffee")
@Builder
@Data
@ToString(callSuper = true) 
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -1292381308215499443L;
	
	private String name;
	// PersistentMoneyMinorAmount 可以将xx.00变成xx00c插入到数据库中
	@Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyMinorAmount", 
			parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", 
			value = "CNY")})
	private Money price;
}
