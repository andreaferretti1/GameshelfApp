package org.application.gameshelfapp.buyvideogames.entities;

public class User {

    private final String username;
    private final String email;
    private final String typeOfUser;

    public User(String username, String email, String typeOfUser){
        this.username = username;
        this.email = email;
        this.typeOfUser = typeOfUser;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }
}
