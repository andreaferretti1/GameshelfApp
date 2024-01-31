package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;

public class SellerBoundary {
    private final BuyGamesController buyGamesController;
    public SellerBoundary(BuyGamesController controller){
        this.buyGamesController = controller;
    }
}
