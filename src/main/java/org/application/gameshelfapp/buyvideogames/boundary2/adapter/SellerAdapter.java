package org.application.gameshelfapp.buyvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.bean.SaleBean;
import org.application.gameshelfapp.buyvideogames.boundary.SellerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.buyvideogames.exception.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public class SellerAdapter implements SellerBoundaryInterface{
    private final SellerBoundary boundary;
    public SellerAdapter(UserBean userBean){
        this.boundary = new SellerBoundary(userBean);
    }

    @Override
    public UserBean getUserBean(){
        return this.boundary.getUserBean();
    }

    @Override
    public List<SaleBean> getSalesToConfirm() throws PersistencyErrorException {
        this.boundary.getGamesToSend();
        return this.boundary.getSalesBean();
    }

    @Override
    public void confirmSale(String id) throws PersistencyErrorException, ConfirmDeliveryException, GmailException, WrongSaleException {
        this.boundary.sendGame(Integer.parseInt(id));
    }
}
