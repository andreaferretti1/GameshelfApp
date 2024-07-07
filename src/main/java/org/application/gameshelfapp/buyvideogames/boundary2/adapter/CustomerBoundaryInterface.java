package org.application.gameshelfapp.buyvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public interface CustomerBoundaryInterface{

    UserBean getUserBean();
    SellingGamesCatalogueBean searchVideogame(String gameNameBean, String consoleBean, String categoryBean) throws PersistencyErrorException, NoGameInCatalogueException;
    void chooseGameToBuy(String gameNameBean, String consoleBean, String categoryBean, int copiesBean, float priceBean);
    void pay(String nameBean, String typeOfCardBean, String paymentKeyBean, String streetBean, String regionBean, String countryBean) throws PersistencyErrorException, RefundException, NoGameInCatalogueException, GameSoldOutException, SyntaxErrorException, InvalidAddressException;

}
