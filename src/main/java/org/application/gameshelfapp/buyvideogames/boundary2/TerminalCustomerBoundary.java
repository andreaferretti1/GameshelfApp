package org.application.gameshelfapp.buyvideogames.boundary2;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary2.adapter.CustomerAdapter;
import org.application.gameshelfapp.buyvideogames.boundary2.adapter.CustomerBoundaryInterface;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public class TerminalCustomerBoundary implements TerminalBoundary {

    private final CustomerBoundaryInterface customer;
    public static final String START_COMMAND = "Type <see catalogue, gameName/null, console/null, category/null>";

    public TerminalCustomerBoundary(UserBean userBean){
        this.customer = new CustomerAdapter(userBean);
    }
    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, NoGameInCatalogueException, ArrayIndexOutOfBoundsException, RefundException, GameSoldOutException, SyntaxErrorException, InvalidAddressException {
        switch(command[0]){
            case"see catalogue" -> {
                return this.catalogueBeanToString(this.customer.searchVideogame(command[1], command[2], command[3]));
            }
            case "select gameToBuy" -> {
                this.customer.chooseGameToBuy(command[1], command[2], command[3], Integer.parseInt(command[4]), Float.parseFloat(command[5]));
                return "\n\n Type <pay, yourName, typeOfcard, paymentKey, street, region, country>\n";
            }
            case "pay" -> {
                this.customer.pay(command[1], command[2], command[3], command[4], command[5], command[6]);
                return "\n\nPayment successful\n";
            }
            default -> {
                return "You inserted tne wrong command";
            }
        }
    }

    @Override
    public UserBean getUserBean(){
        return this.customer.getUserBean();
    }

    private String catalogueBeanToString(SellingGamesCatalogueBean catalogueBean){
        StringBuilder game = new StringBuilder();
        for(VideogameBean gameBean: catalogueBean.getSellingGamesBean()){
            game.append(String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", gameBean.getName(), gameBean.getPlatformBean(), gameBean.getCategoryBean(), gameBean.getCopiesBean(), gameBean.getPriceBean(), gameBean.getDescriptionBean())) ;
        }
        return game + "\nType <select game, gameTitle, console, category, copies, price>\n\n";
    }
}
