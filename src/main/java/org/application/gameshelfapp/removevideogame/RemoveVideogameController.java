package org.application.gameshelfapp.removevideogame;

import org.application.gameshelfapp.buyvideogames.bean.VideogameBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.List;

public class RemoveVideogameController {
    public void removeGameFromCatalogue(VideogameBean videogameBean) throws PersistencyErrorException, NoGameInCatalogueException, GameSoldOutException {
        ItemDAO itemDAO = PersistencyAbstractFactory.getFactory().createItemDAO();
        Filters filters = new Filters(videogameBean.getName(), videogameBean.getPlatformBean(), videogameBean.getCategoryBean());

        List<Videogame> selling = itemDAO.getVideogamesFiltered(filters);
        selling.getFirst().setCopies(videogameBean.getCopiesBean());
        itemDAO.removeGameForSale(selling.getFirst());
    }
}
