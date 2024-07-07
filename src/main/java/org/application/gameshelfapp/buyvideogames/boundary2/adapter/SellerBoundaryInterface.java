package org.application.gameshelfapp.buyvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.bean.SaleBean;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.buyvideogames.exception.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface SellerBoundaryInterface {
    UserBean getUserBean();
    List<SaleBean> getSalesToConfirm() throws PersistencyErrorException;
    void confirmSale(String id) throws PersistencyErrorException, ConfirmDeliveryException, GmailException, WrongSaleException;
}
