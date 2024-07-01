package org.application.gameshelfapp.sellvideogames.bean;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.sellvideogames.entities.SellingGamesCatalogue;

import java.util.ArrayList;
import java.util.List;

public class SellingGamesCatalogueBean implements Observer{

    private List<VideogameBean> sellingGamesBean;

    private SellingGamesCatalogue subject;

    public void setSellingGamesBean(List<VideogameBean> sellingGamesBean){
        this.sellingGamesBean = sellingGamesBean;
    }

    public List<VideogameBean> getSellingGamesBean(){
        return this.sellingGamesBean;
    }

    public void setSubject(SellingGamesCatalogue sellingGamesCatalogue){
        this.subject = sellingGamesCatalogue;
    }

    @Override
    public void update() {
        List<Videogame> videogames = this.subject.getSellingGames();
        this.sellingGamesBean = new ArrayList<>();
        for (Videogame game: videogames){
            VideogameBean gameBean = new VideogameBean();
            gameBean.setVideogame(game);
            gameBean.getVideogameFromModel();
            this.sellingGamesBean.add(gameBean);
        }

    }
}
