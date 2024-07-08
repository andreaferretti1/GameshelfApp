package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.buyvideogames.dao.*;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.dao.SaleDAOJDBC;

public class JDBCFactory extends PersistencyAbstractFactory {
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
    public SaleDAO createSaleDAO(){
        return new SaleDAOJDBC();
    }
}
