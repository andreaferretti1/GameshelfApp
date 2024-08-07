package org.application.gameshelfapp.buyvideogames.entities;

public class Credentials {

    private String name;
    private String typeOfPayment;
    private String paymentKey;
    private String address;
    private String email;

    public Credentials(String name, String address, String email){
        this(name, null, null, address, email);
    }

    public Credentials(String name, String typeOfPayment, String paymentKey, String address, String email){
        this.name = name;
        this.typeOfPayment = typeOfPayment;
        this.paymentKey = paymentKey;
        this.address = address;
        this.email = email;
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTypeOfPayment(String typeOfPayment) {
        this.typeOfPayment = typeOfPayment;
    }
    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
