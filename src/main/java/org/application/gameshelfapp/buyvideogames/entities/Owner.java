package org.application.gameshelfapp.buyvideogames.entities;

public abstract class Owner {

    protected final String name;
    protected final String email;
    protected int numberOfCopies;
    protected final float price;

    protected Owner(String name, String email, int numberOfCopies, float price){
        this.name = name;
        this.email = email;
        this.numberOfCopies = numberOfCopies;
        this.price = price;
    }


    public String getName(){
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }
    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public float getPrice() {
        return price;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public abstract String getSpecificAttribute();
    public abstract void setSpecificAttribute(String s);
}
