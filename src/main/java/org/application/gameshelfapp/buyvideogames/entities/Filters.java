package org.application.gameshelfapp.buyvideogames.entities;

public class Filters {

    private final String name;
    private final String console;
    private final String online;
    private final String category;

    public Filters(String name, String console, String online, String category){
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
