package org.application.gameshelfapp.login.entities;


import org.application.gameshelfapp.login.exception.NullPasswordException;

import java.util.Arrays;

public class Access {

    protected String username;

    protected String email;

    protected String password;

    private final String typeOfUser;

    protected String encodedPassword;

    protected SHA256Encoder encoder;

    protected Access(String username, String email, String password, String typeOfUser){
        this.username = username;
        this.email = email;
        this.password = password;
        this.typeOfUser = typeOfUser;
        this.encoder = new SHA256Encoder(password);
    }

    public String getEmail(){
        return this.email;
    }

    public String getTypeOfUser(){
        return this.typeOfUser;
    }
    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEncodedPassword(){
        return this.encodedPassword;
    }
    public String getUsername(){
        return this.username;
    }
    public void encodePassword() throws NullPasswordException {
        this.encoder.cryptPassword();
        this.encodedPassword = this.encoder.getEncryptedPassword();
        Arrays.fill(this.password.toCharArray(), '\0');
        this.password = null;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
