package org.application.gameshelfapp.login.bean;

import org.application.gameshelfapp.login.exception.SyntaxErrorEcxeption;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogInBean {

    private String usernameBean;
    private String emailBean;
    private String passwordBean;

    public LogInBean(String username, String email, String password) throws SyntaxErrorEcxeption{

        this.usernameBean = username;
        this.checkEmailSyntax(email);
        this.emailBean = email;
        this.passwordBean = password;
    }

    public void setEmailBean(String email) throws SyntaxErrorEcxeption{
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

    public String getUsernameBean(){
        return this.usernameBean;
    }

    private void checkEmailSyntax(String s) throws SyntaxErrorEcxeption{
        String syntaxRegex = "[a-zA-Z0-9]+([._-][a-zA-Z0-9]+)?@gmail/.[a-z]+$";
        Pattern p = Pattern.compile(syntaxRegex);
        Matcher m = p.matcher(s);
        boolean b = m.matches();
        if(!b){
            throw new SyntaxErrorEcxeption("Email syntax is incorrect");
        }
    }
}
