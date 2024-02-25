package org.application.gameshelfapp.login.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorException;


public class RegistrationBean extends AccessBean{

    private final String usernameBean;
    private final String typeOfUser;
    private int checkCode;


    public RegistrationBean(String username, String email, String password,String typeOfUser) throws SyntaxErrorException {
        super(email, password);
        this.checkSyntax(username, this.usernameRegex, "username");
        this.usernameBean = username;
        this.typeOfUser = typeOfUser;
    }

    public String getUsernameBean(){
        return this.usernameBean;
    }
    public void setCheckCode(int insertedCode){
        this.checkCode = insertedCode;
    }
    public int getCheckCode(){
        return this.checkCode;
    }
    public String getTypeOfUser() {
        return this.typeOfUser;
    }

}
