package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.exception.FiltersException;

public class FiltersBean {

    private  final String name;
    private  final String console;
    private  final String online;
    private  final String category;

    public FiltersBean(String name, String console, String online, String category) throws FiltersException {
        if(name.isEmpty() || console == null || online == null || category == null) throw new FiltersException("You should insert all the filters");
        this.name = name;
        this.console = console;
        this.online = online;
        this.category = category;
    }
    public String getName() {
        return this.name;
    }

    public String getConsole() {
        return this.console;
    }

    public String getOnline() {
        return this.online;
    }

    public String getCategory() {
        return this.category;
    }
}
