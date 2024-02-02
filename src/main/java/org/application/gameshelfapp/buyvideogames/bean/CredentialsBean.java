package org.application.gameshelfapp.buyvideogames.bean;

public class CredentialsBean {

    private final String typeOfPaymentBean;
    private final String paymentKeyBean;
    private final String addressBean;

    public CredentialsBean(String typeOfPaymentBean, String paymentKeyBean, String address){

        this.typeOfPaymentBean = typeOfPaymentBean;
        this.paymentKeyBean = paymentKeyBean;
        this.addressBean = address;
    }

    public String getTypeOfPaymentBean() {
        return this.typeOfPaymentBean;
    }

    public String getPaymentKeyBean() {
        return this.paymentKeyBean;
    }

    public String getAddressBean() {
        return this.addressBean;
    }
}
