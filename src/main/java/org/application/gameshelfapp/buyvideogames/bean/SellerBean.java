package org.application.gameshelfapp.buyvideogames.bean;

public class SellerBean extends OwnerBean{

    private final String description;

     public SellerBean(String name, String email, int copies, float price, String description){
         super(name, email, copies, price);
         this.description = description;
     }

    @Override
    public String getSpecificAttributeBean() {
        return this.description;
    }
}
