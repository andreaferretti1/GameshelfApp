package org.application.gameshelfapp.login.entities;

public class AccessFactory {

    private AccessFactory(){
        throw new IllegalStateException("Utility class");
    }

    public static Access createAccess(TypeOfAccess type, String username, String email, String password){
        if(type == TypeOfAccess.REGISTRATION){
            return createAccessThroughRegistration(username, email, password);
        }
        return createAccessThroughlogIn(email, password);
    }

    public static AccessThroughLogIn createAccessThroughlogIn(String email, String password){
        return new AccessThroughLogIn(email, password);
    }

    public static AccessThroughRegistration createAccessThroughRegistration(String username, String email, String password){
        return new AccessThroughRegistration(username, email, password);
    }
}
