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

import java.util.List;

public class TerminalSellerBoundary implements TerminalBoundary {
    private final SellerBoundaryInterface adapter;
    public static final String START_COMMAND = "Type <see sales>";
    public TerminalSellerBoundary(UserBean userBean){
        this.adapter = new SellerAdapter(userBean);
    }

    @Override
    public UserBean getUserBean(){
        return this.adapter.getUserBean();
    }

    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, ConfirmDeliveryException, GmailException, WrongSaleException, ArrayIndexOutOfBoundsException {
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
            stringSales.append(String.format("Id: %d, game name: %s, platform: %s copies: %d, price: %f, user name: %s, user email: %s, delivery address: %s%n", saleBean.getIdBean(), saleBean.getGameSoldBean().getName(), saleBean.getGameSoldBean().getPlatformBean(), saleBean.getGameSoldBean().getCopiesBean(), saleBean.getGameSoldBean().getPriceBean(), saleBean.getNameBean(), saleBean.getEmailBean(), saleBean.getAddressBean()));
        }
        return  stringSales + "\n\n<Type confirm gameId>\n";
    }
}
