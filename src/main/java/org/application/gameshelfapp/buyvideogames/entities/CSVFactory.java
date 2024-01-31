package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.entities.AccessDAO;
import org.application.gameshelfapp.login.entities.AccessDAOCSV;

public class CSVFactory extends PersistencyAbstractFactory{

    @Override
    public ItemDAO createItemDAO(){
        return new ItemDAOCSV();
    }

    @Override
    public CatalogueDAO createCatalogueDAO(){
        return new CatalogueDAOCSV();
    }
    @Override
    public AccessDAO createAccessDAO(){
        return new AccessDAOCSV();
    }
}
