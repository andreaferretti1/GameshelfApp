package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.exception.FiltersException;

public class FiltersBean {

    private  final String nameBean;
    private  final String consoleBean;
    private  final String onlineBean;
    private  final String categoryBean;

    public FiltersBean(String name, String console, String online, String category) throws FiltersException {
        if(name.isEmpty() || console == null || online == null || category == null) throw new FiltersException("You should insert all the filters");
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
