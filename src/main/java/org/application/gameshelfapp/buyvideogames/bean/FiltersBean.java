package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.exception.FiltersException;

public class FiltersBean {

    private String nameBean;
    private String consoleBean;
    private String onlineBean;
    private String categoryBean;
    public String getNameBean() {
        return this.nameBean;
    }

    public String getConsoleBean() {
        return this.consoleBean;
    }

    public String getOnlineBean() {
        return this.onlineBean;
    }

    public String getCategoryBean() {
        return this.categoryBean;
    }

    public void setNameBean(String nameBean){
        this.nameBean = nameBean;
    }

    public void setCategoryBean(String categoryBean) throws FiltersException {
        if(!categoryBean.equals("Sport") && !categoryBean.equals("Shooting") && !categoryBean.equals("Adventure")) throw new FiltersException("You should insert if videogame genre is Sport, Shooting or Adventure");
        this.categoryBean = categoryBean;
    }

    public void setConsoleBean(String consoleBean) throws FiltersException {
        if(!consoleBean.equals("PlayStation") && !consoleBean.equals("XBox") && !consoleBean.equals("Pc")) throw new FiltersException("You should insert Playstation, Xbox or Pc for console");
        this.consoleBean = consoleBean;
    }

    public void setOnlineBean(String onlineBean) throws FiltersException {
        if(!onlineBean.equals("Online") && !onlineBean.equals("Offline")) throw new FiltersException("You should insert Online or Offline");
        this.onlineBean = onlineBean;
    }
}
