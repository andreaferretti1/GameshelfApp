package org.application.gameshelfapp.sellvideogames.boundary2;


import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary2.TerminalBoundary;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.boundary2.adapter.SeeGameCatalogue;
import org.application.gameshelfapp.sellvideogames.boundary2.adapter.SeeGameCatalogueAdapter;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.Map;

public class TerminalSellerAddGamesBoundary implements TerminalBoundary {

    SeeGameCatalogue seeGameCatalogueAdapter;

    public static final String START_COMMAND = "Type <show>";

    public TerminalSellerAddGamesBoundary(UserBean userBean) throws WrongUserTypeException { this.seeGameCatalogueAdapter = new SeeGameCatalogueAdapter(userBean);}

    private String catalogueBeanToString(SellingGamesCatalogueBean catalogueBean){
        StringBuilder game = new StringBuilder();
        for(VideogameBean gameBean: catalogueBean.getSellingGamesBean()){
           game.append(String.format("name: %s, console: %s, category: %s, copies: %d, price: %f, description: %s%n", gameBean.getName(), gameBean.getPlatformBean(), gameBean.getCategoryBean(), gameBean.getCopiesBean(), gameBean.getPriceBean(), gameBean.getDescriptionBean())) ;
        }
        return game + "\nType <add/remove/update, gameTitle, console, category, description, copies, price>\n\n";
    }
    private String mapToString(Map<String, String[]> map){
        StringBuilder filters = new StringBuilder();
        for(Map.Entry<String, String[]> entry: map.entrySet()){
            filters.append(entry.getKey()).append(": ").append(String.join(", ", entry.getValue())).append("\n");
        }
        return filters + "\nType <search, gameTitle, console, category>\n\n";
    }
    @Override
    public String executeCommand(String[] command) throws PersistencyErrorException, CheckFailedException, GmailException, ArrayIndexOutOfBoundsException, NoGameInCatalogueException, InvalidTitleException, AlreadyExistingVideogameException, GameSoldOutException {
        switch(command[0]){
            case "filters" -> {
                return this.mapToString(this.seeGameCatalogueAdapter.getFilters());
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
