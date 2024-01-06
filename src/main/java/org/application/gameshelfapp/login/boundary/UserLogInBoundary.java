package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.StartingPageController;
import org.application.gameshelfapp.login.RegistrationPageController;
import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.controller.LogInController;
import org.application.gameshelfapp.login.entities.TypeOfAccess;
import org.application.gameshelfapp.login.graphiccontrollers.InsertCodeController;

public class UserLogInBoundary {

    private LogInBean logBean = null;
    private RegistrationBean regBean = null;

    private final LogInController controller;

    private StartingPageController startingPageController;
    private RegistrationPageController registrationPageController;
    private InsertCodeController insertCodeController;


    public UserLogInBoundary(StartingPageController controller){
        this.controller = new LogInController(this);
        this.startingPageController = controller;
    }

    public void setStartingPageController(StartingPageController controller){
        this.startingPageController = controller;
    }

    public void setRegistrationPageController(RegistrationPageController controller){
        this.registrationPageController = controller;
    }

    public void setInsertCodeController(InsertCodeController controller){
        this.insertCodeController = controller;
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

        }
    }

    public void goToHomePage(TypeOfAccess type){

        switch(type){
            case TypeOfAccess.LOGIN:
                this.startingPageController.switchToHomePage();

            case TypeOfAccess.REGISTRATION:
                this.insertCodeController.switchToHomePage();

        }
    }

}
