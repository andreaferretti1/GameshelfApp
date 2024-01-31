package org.application.gameshelfapp.buyvideogames.boundary;

import java.security.SecureRandom;

public class Braintree {

    private String transactionId;
    public void pay(float money, String userkey){
        SecureRandom random = new SecureRandom();
        this.transactionId = String.valueOf(random.nextInt());
    }

    public void refund(){
        this.transactionId = "refunded";
    }



}
