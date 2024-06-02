package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;

public class AccessThroughLogIn extends Access {

    public AccessThroughLogIn(String email, String password, String typeOfUser){
        super(email, password, null, typeOfUser);
    }

    public void checkAccount(Access user) throws CheckFailedException {

        if(user == null || !this.encodedPassword.equals(user.getEncodedPassword())){
            throw new CheckFailedException("Credentials are not correct");
        }
    }
}
