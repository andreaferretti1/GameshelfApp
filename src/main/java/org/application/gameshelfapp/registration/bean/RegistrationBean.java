package org.application.gameshelfapp.registration.bean;

import org.application.gameshelfapp.login.bean.AccessBean;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;


public class RegistrationBean extends AccessBean {

    private  String usernameBean;
    private String typeOfUser;

    private int checkCode;

    public RegistrationBean(){
        super();
    }

    public void setUsernameBean(String usernameBean) throws SyntaxErrorException {
        this.checkSyntax(usernameBean, this.usernameRegex, "username");
        this.usernameBean = usernameBean;
    }
    public void setTypeOfUser(String typeOfUser){
        this.typeOfUser = typeOfUser;
    }
    public void setCheckCode(int insertedCode){
        this.checkCode = insertedCode;
    }

    public String getUsernameBean(){
        return this.usernameBean;
    }
    public int getCheckCode(){
        return this.checkCode;
    }
    public String getTypeOfUser() {
        return this.typeOfUser;
    }
}
