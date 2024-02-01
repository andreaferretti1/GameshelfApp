package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.SellerBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;

import java.util.List;

public class CustomerBoundary {

    private final BuyGamesController buyGamesController;
    private List<SellerBean> sellers;
    private List<VideogameBean> games;

    public CustomerBoundary(BuyGamesController controller){
        this.buyGamesController = controller;
    }

    public void setSellers(List<SellerBean> sellerBeans){
        this.sellers = sellerBeans;
    }

    public void setGames(List<VideogameBean> videogameBeans){
        this.games = videogameBeans;
    }
}
