package com.xz.nosql.service;

import java.util.ArrayList;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xz.nosql.model.Coffee2;
import com.xz.nosql.model.CoffeeOrder;
import com.xz.nosql.model.OrderState;
import com.xz.nosql.repository.CoffeeOrderRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CoffeeOrderService {
	@Autowired
	private CoffeeOrderRepository orderRepository;

    public CoffeeOrder createOrder (String customer, Coffee2... coffee) {
    	CoffeeOrder order = CoffeeOrder.builder()
    			.customer(customer)
    			.items(new ArrayList<>(Arrays.asList(coffee)))
    			.state(OrderState.INIT)
    			.build();
    	CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order:{}", saved);
    	return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
    	if (state.compareTo(order.getState()) <= 0) {
    		return false;
    	}
    	order.setState(state);
    	orderRepository.save(order);
    	log.info("Updated Order:{}", order);
    	return true;
    }
}
