package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.login.bean.UserBean;

public class SellerBoundary {
    private final BuyGamesController buyGamesController;
    private final UserBean userBean;
    public SellerBoundary(BuyGamesController controller, UserBean userBean ){
        this.buyGamesController = controller;
        this.userBean = userBean;
    }
}
