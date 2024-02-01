package org.application.gameshelfapp.login.entities;


import java.util.Arrays;

public class Access {

    protected String username;
    protected String email;
    protected String password;
    private String typeOfUser;
    protected String encodedPassword;
    protected Encoder encoder;

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


    public String getEncodedPassword(){
        return this.encodedPassword;
    }
    public String getUsername(){
        return this.username;
    }

    public void encodePassword(){
        this.encoder.cryptPassword();
        this.encodedPassword = this.getEncodedPassword();
        Arrays.fill(this.password.toCharArray(), '\0');
        this.password = null;
    }

}
