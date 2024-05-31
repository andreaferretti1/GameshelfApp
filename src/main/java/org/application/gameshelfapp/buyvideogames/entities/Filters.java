package org.application.gameshelfapp.buyvideogames.entities;

public class Filters {

    private String name;
    private String console;
    private String category;

    public Filters(String name, String console, String category){
        this.name = name;
        this.console = console;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getConsole() {
        return this.console;
    }

    public String getCategory() {
        return this.category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConsole(String platform) {
        this.console = platform;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
