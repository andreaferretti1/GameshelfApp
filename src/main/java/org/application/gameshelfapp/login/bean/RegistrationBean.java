package org.application.gameshelfapp.login.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorEcxeption;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationBean {

    private String usernameBean;
    private String emailBean;
    private String passwordBean;
    private int checkCode;


    public RegistrationBean(String username, String email, String password) {
        this.usernameBean = username;
        try{
            this.checkEmailSyntax(email);
            this.emailBean = email;
        } catch(SyntaxErrorEcxeption e){
            //mostra pannello di blocco
        }
        this.passwordBean = password;
    }

    public void setUsernameBean(String username){
        this.usernameBean = username;
    }

    public void setEmailBean(String email) throws Exception{

        this.checkEmailSyntax(email);
        this.emailBean = email;

    }

    public void setPasswordBean(String password){
        this.passwordBean = password;
    }

    public String getUsernameBean(){
        return this.usernameBean;
    }

    public String getEmailBean(){
        return this.emailBean;
    }

    public String getPasswordBean(){
        return this.passwordBean;
    }

    private void checkEmailSyntax(String s) throws SyntaxErrorEcxeption{
        String syntaxRegex = "[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)?@gmail/.[a-z]+$";
        Pattern p = Pattern.compile(syntaxRegex);
        Matcher m = p.matcher(s);
        boolean b = m.matches();
        if(!b) {
            throw new SyntaxErrorEcxeption("Invalid email address");
        }
    }

    public void setCheckCode(int insertedCode){
        this.checkCode = insertedCode;
    }

    public int getCheckCode(){
        return this.checkCode;
    }
}
