package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.SellerBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;

import java.util.ArrayList;

public class CustomerBoundary {

    private final BuyGamesController buyGamesController;
    private ArrayList<SellerBean> sellers;
    private ArrayList<VideogameBean> games;

    public CustomerBoundary(BuyGamesController controller){
        this.buyGamesController = controller;
    }

    public void setSellers(ArrayList<SellerBean> sellerBeans){
        this.sellers = sellerBeans;
    }

    public void setGames(ArrayList<VideogameBean> videogameBeans){
        this.games = videogameBeans;
    }
}
