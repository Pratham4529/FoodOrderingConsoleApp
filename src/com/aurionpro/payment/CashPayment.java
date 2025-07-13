package com.aurionpro.payment;

public class CashPayment implements IPayment {
	@Override
	public void pay(double amount) {
	    System.out.printf("₹"+amount+ "will be collected in Cash on Delivery.");
	    System.out.println("Thank you for choosing Cash on Delivery!");
	}

}
