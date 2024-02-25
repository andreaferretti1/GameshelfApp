package org.application.gameshelfapp.login.bean;

public class UserBean {

    private final String username;
    private final String email;
    private final String typeOfUser;

    public UserBean(String username, String email, String typeOfUser){
        this.username = username;
        this.email = email;
        this.typeOfUser = typeOfUser;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }
}
