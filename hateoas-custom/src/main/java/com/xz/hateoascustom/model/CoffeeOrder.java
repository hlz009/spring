package com.xz.hateoascustom.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString(callSuper = true) 
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 3007265794478869804L;

	private String customer;
    private OrderState state;
}
