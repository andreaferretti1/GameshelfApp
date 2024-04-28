package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.entities.AccessDAO;
import org.application.gameshelfapp.login.entities.AccessDAOJDBC;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public class JDBCFactory extends PersistencyAbstractFactory{

    @Override
    public ItemDAO createItemDAO(){
        return new ItemDAOJDBC();
    }
    @Override
    public CatalogueDAO createCatalogueDAO(){
        return new CatalogueDAOJDBC();
    }
    @Override
    public AccessDAO createAccessDAO(){
        return new AccessDAOJDBC();
    }

    @Override
    public SaleDAO createSaleDAO() throws PersistencyErrorException {
        return new SaleDAOJDBC();
    }
}
