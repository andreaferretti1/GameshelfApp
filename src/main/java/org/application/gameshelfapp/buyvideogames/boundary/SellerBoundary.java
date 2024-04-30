package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogamesFoundBean;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class SellerBoundary {

    private VideogamesFoundBean videogamesFoundBean;
    private final BuyGamesController buyGamesController;
    private final UserBean userBean;
    public SellerBoundary(BuyGamesController controller, UserBean userBean ){
        this.buyGamesController = controller;
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public VideogamesFoundBean getVideogamesFoundBean() {
        return this.videogamesFoundBean;
    }

    public void getGamesToSend() throws PersistencyErrorException {
        this.videogamesFoundBean = this.buyGamesController.getSales();
    }

    public void sendGame(VideogameBean gameBean) throws ConfirmDeliveryException, GmailException {
        this.buyGamesController.confirmDelivery(gameBean.getId());
    }
}
