package org.application.gameshelfapp.sellvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public interface SellVideogames {
    SellingGamesCatalogueBean getSellingGamesCatalogue(String gameTitle, String console, String category) throws PersistencyErrorException, NoGameInCatalogueException;
    SellingGamesCatalogueBean addSellingGames(String gameTitle, String console, String category, String description, int copies, float price) throws PersistencyErrorException, CheckFailedException, NoGameInCatalogueException, GmailException, InvalidTitleException, AlreadyExistingVideogameException;
    SellingGamesCatalogueBean removeSellingGames(String gameTitle, String console, String category, String description, int copies, float price) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException;
    SellingGamesCatalogueBean updateSellingGames(String gameTitle, String console, String category, String description, int copies, float price) throws PersistencyErrorException, NoGameInCatalogueException;
    UserBean getUserBean();
}
