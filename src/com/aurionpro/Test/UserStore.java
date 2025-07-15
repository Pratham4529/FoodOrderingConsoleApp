package com.aurionpro.Test;

import java.util.ArrayList;
import java.util.List;

import com.aurionpro.delivery.*;
import com.aurionpro.discount.*;
import com.aurionpro.payment.*;
import com.aurionpro.user.*;

public class UserStore {
    private static final List<User> users = new ArrayList<>();
    private static final List<IDiscount> discountStrategies = new ArrayList<>();
    private static final List<IPayment> paymentStrategies = new ArrayList<>();
    private static final List<IDelivery> deliveryPartners = new ArrayList<>();

    static {
        users.add(new Admin("admin", "admin123"));
        users.add(new Customer("pratham", "1234", "pratham", "PrathamP"));

        discountStrategies.add(new FlatDiscount());

        paymentStrategies.add(new CashPayment());
        paymentStrategies.add(new UPIPayment());

        deliveryPartners.add(new ZomatoPartner());
        deliveryPartners.add(new SwiggyPartner());
    }

    public static List<User> getUsers() {
        return users;
    }

    public static List<IDiscount> getDiscountStrategies() {
        return discountStrategies;
    }

    public static List<IPayment> getPaymentStrategies() {
        return paymentStrategies;
    }

    public static List<IDelivery> getDeliveryPartners() {
        return deliveryPartners;
    }
} 
