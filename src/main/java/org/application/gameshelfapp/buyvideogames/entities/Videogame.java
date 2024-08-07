package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.CheckFailedException;

import java.util.List;

public class Videogame {

    private String name;
    private int copies;
    private float price;
    private String description;
    private String platform;
    private String category;
    public Videogame(String name, int copies, float price, String description, String platform, String category) {
        this.name = name;
        this.copies = copies;
        this.price = price;
        this.description = description;
        this.platform = platform;
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public int getCopies() {
        return this.copies;
    }
    public void setCopies(int copies){
        this.copies = copies;
    }
    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getPlatform() {
        return this.platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void buyVideogame(int copiesToBuy, float price) throws GameSoldOutException {
        if(copiesToBuy <= 0 || this.copies < copiesToBuy || this.price != price) throw new GameSoldOutException("Price or copies are not correct");
        this.copies = copiesToBuy;
    }

    public void updateVideogame(int copiesToAdd, float newPrice, String newDescription){
        if(copiesToAdd >= 0 && newPrice >0 && newDescription != null){
            this.copies = copiesToAdd;
            this.price = newPrice;
            this.description = newDescription;
        }
    }

    public void removeVideogame(int copiesToRemove){
        if (copiesToRemove > 0) { this.copies = copiesToRemove; }
        else this.copies = 0;
    }

    public void checkAddedVideogameData(List<String> categories, List<String> consoles) throws CheckFailedException{
        if (this.name == null || !consoles.contains(this.platform) || !categories.contains(this.category) || this.description == null || this.copies < 0 || this.price <= 0) throw new CheckFailedException("Invalid informations");
    }
}