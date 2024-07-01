package org.application.gameshelfapp.sellvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.controller.SellGamesController;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public class SellerAddGamesBoundary {

    private final SellGamesController sellGamesController;

    private UserBean userBean;

    private SellingGamesCatalogueBean sellingGamesCatalogueBean;


    public SellerAddGamesBoundary (SellGamesController controller, UserBean userBean) {
        this.sellGamesController = controller;
        this.userBean = userBean;
    }

    public void getSellingCatalogue (FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException {
        this.sellingGamesCatalogueBean = this.sellGamesController.showSellingGamesCatalogue(filtersBean);
    }

    public void addSellingGames (VideogameBean videogameBean) throws PersistencyErrorException, CheckFailedException, InvalidTitleException, NoGameInCatalogueException {
        this.sellingGamesCatalogueBean = this.sellGamesController.addGameToCatalogue(videogameBean);
    }

    public void removeSellingGames(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException {
        this.sellingGamesCatalogueBean = this.sellGamesController.removeGameFromCatalogue(videogameBean);
    }

    public void updateSellingGame(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException{
        this.sellingGamesCatalogueBean = this.sellGamesController.updateGameInCatalogue(videogameBean);
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
