package org.application.gameshelfapp.login.entities;

public class AccessThroughLogIn extends Access {

    public AccessThroughLogIn(String email, String password){
        this.email = email;
        this.password = password;
        this.encoder = new MD5Encoder(password);
    }

    public String getEmail(){
        return this.email;
    }


    @Override
    public void checkCorrectness(int i){

    }

    public void checkPassword(){

    }

    public void checkEmail(){

    }
}
