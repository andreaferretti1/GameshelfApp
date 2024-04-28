package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.entities.AccessDAO;
import org.application.gameshelfapp.login.entities.AccessDAOCSV;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class CSVFactory extends PersistencyAbstractFactory{

    public static final String PROPERTIES = "src/main/resources/org/application/gameshelfapp/configuration/configuration.properties";
    @Override
    public ItemDAO createItemDAO() throws PersistencyErrorException{
        return new ItemDAOCSV();
    }

    @Override
    public CatalogueDAO createCatalogueDAO() throws PersistencyErrorException{
        return new CatalogueDAOCSV();
    }
    @Override
    public AccessDAO createAccessDAO() throws PersistencyErrorException {
        return new AccessDAOCSV();
    }

    @Override
    public SaleDAO createSaleDAO() throws PersistencyErrorException {
        return new SaleDAOCSV();
    }
}
