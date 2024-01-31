package org.application.gameshelfapp.buyvideogames.bean;

public class FiltersBean {

    private  String name;
    private  String console;
    private  String online;
    private  String category;

    public void setName(String name) {
        this.name = name;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public void setCategory(String category) {
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
