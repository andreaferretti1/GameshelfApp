package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;

import java.util.List;

public class Filters {

    private String name;
    private String console;
    private String category;
    public static List<String> consoles;
    public static List<String> categories;

    public Filters(String console, String category) throws CheckFailedException{
        this(null, console, category);
    }
    public Filters(String name, String console, String category) throws CheckFailedException{
        this.name = name;
        this.setConsole(console);
        this.setCategory(category);
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

    public void setName(String name) throws CheckFailedException{
        if(name == null) throw new CheckFailedException("You should specify videogame name");
        this.name = name;
    }

    public void setConsole(String platform) throws CheckFailedException {
        if(!Filters.consoles.contains(platform)) throw new CheckFailedException("Wrong console");
        this.console = platform;
    }

    public void setCategory(String category) throws CheckFailedException{
        if(!Filters.categories.contains(category)) throw new CheckFailedException("Wrong category");
        this.category = category;
    }
}
