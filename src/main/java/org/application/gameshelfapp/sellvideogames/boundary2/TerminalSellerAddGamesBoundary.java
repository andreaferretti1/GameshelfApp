package org.application.gameshelfapp.sellvideogames.boundary2;


import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.seevideogamecatalogue.utilityboundary2.ConvertToStringUtility;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.boundary2.adapter.SeeGameCatalogue;
import org.application.gameshelfapp.sellvideogames.boundary2.adapter.SeeGameCatalogueAdapter;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public class TerminalSellerAddGamesBoundary implements TerminalBoundary{

    SeeGameCatalogue seeGameCatalogueAdapter;

    public static final String START_COMMAND = "\nType <filters>\n";

    public TerminalSellerAddGamesBoundary(UserBean userBean) throws WrongUserTypeException { this.seeGameCatalogueAdapter = new SeeGameCatalogueAdapter(userBean);}

    private String catalogueBeanToString(SellingGamesCatalogueBean catalogueBean){
        return ConvertToStringUtility.catalogueBeanToString(catalogueBean) + "\nType <add/remove/update, gameTitle, console, category, description, copies, price>\n\n";
    }
    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, CheckFailedException, GmailException, ArrayIndexOutOfBoundsException, NoGameInCatalogueException, InvalidTitleException, AlreadyExistingVideogameException, GameSoldOutException {
        switch(command[0]){
            case "filters" -> {
                return ConvertToStringUtility.mapToString(this.seeGameCatalogueAdapter.getFilters());
            }
            case "search" -> {
                return this.catalogueBeanToString(this.seeGameCatalogueAdapter.getSellingGamesCatalogue(command[1], command[2], command[3]));
            }
            case "add" -> {
                return this.catalogueBeanToString(this.seeGameCatalogueAdapter.addSellingGames(command[1], command[2], command[3], command[4], Integer.parseInt(command[5]), Float.parseFloat(command[6])));
            }
            case "remove" -> {
                return this.catalogueBeanToString(this.seeGameCatalogueAdapter.removeSellingGames(command[1], command[2], command[3], command[4], Integer.parseInt(command[5]), Float.parseFloat(command[6])));
            }
            case "update" -> {
                return this.catalogueBeanToString(this.seeGameCatalogueAdapter.updateSellingGames(command[1], command[2], command[3], command[4], Integer.parseInt(command[5]), Float.parseFloat(command[6])));
            }
            default -> {
                return "Wrong command inserted";
            }
        }
    }
    @Override
    public UserBean getUserBean(){ return this.seeGameCatalogueAdapter.getUserBean();}
}
