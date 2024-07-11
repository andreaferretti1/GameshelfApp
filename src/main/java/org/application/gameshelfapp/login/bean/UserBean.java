package org.application.gameshelfapp.login.bean;

import org.application.gameshelfapp.buyvideogames.entities.User;

public class UserBean {
    private String username;
    private String email;
    private String typeOfUser;
    private User user;
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
    public void setUser(User user){
        this.user = user;
    }
    public void getDataFromModel(){
        this.username = this.user.getUsername();
        this.email = this.user.getEmail();
        this.typeOfUser = this.user.getTypeOfUser();
        this.user = null;
    }
}
