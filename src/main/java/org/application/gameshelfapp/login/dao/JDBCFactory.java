package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.buyvideogames.dao.*;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.dao.SaleDAOJDBC;
import org.application.gameshelfapp.sellvideogames.dao.CategoryDAO;
import org.application.gameshelfapp.sellvideogames.dao.CategoryDAOJDBC;
import org.application.gameshelfapp.sellvideogames.dao.ConsoleDAO;
import org.application.gameshelfapp.sellvideogames.dao.ConsoleDAOJDBC;

public class JDBCFactory extends PersistencyAbstractFactory {
    @Override
    public ItemDAO createItemDAO(){
        return new ItemDAOJDBC();
    }
    @Override
    public AccessDAO createAccessDAO(){
        return new AccessDAOJDBC();
    }
    @Override
    public SaleDAO createSaleDAO(){
        return new SaleDAOJDBC();
    }
    @Override
    public CategoryDAO createCategoryDAO() { return new CategoryDAOJDBC();}
    @Override
    public ConsoleDAO createConsoleDAO() { return new ConsoleDAOJDBC();}
}
