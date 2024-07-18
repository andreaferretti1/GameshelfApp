package org.application.gameshelfapp.sellvideogames.controller;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.boundary.GoogleBoundary;
import org.application.gameshelfapp.login.dao.AccessDAO;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.application.gameshelfapp.seevideogamecatalogue.SeeGameCatalogueController;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.boundary.MobyGames;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.List;


public class SellVideogamesController {
    public SellVideogamesController(UserBean userBean) throws WrongUserTypeException {
        if(userBean == null || userBean.getTypeOfUser().equals("Customer")){
            throw new WrongUserTypeException("Only Admin or Seller users can access this operation\n");
        }
    }
    public SellingGamesCatalogueBean showSellingGamesCatalogue (FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException, CheckFailedException {
        SeeGameCatalogueController catController = new SeeGameCatalogueController();
        return catController.displaySellingGamesCatalogue(filtersBean);
    }

    public List<String> obtainCategories(){
        SeeGameCatalogueController catController = new SeeGameCatalogueController();
        return catController.getCategoriesValue();
    }

    public List<String> obtainConsoles(){
        SeeGameCatalogueController catController = new SeeGameCatalogueController();
        return catController.getConsolesValue();
    }

    public SellingGamesCatalogueBean addGameToCatalogue (VideogameBean videogameBean) throws PersistencyErrorException, InvalidTitleException, CheckFailedException, NoGameInCatalogueException, GmailException, AlreadyExistingVideogameException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Videogame videogame = new Videogame(videogameBean.getName(), videogameBean.getCopiesBean(), videogameBean.getPriceBean(), videogameBean.getDescriptionBean(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());
        videogame.checkAddedVideogameData(Filters.getCategories(), Filters.getConsoles());

        MobyGames mobyGames = new MobyGames();
        mobyGames.verifyVideogame(videogameBean.getName());

        itemDAO.checkVideogameExistence(new Filters(videogameBean.getName(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean()));
        itemDAO.addGameForSale(videogame);

        AccessDAO accessDAO = PersistencyAbstractFactory.getFactory().createAccessDAO();

        List<Access> customers = accessDAO.getRandomCustomers();
        GoogleBoundary mailSender = new GoogleBoundary();
        for (Access cust: customers){
            mailSender.sendMailToRandomCustomer(cust.getEmail(), videogameBean.getName(), videogameBean.getPlatformBean());
        }
        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }

    public SellingGamesCatalogueBean cancelGameFromCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException, CheckFailedException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(videogameBean.getName(),videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        Videogame game = itemDAO.getVideogame(filters);
        game.removeVideogame(videogameBean.getCopiesBean());
        itemDAO.removeGameForSale(game);

        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }

    public SellingGamesCatalogueBean modifyGameInCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, CheckFailedException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(videogameBean.getName(),videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        Videogame game = itemDAO.getVideogame(filters);
        game.updateVideogame(videogameBean.getCopiesBean(), videogameBean.getPriceBean(), videogameBean.getDescriptionBean());
        itemDAO.updateGameForSale(game);

        FiltersBean filtersBean = new FiltersBean();
        filtersBean.setCategoryBean(videogameBean.getCategoryBean());
        filtersBean.setConsoleBean(videogameBean.getPlatformBean());
        filtersBean.setNameBean(videogameBean.getName());

        return this.showSellingGamesCatalogue(filtersBean);
    }
}
