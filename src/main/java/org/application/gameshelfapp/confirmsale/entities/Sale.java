package org.application.gameshelfapp.confirmsale.entities;

import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;

public class Sale {
    private int id;
    private Videogame gameSold;
    private Credentials credentials;
    private String state;


    public static final String TO_CONFIRM = "To confirm";
    public static final String CONFIRMED = "Confirmed";
    public Sale (Videogame game, Credentials credentials, String state){this(-1, game, credentials, state);}
    public Sale(int id, Videogame game, Credentials credentials, String state) {
        this.id = id;
        this.gameSold = game;
        this.credentials = credentials;
        this.state = state;
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
    public String getState() {
        return this.state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Credentials getCredentials() {
        return this.credentials;
    }
    public void confirm(){
        this.state = Sale.CONFIRMED;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
