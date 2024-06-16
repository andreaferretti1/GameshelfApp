package org.application.gameshelfapp.buyvideogames.entities;

public class Sale {
    private int id;
    private Videogame gameSold;
    private String email;
    private String address;
    private String state;
    private String name;

    public static final String TO_CONFIRM = "To confirm";
    public static final String CONFIRMED = "Confirmed";
    public Sale(Videogame game, String email, String address, String state, String name) {
        this.gameSold = game;
        this.email = email;
        this.address = address;
        this.state = state;
        this.name = name;
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Videogame getVideogameSold(){
        return this.gameSold;
    }
    public void setVideogameSold(Videogame game){
        this.gameSold = game;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
