package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;

public abstract class Access {
    protected String username;
    protected String email;
    protected String password;
    protected String encodedPassword = null;
    protected Encoder encoder;

    public abstract void checkCorrectness(TypeOfAccess access, int checkCode, String passwordToCheck) throws CheckFailedException;

    protected String getPassword(){
        return encoder.getEncryptedPassword();
    }

    public String getUsername(){
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public String getEncodedPassword(){
        return this.encodedPassword;
    }

}
