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

    public void setAddressBean(String streetBean, String regionBean, String countryBean) throws SyntaxErrorException{
        if(streetBean == null || streetBean.isEmpty()) throw new SyntaxErrorException("Insert delivery address");
        else if(regionBean == null || regionBean.isEmpty()) throw new SyntaxErrorException("Insert region");
        else if (countryBean == null || countryBean.isEmpty()) throw new SyntaxErrorException("Insert country");
        this.addressBean = streetBean + "," + regionBean + "," + countryBean;
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
