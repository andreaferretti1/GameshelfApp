package org.application.gameshelfapp.buyvideogames.entities;

public class Customer extends Owner{

    private  String address;
    protected Customer(String name, String email, int numberOfCopies, String address) {
        super(name, email, numberOfCopies, -1);
        this.address = address;
    }

    @Override
    public String getSpecificAttribute() {
        return this.address;
    }

    @Override
    public void setSpecificAttribute(String address) {
            this.address = address;
    }
}
