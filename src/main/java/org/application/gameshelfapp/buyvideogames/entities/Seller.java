package org.application.gameshelfapp.buyvideogames.entities;

public class Seller extends Owner{
    private String description;

    public Seller(String name, String email, int numberOfCopies, String description, float price){
        super(name, email, numberOfCopies, price);
        this.description = description;
    }

    @Override
    public String getSpecificAttribute() {
        return this.description;
    }

    @Override
    public void setSpecificAttribute(String description) {
        this.description = description;
    }

}

