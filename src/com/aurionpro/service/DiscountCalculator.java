package com.aurionpro.service;

import com.aurionpro.discount.IDiscount;
import com.aurionpro.model.Order;

public class DiscountCalculator {
	private IDiscount discount;
	
	public DiscountCalculator(IDiscount discount) {
	    this.discount = discount;
	}

	public double calculate(Order order) {
	    return discount.applyDiscount(order.getTotalBeforeDiscount());
	}


}
