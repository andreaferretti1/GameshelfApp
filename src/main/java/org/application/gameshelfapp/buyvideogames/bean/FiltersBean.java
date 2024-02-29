package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.exception.FiltersException;

public class FiltersBean {

    private  final String nameBean;
    private  final String consoleBean;
    private  final String onlineBean;
    private  final String categoryBean;

    public FiltersBean(String name, String console, String online, String category) throws FiltersException {
        if(!console.equals("PlayStation") && !console.equals("XBox") && !console.equals("Pc")) throw new FiltersException("You should type Playstation, Xbox or Pc for console");
        if(!online.equals("Online") && !online.equals("Offline")) throw new FiltersException("You should type Online or Offline");
        if(!category.equals("Sport") && !category.equals("Shooting") && !category.equals("Adventure")) throw new FiltersException("You should specify whether videogame genre is Sport, Shooting or Adventure");
        this.nameBean = name;
        this.consoleBean = console;
        this.onlineBean = online;
        this.categoryBean = category;
    }
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
}
