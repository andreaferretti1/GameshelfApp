package org.application.gameshelfapp.login.bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInBean {
    private String emailBean;
    private String passwordBean;

    public LogInBean(String email, String password){
        this.emailBean = email;
        this.passwordBean = password;
    }

    public void setEmailBean(String email){
        this.checkEmailSyntax(email);
        this.emailBean = email;
    }

    public void setPasswordBean(String password){
        this.passwordBean = password;
    }

    public String getEmailBean(){
        return this.emailBean;
    }

    public String getPasswordBean(){
        return this.passwordBean;
    }

    public void checkEmailSyntax(String s){
        String syntaxRegex = "[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)?@gmail/.[a-z]+$";
        Pattern p = Pattern.compile(syntaxRegex);
        Matcher m = p.matcher(s);
        boolean b = m.matches();
        if(!b){ //lancia eccezione
        }
    }
}
