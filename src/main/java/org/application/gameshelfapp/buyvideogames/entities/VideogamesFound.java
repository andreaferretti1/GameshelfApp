package org.application.gameshelfapp.buyvideogames.entities;

import java.util.ArrayList;
import java.util.List;

public class VideogamesFound {

    private List<Videogame> gamesFound;

    public VideogamesFound(){
        this.gamesFound = new ArrayList<Videogame>();
    }

    public void setGamesFound(List<Videogame> gamesFound){
        this.gamesFound = gamesFound;
    }

    public List<Videogame> getGamesFound(){
        return this.gamesFound;
    }
}
