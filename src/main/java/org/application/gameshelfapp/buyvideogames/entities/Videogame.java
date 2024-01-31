package org.application.gameshelfapp.buyvideogames.entities;

public class Videogame {

    private String name;
    private String id;

    Owner owner;
    public Videogame(String name, String id, Owner owner){
        this.name = name;
        this.id = id;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getId(){
        return this.id;
    }

    public void createOwnSeller(String name, String email, int numberOfCopies, String description, float price){
        this.owner = new Seller(name, email, numberOfCopies, description, price);
    }

    public void createOwnCustomer(String name, String email, int numberOfCopies, String address){
        this.owner = new Customer(name, email, numberOfCopies, address);
    }

    public String getOwnerName(){
        return this.owner.getName();
    }

    public String getOwnerEmail(){
       return this.owner.getEmail();
    }

    public Integer getOwnerCopies(){
        return this.owner.getNumberOfCopies();
    }
    public void setOwnerCopies(int quantity){this.owner.setNumberOfCopies(quantity);}

    public String getSellerDescription(){
        return this.owner.getSpecificAttribute();
    }


    public float getOwnerPrice(){
        return this.owner.getPrice();
    }

    public String getCustomerAddress(){
        return this.owner.getSpecificAttribute();
    }
}