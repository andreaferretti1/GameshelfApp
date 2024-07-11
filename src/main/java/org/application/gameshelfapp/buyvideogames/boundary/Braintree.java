package org.application.gameshelfapp.buyvideogames.boundary;

import java.security.SecureRandom;

public class Braintree {

    private String transactionId;
    public void pay(float money, String userKey, String paymentMethod){
        SecureRandom random = new SecureRandom();
        this.transactionId = userKey + random.nextInt((int) money) + paymentMethod;
    }

    public void refund(){
        this.transactionId = "refunded";
    }
}
