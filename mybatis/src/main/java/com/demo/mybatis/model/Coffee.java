package com.demo.mybatis.model;

import java.util.Date;

import org.joda.money.Money;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 与jpa比较，mybatis实体类配置更简单
 * @author xiaozhi009
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Coffee {
    private Long id;
    private String name;
    private Money price;
    private Date createTime;
    private Date updateTime;
}
