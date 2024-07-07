package org.application.gameshelfapp.buyvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.boundary.CustomerBoundary;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.application.gameshelfapp.buyvideogames.exception.RefundException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.SyntaxErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public class CustomerAdapter implements CustomerBoundaryInterface {

    private final CustomerBoundary customerBoundary;

    public CustomerAdapter(UserBean userBean){
        this.customerBoundary = new CustomerBoundary(userBean);
    }

    @Override
    public UserBean getUserBean(){
        return this.customerBoundary.getUserBean();
    }

    @Override
    public SellingGamesCatalogueBean searchVideogame(String gameNameBean, String consoleBean, String categoryBean) throws PersistencyErrorException, NoGameInCatalogueException {
        this.customerBoundary.insertFilters(gameNameBean, consoleBean, categoryBean);
        return this.customerBoundary.getSellingGamesCatalogueBean();
    }

    @Override
    public void chooseGameToBuy(String gameNameBean, String consoleBean, String categoryBean, int copiesBean, float priceBean){
        VideogameBean videogameBean = new VideogameBean();
        videogameBean.setName(gameNameBean);
        videogameBean.setPlatformBean(consoleBean);
        videogameBean.setCategoryBean(categoryBean);
        videogameBean.setCopiesBean(copiesBean);
        videogameBean.setPriceBean(priceBean);
        this.customerBoundary.setGameToBuy(videogameBean);
    }

    @Override
    public void pay(String nameBean, String typeOfCardBean, String paymentKeyBean, String streetBean, String regionBean, String countryBean) throws PersistencyErrorException, RefundException, NoGameInCatalogueException, GameSoldOutException, SyntaxErrorException, InvalidAddressException {
        this.customerBoundary.insertCredentialsAndPay(nameBean, typeOfCardBean, paymentKeyBean, streetBean, regionBean, countryBean);
    }

}
