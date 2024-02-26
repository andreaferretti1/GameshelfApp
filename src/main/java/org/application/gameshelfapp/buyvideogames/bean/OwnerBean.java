package org.application.gameshelfapp.buyvideogames.bean;

public abstract class OwnerBean {

    protected final String nameBean;
    protected final String emailBean;
    protected int numberOfCopiesBean;
    protected final float priceBean;

    protected OwnerBean(String nameBean, String emailBean, int numberOfCopiesBean, float priceBean){
        this.nameBean = nameBean;
        this.emailBean = emailBean;
        this.numberOfCopiesBean = numberOfCopiesBean;
        this.priceBean = priceBean;
    }

    public String getNameBean() {
        return this.nameBean;
    }

    public String getEmailBean() {
        return this.emailBean;
    }

    public int getNumberOfCopiesBean() {
        return this.numberOfCopiesBean;
    }

    public float getPriceBean() {
        return this.priceBean;
    }

    public abstract String getSpecificAttributeBean();
}
