package org.application.gameshelfapp.buyvideogames.entities;

public class Sale {
    private int id;
    private Videogame gameSold;
    private String email;
    private String address;
    private String state;
    private String platform;

    public static final String TO_CONFIRM = "To confirm";
    public static final String CONFIRMED = "Confirmed";
    public Sale(Videogame game, String email, String address, String state, String platform) {
        this.gameSold = game;
        this.email = email;
        this.address = address;
        this.state = state;
        this.platform = platform;
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
    public void setGameSold(Videogame game){
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
    public String getPlatform() {
        return this.platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
