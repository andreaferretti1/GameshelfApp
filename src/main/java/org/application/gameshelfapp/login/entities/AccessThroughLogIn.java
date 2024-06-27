package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;

public class AccessThroughLogIn extends Access {

    public AccessThroughLogIn(String username, String email, String typeOfUser){
        super(username, email, null, typeOfUser);
    }

    public void checkAccount(Access user) throws CheckFailedException {

        if(user == null || !this.encodedPassword.equals(user.getEncodedPassword())){
            throw new CheckFailedException("Credentials are not correct");
        }
    }
}
