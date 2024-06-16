package org.application.gameshelfapp.buyvideogames.entities;

import java.util.List;

public class VideogamesFound {

    private List<Videogame> gamesFound;

    public void setGamesFound(List<Videogame> gamesFound){
        this.gamesFound = gamesFound;
    }

    public List<Videogame> getGamesFound(){
        return this.gamesFound;
    }
}
