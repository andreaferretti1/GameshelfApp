package org.application.gameshelfapp.buyvideogames.bean;

public class CustomerBean extends OwnerBean{

    private String addressBean;
    protected CustomerBean(String name, String email, int numberOfCopies, String address, float priceSold) {
        super(name, email, numberOfCopies, priceSold);
        this.addressBean = address;
    }

    @Override
    public String getSpecificAttributeBean() {
        return this.addressBean;
    }
}
