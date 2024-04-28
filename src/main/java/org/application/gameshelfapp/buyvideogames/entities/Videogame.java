package org.application.gameshelfapp.buyvideogames.entities;

public class Videogame {

    private String name;
    private int copies;
    private float price;
    private String description;
    public Videogame(String name, int copies, float price, String description){
        this.name = name;
        this.copies = copies;
        this.price = price;
        this.description = description;
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
    public void setCopies(int copies) {
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
}