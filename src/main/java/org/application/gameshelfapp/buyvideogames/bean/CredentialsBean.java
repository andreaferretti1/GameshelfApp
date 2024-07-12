package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CredentialsBean {

    private String nameBean;
    private String typeOfPaymentBean;
    private String paymentKeyBean;
    private String addressBean;
    private String emailBean;
    private Credentials credentials;

    public void setNameBean(String nameBean) throws SyntaxErrorException{
        if(nameBean == null || nameBean.isEmpty()) throw new SyntaxErrorException("Insert your name and surname");
        this.nameBean = nameBean;
    }
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

    public String getNameBean(){
        return this.nameBean;
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

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getEmailBean(){
        return this.emailBean;
    }
    public void setEmailBean(String emailBean) throws SyntaxErrorException{
        Pattern p = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher m = p.matcher(emailBean);
        boolean b = m.matches();
        if(!b) throw new SyntaxErrorException("Wrong email");
        this.emailBean = emailBean;
    }
    public void getInformationFromModel(){
        this.nameBean = this.credentials.getName();
        this.addressBean = this.credentials.getAddress();
        this.emailBean = this.credentials.getEmail();
    }
}
