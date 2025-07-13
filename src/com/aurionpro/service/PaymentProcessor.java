package com.aurionpro.service;

import com.aurionpro.payment.IPayment;

public class PaymentProcessor {
	private IPayment payment;
	
	public PaymentProcessor(IPayment payment) {
	    this.payment = payment;
	}

	public void process(double amount) {
	    payment.pay(amount);
	}

}
