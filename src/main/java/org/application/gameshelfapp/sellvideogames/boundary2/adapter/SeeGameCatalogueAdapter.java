package org.application.gameshelfapp.sellvideogames.boundary2.adapter;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.boundary.SellerAddGamesBoundary;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.*;

public class SeeGameCatalogueAdapter implements SeeGameCatalogue {
    SellerAddGamesBoundary boundary;

    public SeeGameCatalogueAdapter(UserBean userBean) throws WrongUserTypeException { this.boundary = new SellerAddGamesBoundary(userBean); }

    private VideogameBean createVideogameBean(String gameTitle, String console, String category, String description, int copies, float price){
        VideogameBean gameBean = new VideogameBean();
        gameBean.setName(gameTitle);
        gameBean.setPlatformBean(console);
        gameBean.setCategoryBean(category);
        gameBean.setDescriptionBean(description);
        gameBean.setCopiesBean(copies);
        gameBean.setPriceBean(price);
        return gameBean;
    }
    @Override
    public SellingGamesCatalogueBean getSellingGamesCatalogue(String gameTitle, String console, String category) throws PersistencyErrorException, NoGameInCatalogueException, CheckFailedException {
        FiltersBean filtersBean = new FiltersBean();
        if(gameTitle.equals("null")) gameTitle = null;
        if(console.equals("null")) console = null;
        if(category.equals("null")) category = null;
        filtersBean.setNameBean(gameTitle);
        filtersBean.setConsoleBean(console);
        filtersBean.setCategoryBean(category);
        this.boundary.getSellingCatalogue(filtersBean);
        return this.boundary.getSellingGamesCatalogueBean();
    }
    @Override
    public SellingGamesCatalogueBean addSellingGames(String gameTitle, String console, String category, String description, int copies, float price) throws PersistencyErrorException, CheckFailedException, NoGameInCatalogueException, GmailException, InvalidTitleException, AlreadyExistingVideogameException {
        VideogameBean gameBean = this.createVideogameBean(gameTitle, console, category, description, copies, price);
        this.boundary.addSellingGames(gameBean);
        return this.boundary.getSellingGamesCatalogueBean();
    }
    @Override
    public SellingGamesCatalogueBean removeSellingGames(String gameTitle, String console, String category, String description, int copies, float price) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException, CheckFailedException {
        VideogameBean gameBean = this.createVideogameBean(gameTitle, console, category, description, copies, price);
        this.boundary.removeSellingGames(gameBean);
        return this.boundary.getSellingGamesCatalogueBean();
    }
    @Override
    public SellingGamesCatalogueBean updateSellingGames(String gameTitle, String console, String category, String description, int copies, float price) throws PersistencyErrorException, NoGameInCatalogueException, CheckFailedException {
        VideogameBean gameBean = this.createVideogameBean(gameTitle, console, category, description, copies, price);
        this.boundary.updateSellingGame(gameBean);
        return this.boundary.getSellingGamesCatalogueBean();
    }
    @Override
    public UserBean getUserBean(){return this.boundary.getUserBean();}

    @Override
    public Map<String, String[]> getFilters() throws PersistencyErrorException {
        Map<String, String[]> filters = new HashMap<>();
        List<String> cat = this.boundary.getCategories();
        filters.put("Category", cat.toArray(cat.toArray(new String[0])));
        List<String> con = this.boundary.getConsoles();
        filters.put("Console", con.toArray(new String[0]));
        return filters;
    }

}
