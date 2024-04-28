package org.application.gameshelfapp.buyvideogames.entities;

public class Filters {

    private final String name;
    private final String platform;
    private final String category;

    public Filters(String name, String console, String category){
        this.name = name;
        this.platform = console;
        this.category = category;
    }

    public String getName() {
        return this.name;
    }

    public String getPlatform() {
        return this.platform;
    }

    public String getCategory() {
        return this.category;
    }
}
