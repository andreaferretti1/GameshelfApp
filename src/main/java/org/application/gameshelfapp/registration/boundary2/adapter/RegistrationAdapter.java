package org.application.gameshelfapp.registration.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.registration.boundary.RegistrationBoundary;

public class RegistrationAdapter implements Registration{

    private final RegistrationBoundary registrationBoundary;
    public RegistrationAdapter(){
        this.registrationBoundary = new RegistrationBoundary();
    }
    @Override
    public String register(String username, String email, String password) throws PersistencyErrorException, CheckFailedException, GmailException, SyntaxErrorException, NullPasswordException {
        this.registrationBoundary.register(username, email, password);
        return "Please check your email and insert the code we sent you through email typing 'code <codeSent>'\n";
    }

    @Override
    public String checkCode(String code) throws PersistencyErrorException, CheckFailedException {
        this.registrationBoundary.checkCode(code);
        return "You're registered.\n\nType <buy>\n";
    }

    @Override
    public UserBean getUserBean(){
        return this.registrationBoundary.getUserBean();
    }

}
