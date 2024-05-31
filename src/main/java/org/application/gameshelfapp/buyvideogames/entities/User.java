package org.application.gameshelfapp.buyvideogames.entities;

public class User {

    private String username;
    private String email;
    private String typeOfUser;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }
}
