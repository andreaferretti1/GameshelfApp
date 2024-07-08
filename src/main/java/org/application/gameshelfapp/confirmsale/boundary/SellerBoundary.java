package org.application.gameshelfapp.confirmsale.boundary;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public class SellerBoundary {
    private List<SaleBean> salesBean;
    private final BuyGamesController buyGamesController;
    private final UserBean userBean;
    public SellerBoundary(UserBean userBean ){
        this.buyGamesController = new BuyGamesController();
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }

    public List<SaleBean> getSalesBean() {
        return this.salesBean;
    }

    public void setSalesBean(List<SaleBean> salesBean) {
        this.salesBean = salesBean;
    }

    public void getGamesToSend() throws PersistencyErrorException {
        this.salesBean = this.buyGamesController.getSales();
    }

    public void sendGame(long id) throws ConfirmDeliveryException, GmailException, PersistencyErrorException, WrongSaleException {
        this.buyGamesController.confirmDelivery(id);
    }
}
