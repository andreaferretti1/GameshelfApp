package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.controller.LogInController;

public class UserLogInBoundary {

    private LogInBean logBean = null;
    private RegistrationBean regBean = null;

    private final LogInController controller;


    public UserLogInBoundary(){
        this.controller = new LogInController(this);
    }


    public void log(String email, String password){
        this.logBean = new LogInBean(email, password);
        controller.logIn(logBean);
    }
    public void register(String username, String email, String password){
        this.regBean = new RegistrationBean(username, email, password);
        controller.registration(this.regBean);
    }
    public void checkCode(String code){
        try{
            int insertedCode = Integer.parseInt(code);
            this.regBean.setCheckCode(insertedCode);
            this.controller.checkCode(this.regBean);
        } catch(NumberFormatException e){
            //lancia eccezione
        }
    }

}
