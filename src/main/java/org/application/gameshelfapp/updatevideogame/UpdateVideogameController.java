package org.application.gameshelfapp.updatevideogame;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public class UpdateVideogameController {
    public void updateGameInCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, CheckFailedException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(videogameBean.getPlatformBean(), videogameBean.getCategoryBean());
        filters.setName(videogameBean.getName());

        Videogame game = itemDAO.getVideogame(filters);
        game.updateVideogame(videogameBean.getCopiesBean(), videogameBean.getPriceBean(), videogameBean.getDescriptionBean());
        itemDAO.updateGameForSale(game);
    }
}
