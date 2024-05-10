package org.application.gameshelfapp.buyvideogames.entities;

import com.google.gson.annotations.SerializedName;

public class Credentials {

    private final String typeOfPayment;
    private final String paymentKey;
    @SerializedName("display_name")
    private final String address;

    public Credentials(String typeOfPayment, String paymentKey, String address){

        this.typeOfPayment = typeOfPayment;
        this.paymentKey = paymentKey;
        this.address = address;
    }


    public String getTypeOfPayment() {
        return this.typeOfPayment;
    }

    public String getPaymentKey() {
        return this.paymentKey;
    }

    public String getAddress() {
        return this.address;
    }
}
