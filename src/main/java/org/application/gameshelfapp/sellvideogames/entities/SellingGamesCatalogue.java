package org.application.gameshelfapp.sellvideogames.entities;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;

import java.util.List;

public class SellingGamesCatalogue extends Subject{

    private List<Videogame> sellingGames;

    public SellingGamesCatalogue(){
        super();
    }

    public void setSellingGames(List<Videogame> sellingGames){
        this.sellingGames = sellingGames;
        this.notifyObservers();
    }

    public List<Videogame> getSellingGames(){
        return this.sellingGames;
    }
}
