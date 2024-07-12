package org.application.gameshelfapp.buyvideogames.boundary2;

import org.application.gameshelfapp.buyvideogames.boundary2.adapter.CustomerAdapter;
import org.application.gameshelfapp.buyvideogames.boundary2.adapter.CustomerBoundaryInterface;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.seevideogamecatalogue.utilityboundary2.ConvertToStringUtility;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public class TerminalCustomerBoundary implements TerminalBoundary {

    private final CustomerBoundaryInterface customer;
    public static final String START_COMMAND = "\nType <see catalogue, gameName/null, console/null, category/null\n>";

    public TerminalCustomerBoundary(UserBean userBean) throws WrongUserTypeException {
        this.customer = new CustomerAdapter(userBean);
    }
    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, NoGameInCatalogueException, ArrayIndexOutOfBoundsException, RefundException, GameSoldOutException, SyntaxErrorException, InvalidAddressException, GmailException{
        switch(command[0]){
            case"see catalogue" -> {
                return ConvertToStringUtility.mapToString(this.customer.getFilters());
            }
            case "search" ->{
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
        return ConvertToStringUtility.catalogueBeanToString(catalogueBean) + "\nType <select gameToBuy, gameTitle, console, category, copies, price>\n\n";
    }
}
