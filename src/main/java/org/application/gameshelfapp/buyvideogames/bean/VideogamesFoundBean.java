package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.entities.VideogamesFound;

import java.util.ArrayList;
import java.util.List;

public class VideogamesFoundBean {

    private List<VideogameBean> gamesFoundBean;
    private VideogamesFound videogamesFound;

    public List<VideogameBean> getGamesFoundBean(){
        return this.gamesFoundBean;
    }

    public void setVideogamesFound(VideogamesFound videogamesFound) {
        this.videogamesFound = videogamesFound;
    }

    public void getInformationFromModel(){
        this.gamesFoundBean = new ArrayList<VideogameBean>();
        List<Videogame> gamesFound = videogamesFound.getGamesFound();
        for(Videogame game: gamesFound){
            VideogameBean gameBean = new VideogameBean();
            gameBean.getVideogameFromModel(game);
            this.gamesFoundBean.add(gameBean);
        }
    }

    
}
