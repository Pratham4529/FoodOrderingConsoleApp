package com.aurionpro.discount;

public class NoDiscount implements IDiscount  {\
	@Override
	public double applyDiscount(double totalAmount) {
	    return 0.0;
	}


}
