package org.application.gameshelfapp.buyvideogames.boundary;

import java.security.SecureRandom;

public class Braintree {

    private String transactionId;
    public void pay(float money, String userkey){
        SecureRandom random = new SecureRandom();
        this.transactionId = userkey + String.valueOf(random.nextInt((int) money));
    }

    public void refund(){
        this.transactionId = "refunded";
    }



}
