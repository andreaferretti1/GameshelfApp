package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.CheckFailedException;

import java.util.List;

public class Filters {

    private String name;
    private String console;
    private String category;
    private static List<String> consoles;
    private static List<String> categories;

    public Filters(String name, String console, String category) throws CheckFailedException{
        this.name = name;
        this.setConsole(console);
        this.setCategory(category);
    }

    public static void setConsoles(List<String> consoles) {
        Filters.consoles = consoles;
    }

    public static void setCategories(List<String> categories) {
        Filters.categories = categories;
    }
    public static List<String> getConsoles(){
        return Filters.consoles;
    }
    public static List<String> getCategories(){
        return Filters.categories;
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

    public void setName(String name){
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
