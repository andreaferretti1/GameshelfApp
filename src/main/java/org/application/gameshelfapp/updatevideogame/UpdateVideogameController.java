package org.application.gameshelfapp.updatevideogame;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.List;

public class UpdateVideogameController {
    public void updateGameInCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(videogameBean.getName(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        List<Videogame> updating = itemDAO.getVideogamesFiltered(filters);
        Videogame game = updating.getFirst();
        game.setCopies(videogameBean.getCopiesBean());
        game.setPrice(videogameBean.getPriceBean());
        game.setDescription(videogameBean.getDescriptionBean());
        itemDAO.updateGameForSale(game);
    }
}
