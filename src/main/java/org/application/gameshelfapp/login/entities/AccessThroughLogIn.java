package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;

import java.util.Arrays;

public class AccessThroughLogIn extends Access {

    public AccessThroughLogIn(String email, String password){
        this.email = email;
        this.password = password;
        this.encoder = new MD5Encoder(password);
    }


    @Override
    public void checkCorrectness(TypeOfAccess type, int i, String passwordToCheck) throws CheckFailedException {

        this.encoder.cryptPassword();
        this.encodedPassword = encoder.getEncryptedPassword();
        Arrays.fill(this.password.toCharArray(), '\0');
        if(!(this.encodedPassword.equals(passwordToCheck))){
                throw new CheckFailedException("Either password or email are incorrect");
        }

    }

}
