package org.application.gameshelfapp.login.controller;

import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.boundary.FacebookLogInBoundary;
import org.application.gameshelfapp.login.boundary.GoogleLogInBoundary;
import org.application.gameshelfapp.login.boundary.UserLogInBoundary;
import org.application.gameshelfapp.login.entities.*;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.PersistencyAccountException;

import java.io.IOException;


public class LogInController {
    private final UserLogInBoundary logInBoundary;

    private FacebookLogInBoundary facebookBoundary;
    private GoogleLogInBoundary googleBoundary;
    private Access access;
    private AccessDAO accessDAO;

    public LogInController(UserLogInBoundary userLogInBoundary){
        this.logInBoundary = userLogInBoundary;
        this.accessDAO = new AccessDAOCSV(); //TODO meccanismo di creazione dei dao
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

        access = AccessFactory.createAccess(TypeOfAccess.LOGIN, null, logEmail, logPassword);
        try {

            Access accessRetrieved = this.accessDAO.retrieveAccountByEmail(TypeOfAccess.LOGIN, this.access.getEmail());
            access.checkCorrectness(TypeOfAccess.LOGIN, -1, accessRetrieved.getEncodedPassword());
            this.logInBoundary.goToHomePage(TypeOfAccess.LOGIN);

        } catch(PersistencyErrorException e){

        } catch(PersistencyAccountException e){

        } catch (CheckFailedException e) {
            //Di' alla boundary di mostrare il pannello di errore delle credenziali inserite
        } catch(IOException e){

        }


    }

    public void registration(RegistrationBean regBean){
        String regUsername = regBean.getUsernameBean();
        String regEmail = regBean.getEmailBean();
        String regPassword = regBean.getPasswordBean();
        access = AccessFactory.createAccess(TypeOfAccess.REGISTRATION, regUsername, regEmail, regPassword);
        //manda mail
    }

    public void checkCode(RegistrationBean regBean){
        try {
            this.access.checkCorrectness(TypeOfAccess.REGISTRATION, regBean.getCheckCode(), null);
        } catch (CheckFailedException e) {
            //Di' alla boundary di mostrare il pannello di errore
        }
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
