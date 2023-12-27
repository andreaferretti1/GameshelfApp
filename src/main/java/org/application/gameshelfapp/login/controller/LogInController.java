package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.boundary.FacebookLogInBoundary;
import org.application.gameshelfapp.login.boundary.GoogleLogInBoundary;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessFactory;
import org.application.gameshelfapp.login.entities.AccessThroughRegistration;


public class LogInController {
    private final UserLogInBoundary logInBoundary;

    private FacebookLogInBoundary facebookBoundary;
    private GoogleLogInBoundary googleBoundary;
    private final AccessFactory factory;
    private Access access;

    public LogInController(UserLogInBoundary userLogInBoundary){
        this.logInBoundary = userLogInBoundary;
        factory = new AccessFactory();
    }


    private void setFacebookBoundary(FacebookLogInBoundary fbBound){
        this.facebookBoundary = fbBound;
    }

    private void setGoogleBoundary(GoogleLogInBoundary gBound){
        this.googleBoundary = gBound;
    }

    private void googleLogIn(){
        this.googleBoundary = new GoogleLogInBoundary();
        this.googleBoundary.log();
    }

    private void facebookLogIn(){
        this.facebookBoundary = new FacebookLogInBoundary();
        this.facebookBoundary.log();
    }
    public void logIn(LogInBean logBean){
        String logEmail = logBean.getEmailBean();
        String logPassword = logBean.getPasswordBean();
        access = factory.createAccess(0, null, logEmail, logPassword);
        access.checkCorrectness(0);
    }

    public void registration(RegistrationBean regBean){
        String regUsername = regBean.getUsernameBean();
        String regEmail = regBean.getEmailBean();
        String regPassword = regBean.getPasswordBean();
        access = factory.createAccess(1, regUsername, regEmail, regPassword);
    }

    public void checkCode(RegistrationBean regBean){
        this.access.checkCorrectness(regBean.getCheckCode());
    }

    public void registrationWithGoogle(){
        setGoogleBoundary(new GoogleLogInBoundary());
        this.googleBoundary.register();
    }

    public void registrationWithFacebook(){
        setFacebookBoundary(new FacebookLogInBoundary());
        this.facebookBoundary.register();
    }

}
