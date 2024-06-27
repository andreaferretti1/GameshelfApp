package org.application.gameshelfapp.buyvideogames.bean;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.entities.VideogamesFound;

import java.util.ArrayList;
import java.util.List;

public class VideogamesFoundBean {

    private List<VideogameBean> gamesFoundBean;
    private VideogamesFound videogamesFound;

    public List<VideogameBean> getVideogamesFoundBean(){
        return this.gamesFoundBean;
    }
    public void setGamesFoundBean(List<VideogameBean> gamesFoundBean){
        this.gamesFoundBean = gamesFoundBean;
    }

    public void setVideogamesFound(VideogamesFound videogamesFound) {
        this.videogamesFound = videogamesFound;
    }
    public VideogamesFound getVideogamesFound(){
        return this.videogamesFound;
    }
    public void getInformationFromModel(){
        this.gamesFoundBean = new ArrayList<>();
        List<Videogame> gamesFound = videogamesFound.getGamesFound();
        for(Videogame game: gamesFound){
            VideogameBean gameBean = new VideogameBean();
            gameBean.setVideogame(game);
            gameBean.getVideogameFromModel();
            this.gamesFoundBean.add(gameBean);
        }
    }

    
}
