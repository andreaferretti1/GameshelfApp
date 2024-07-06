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
import org.application.gameshelfapp.removevideogame.RemoveVideogameController;
import org.application.gameshelfapp.seevideogamecatalogue.SeeGameCatalogueController;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.boundary.MobyGames;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.application.gameshelfapp.updatevideogame.UpdateVideogameController;

import java.util.List;


public class SellVideogamesController {
    public SellingGamesCatalogueBean showSellingGamesCatalogue (FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException {
        SeeGameCatalogueController catController = new SeeGameCatalogueController();
        return catController.displaySellingGamesCatalogue(filtersBean);
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

    public SellingGamesCatalogueBean cancelGameFromCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException {
        RemoveVideogameController remController = new RemoveVideogameController();
        remController.removeGameFromCatalogue(videogameBean);

        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }

    public SellingGamesCatalogueBean modifyGameInCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException{
        UpdateVideogameController upController = new UpdateVideogameController();
        upController.updateGameInCatalogue(videogameBean);

        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }
}
