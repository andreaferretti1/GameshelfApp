package org.application.gameshelfapp.sellvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.boundary.MobyGames;
import org.application.gameshelfapp.sellvideogames.entities.SellingGamesCatalogue;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.List;


public class SellGamesController {

    public SellingGamesCatalogueBean showSellingGamesCatalogue (FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(filtersBean.getNameBean(), filtersBean.getConsoleBean(), filtersBean.getCategoryBean());
        SellingGamesCatalogueBean sellingGamesCatalogueBean = new SellingGamesCatalogueBean();
        SellingGamesCatalogue sellingGamesCatalogue = new SellingGamesCatalogue();

        sellingGamesCatalogue.attach(sellingGamesCatalogueBean);
        sellingGamesCatalogueBean.setSubject(sellingGamesCatalogue);

        List<Videogame> gamesOnSale = itemDAO.getVideogamesFiltered(filters);
        sellingGamesCatalogue.setSellingGames(gamesOnSale);
        sellingGamesCatalogueBean.setSubject(null);

        return sellingGamesCatalogueBean;
    }

    public SellingGamesCatalogueBean addGameToCatalogue (VideogameBean videogameBean) throws PersistencyErrorException, InvalidTitleException, CheckFailedException, NoGameInCatalogueException, GmailException, AlreadyExistingVideogameException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Videogame videogame = new Videogame(videogameBean.getName(), videogameBean.getCopiesBean(),videogameBean.getPriceBean(), videogameBean.getDescriptionBean(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        MobyGames mobyGames = new MobyGames();
        mobyGames.verifyVideogame(videogame.getName());

        itemDAO.checkVideogameExistence(new Filters(videogameBean.getName(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean()));
        itemDAO.addGameForSale(videogame);

        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();

        List<Access> customers = accessDAO.getRandomCustomers();
        GoogleBoundary mailSender = new GoogleBoundary();
        for (Access cust: customers){
            mailSender.setMessageToSend(String.format("New Sale: %s for %s", videogameBean.getName(), videogameBean.getPlatformBean()));
            mailSender.sendMail(null, cust.getEmail());
        }
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }

    public SellingGamesCatalogueBean removeGameFromCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(videogameBean.getName(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        List<Videogame> selling = itemDAO.getVideogamesFiltered(filters);
        selling.getFirst().setCopies(videogameBean.getCopiesBean());
        itemDAO.removeGameForSale(selling.getFirst());

        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }

    public SellingGamesCatalogueBean updateGameInCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException{
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(videogameBean.getName(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        List<Videogame> updating = itemDAO.getVideogamesFiltered(filters);
        Videogame game = updating.getFirst();
        game.setCopies(videogameBean.getCopiesBean());
        game.setPrice(videogameBean.getPriceBean());
        game.setDescription(videogameBean.getDescriptionBean());
        itemDAO.updateGameForSale(game);

        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }
}
