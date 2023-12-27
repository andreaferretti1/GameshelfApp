package org.application.gameshelfapp.login.entities;

public class AccessFactory {

    public Access createAccess(int type, String username, String email, String password){
        if(type == 1){
            return this.createAccessThroughRegistration(username, email, password);
        }
        return this.createAccessThroughlogIn(email, password);
    }

    public AccessThroughLogIn createAccessThroughlogIn(String email, String password){
        return new AccessThroughLogIn(email, password);
    }

    public AccessThroughRegistration createAccessThroughRegistration(String username, String email, String password){
        return new AccessThroughRegistration(username, email, password);
    }
}
