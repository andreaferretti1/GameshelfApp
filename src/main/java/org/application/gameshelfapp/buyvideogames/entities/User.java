package org.application.gameshelfapp.buyvideogames.entities;

public class User {

    private final String username;
    private final String email;
    private final String typeOfUser;
    private String address = null;
    private String paymentKey = null;

    public User(String username, String email, String typeOfUser){
        this.username = username;
        this.email = email;
        this.typeOfUser = typeOfUser;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }


    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPaymentKey() {
        return this.paymentKey;
    }

    public void setPaymentKey(String paymentKey) {
        this.paymentKey = paymentKey;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }
}
