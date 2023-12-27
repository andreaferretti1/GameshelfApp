package org.application.gameshelfapp.login.entities;

public abstract class Access {
    protected String email;
    protected String password;
    protected Encoder encoder;

    public abstract void checkCorrectness(int i);

    protected String getPassword(){
        return encoder.getEncryptedPassword();
    }

}
