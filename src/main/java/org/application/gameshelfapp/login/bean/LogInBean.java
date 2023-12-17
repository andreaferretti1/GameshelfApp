package org.application.gameshelfapp.login.bean;

public class LogInBean {
    private String emailBean;
    private String passwordBean;

    public LogInBean(String email, String password){
        this.emailBean = email;
        this.passwordBean = password;
    }

    public void setEmailBean(String email){
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
}
