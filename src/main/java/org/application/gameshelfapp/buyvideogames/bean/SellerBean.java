package org.application.gameshelfapp.buyvideogames.bean;

public class SellerBean {
     private final String name;
     private final String email;
     private final int numberOfCopies;
     private float price;
     private final String description;

     public SellerBean(String name, String email, int copies, float price, String description){
         this.name = name;
         this.email = email;
         this.numberOfCopies = copies;
         this.price = price;
         this.description = description;
     }

    public String getName() {
        return this.name;
    }

    public int getNumberOfCopies() {
        return this.numberOfCopies;
    }

    public String getEmail() {
        return this.email;
    }
    public String getDescription(){
         return this.description;
    }

    public float getPrice() {
        return this.price;
    }
}
