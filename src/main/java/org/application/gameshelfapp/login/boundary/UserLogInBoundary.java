package org.application.gameshelfapp.login.boundary;

import org.application.gameshelfapp.StartingPageController;
import org.application.gameshelfapp.login.RegistrationPageController;
import org.application.gameshelfapp.login.bean.LogInBean;
import org.application.gameshelfapp.login.bean.RegistrationBean;
import org.application.gameshelfapp.login.controller.LogInController;
import org.application.gameshelfapp.login.entities.TypeOfAccess;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyAccountException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorEcxeption;
import org.application.gameshelfapp.login.graphiccontrollers.InsertCodeController;

import java.io.IOException;

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
        try {
            this.logBean = new LogInBean(email, password);
            this.controller.logIn(logBean);
            this.startingPageController.switchToHomePage();
        } catch (SyntaxErrorEcxeption | PersistencyErrorException | PersistencyAccountException | CheckFailedException e) {
            this.startingPageController.displayErrorWindow(e.getMessage());
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public void register(String username, String email, String password) {
        this.regBean = new RegistrationBean(username, email, password);
        try {
            this.controller.registration(this.regBean);
            this.registrationPageController.switchToInsertCodeScene();
        } catch (PersistencyErrorException | PersistencyAccountException e) {
            this.registrationPageController.displayErrorWindow(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void checkCode(String code){
        try{
            int insertedCode = Integer.parseInt(code);
            this.regBean.setCheckCode(insertedCode);
            this.controller.checkCode(this.regBean);
        } catch(NumberFormatException | CheckFailedException e){
            this.insertCodeController.displayErrorWindow(e.getMessage());
        }
    }

    public void goToHomePage(TypeOfAccess type){

        switch(type){
            case TypeOfAccess.LOGIN:
                this.startingPageController.switchToHomePage();
                break;

            case TypeOfAccess.REGISTRATION:
                this.insertCodeController.switchToHomePage();
                break;

        }
    }

}
