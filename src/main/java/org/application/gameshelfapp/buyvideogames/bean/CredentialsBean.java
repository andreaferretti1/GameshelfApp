package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorException;

public class CredentialsBean {

    private String typeOfPaymentBean;
    private String paymentKeyBean;
    private String addressBean;
    public void setTypeOfPaymentBean(String typeOfPaymentBean) throws SyntaxErrorException{
        if(typeOfPaymentBean == null || typeOfPaymentBean.isEmpty()) throw new SyntaxErrorException("Insert type of payment");
        this.typeOfPaymentBean = typeOfPaymentBean;
    }

    public void setPaymentKeyBean(String paymentKeyBean) throws SyntaxErrorException{
        if(paymentKeyBean == null || paymentKeyBean.isEmpty()) throw new SyntaxErrorException("Insert payment key");
        this.paymentKeyBean = paymentKeyBean;
    }

    public void setAddressBean(String addressBean) throws SyntaxErrorException{
        if(addressBean == null || addressBean.isEmpty()) throw new SyntaxErrorException("Insert delivery address");
        this.addressBean = addressBean;
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
