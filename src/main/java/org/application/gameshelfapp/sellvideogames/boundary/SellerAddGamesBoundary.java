package org.application.gameshelfapp.sellvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.controller.SellVideogamesController;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public class SellerAddGamesBoundary {

    private final SellVideogamesController sellVideogamesController;

    private UserBean userBean;

    private SellingGamesCatalogueBean sellingGamesCatalogueBean;


    public SellerAddGamesBoundary(UserBean userBean) {
        this.sellVideogamesController = new SellVideogamesController();
        this.userBean = userBean;
    }

    public void getSellingCatalogue (FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException {
        this.sellingGamesCatalogueBean = this.sellVideogamesController.showSellingGamesCatalogue(filtersBean);
    }

    public void addSellingGames (VideogameBean videogameBean) throws PersistencyErrorException, CheckFailedException, InvalidTitleException, NoGameInCatalogueException, GmailException, AlreadyExistingVideogameException {
        this.sellingGamesCatalogueBean = this.sellVideogamesController.addGameToCatalogue(videogameBean);
    }

    public void removeSellingGames(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException {
        this.sellingGamesCatalogueBean = this.sellVideogamesController.removeGameFromCatalogue(videogameBean);
    }

    public void updateSellingGame(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException{
        this.sellingGamesCatalogueBean = this.sellVideogamesController.updateGameInCatalogue(videogameBean);
    }

    public SellingGamesCatalogueBean getSellingGamesCatalogueBean () {
        return this.sellingGamesCatalogueBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return this.userBean;
    }
}
