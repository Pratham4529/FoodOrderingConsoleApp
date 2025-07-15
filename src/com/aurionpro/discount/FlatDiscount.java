package com.aurionpro.discount;

public class FlatDiscount implements IDiscount {
	@Override
	public double applyDiscount(double totalAmount) {
	    if (totalAmount > 500) {
	        return 50.0;
	    }
	    return 0.0;
	}
}
