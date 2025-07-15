package com.aurionpro.service;

import com.aurionpro.delivery.IDelivery;
import com.aurionpro.delivery.SwiggyPartner;
import com.aurionpro.delivery.ZomatoPartner;

import java.util.Random;

public class DeliveryService {
	
	public IDelivery assign() {
	    Random random = new Random();
	    return (random.nextBoolean()) ? new SwiggyPartner() : new ZomatoPartner();
	}


}
