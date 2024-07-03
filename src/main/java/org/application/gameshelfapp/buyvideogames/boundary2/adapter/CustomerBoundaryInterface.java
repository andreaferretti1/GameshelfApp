package org.application.gameshelfapp.buyvideogames.boundary2.adapter;

import org.application.gameshelfapp.login.bean.UserBean;

public abstract class CustomerBoundaryInterface {
    private UserBean userBean;

    public void setUserBean(UserBean userBean){
        this.userBean = userBean;
    }

    public UserBean getUserBean(){
        return this.userBean;
    }

}
