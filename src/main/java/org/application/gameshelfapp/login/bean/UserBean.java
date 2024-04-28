package org.application.gameshelfapp.login.bean;

public class UserBean {

    private String username;
    private String email;
    private String typeOfUser;
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTypeOfUser(String typeOfUser) {
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
