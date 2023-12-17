package org.application.gameshelfapp.login.bean;

public class RegistrationBean {

    private String usernameBean;
    private String emailBean;
    private String passwordBean;

    public RegistrationBean(String username, String email, String password){
        this.usernameBean = username;
        this.emailBean = email;
        this.passwordBean = password;
    }

    public void setUsernameBean(String username){
        this.usernameBean = username;
    }

    public void setEmailBean(String email){
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
}
