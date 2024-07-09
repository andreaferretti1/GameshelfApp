package org.application.gameshelfapp.confirmsale.boundary2.adapter;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;

import java.util.List;

public interface SellerBoundaryInterface {
    UserBean getUserBean();
    List<SaleBean> getSalesToConfirm() throws PersistencyErrorException, WrongUserTypeException;
    void confirmSale(String id) throws PersistencyErrorException, ConfirmDeliveryException, GmailException, WrongSaleException;
}
