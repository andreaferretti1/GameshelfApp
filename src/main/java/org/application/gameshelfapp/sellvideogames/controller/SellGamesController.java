package org.application.gameshelfapp.sellvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.entities.SellingGamesCatalogue;

import java.util.List;


public class SellGamesController {

    public SellingGamesCatalogueBean showSellingGamesCatalogue (FiltersBean filtersBean) throws PersistencyErrorException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(filtersBean.getNameBean(), filtersBean.getConsoleBean(), filtersBean.getCategoryBean());
        SellingGamesCatalogueBean sellingGamesCatalogueBean = new SellingGamesCatalogueBean();
        SellingGamesCatalogue sellingGamesCatalogue = new SellingGamesCatalogue();

        sellingGamesCatalogue.attach(sellingGamesCatalogueBean);
        sellingGamesCatalogueBean.setSubject(sellingGamesCatalogue);

        List<Videogame> gamesOnSale = itemDAO.getVideogamesForSale(filters);
        sellingGamesCatalogue.setSellingGames(gamesOnSale);
        sellingGamesCatalogueBean.setSubject(null);

        return sellingGamesCatalogueBean;
    }

    public SellingGamesCatalogueBean addGameToCatalogue (VideogameBean videogameBean) throws PersistencyErrorException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Videogame videogame = new Videogame(videogameBean.getName(), videogameBean.getCopiesBean(),videogameBean.getPriceBean(), videogameBean.getDescriptionBean(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        itemDAO.addGameForSale(videogame);

        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }


}
