package org.application.gameshelfapp.seevideogamecatalogue;

import org.application.gameshelfapp.buyvideogames.bean.FiltersBean;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAO;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.bean.SellingGamesCatalogueBean;
import org.application.gameshelfapp.sellvideogames.entities.SellingGamesCatalogue;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.List;

public class SeeGameCatalogueController {
    public SellingGamesCatalogueBean displaySellingGamesCatalogue(FiltersBean filtersBean) throws PersistencyErrorException, NoGameInCatalogueException, CheckFailedException {
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
    public List<String> getCategoriesValue(){
        return Filters.getCategories();
    }
    public List<String> getConsolesValue(){
        return Filters.getConsoles();
    }
}
