package org.application.gameshelfapp.confirmsale.boundary2;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.boundary2.adapter.SellerAdapter;
import org.application.gameshelfapp.confirmsale.boundary2.adapter.SellerBoundaryInterface;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;

import java.util.List;

public class TerminalSellerBoundary implements TerminalBoundary {
    private final SellerBoundaryInterface adapter;
    public static final String START_COMMAND = "\nType <see sales>\n";
    public TerminalSellerBoundary(UserBean userBean) throws WrongUserTypeException {
        this.adapter = new SellerAdapter(userBean);
    }

    @Override
    public UserBean getUserBean(){
        return this.adapter.getUserBean();
    }

    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, ConfirmDeliveryException, GmailException, WrongSaleException, ArrayIndexOutOfBoundsException, WrongUserTypeException {
        switch(command[0]){
            case "see sales" -> {
                return this.showSalesToConfirm(this.adapter.getSalesToConfirm());
            }
            case "confirm" -> {
                this.adapter.confirmSale(command[1]);
                return "Game sent";
            }
            default -> {
                return "You inserted the wrong command";
            }
        }

    }

    private String showSalesToConfirm(List<SaleBean> sales){
        StringBuilder stringSales = new StringBuilder();
        for(SaleBean saleBean: sales){
            saleBean.getInformationFromModel();
            stringSales.append(String.format("Id: %d, game name: %s, platform: %s copies: %d, price: %f€, user name: %s, user email: %s, delivery address: %s%n", saleBean.getIdBean(), saleBean.getGameSoldBean().getName(), saleBean.getGameSoldBean().getPlatformBean(), saleBean.getGameSoldBean().getCopiesBean(), saleBean.getGameSoldBean().getPriceBean(), saleBean.getCredentialsBean().getNameBean(), saleBean.getCredentialsBean().getEmailBean(), saleBean.getCredentialsBean().getAddressBean()));
        }
        return  stringSales + "\n\n<Type confirm gameId>\n";
    }
}
