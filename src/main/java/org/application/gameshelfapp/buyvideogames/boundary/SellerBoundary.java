package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.SaleBean;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public class SellerBoundary {

    private List<SaleBean> salesBean;
    private final BuyGamesController buyGamesController;
    private final UserBean userBean;
    public SellerBoundary(BuyGamesController controller, UserBean userBean ){
        this.buyGamesController = controller;
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public List<SaleBean> getSalesBean() {
        return this.salesBean;
    }

    public void getGamesToSend() throws PersistencyErrorException {
        this.salesBean = this.buyGamesController.getSales();
    }

    public void sendGame(int index) throws ConfirmDeliveryException, GmailException{
        this.buyGamesController.confirmDelivery(this.salesBean.get(index));
    }
}
