package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorException;

public class CredentialsBean {

    private final String typeOfPaymentBean;
    private final String paymentKeyBean;
    private final String addressBean;

    public CredentialsBean(String typeOfPaymentBean, String paymentKeyBean, String address) throws SyntaxErrorException{

        this.checkSyntax(typeOfPaymentBean, paymentKeyBean, address);
        this.typeOfPaymentBean = typeOfPaymentBean;
        this.paymentKeyBean = paymentKeyBean;
        this.addressBean = address;
    }

    private void checkSyntax(String typeOfPaymentBean, String paymentKeyBean, String addressBean) throws SyntaxErrorException {
        if(typeOfPaymentBean.isEmpty() || paymentKeyBean.isEmpty() || addressBean.isEmpty()) throw new SyntaxErrorException("Please fill all the fields");
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
