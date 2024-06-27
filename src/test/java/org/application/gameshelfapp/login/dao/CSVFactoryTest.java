package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVFactoryTest {
// getFile non è stato testato perchè viene chiamato ogni volta che creo un DAO.
    @Test
    void createItemDaotest(){
        try {
            CSVFactory csvFactory = new CSVFactory();
            csvFactory.createItemDAO();
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void createCatalogueDAOTest(){
        try{
            CSVFactory csvFactory = new CSVFactory();
            csvFactory.createCatalogueDAO();
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void createAccessDAOTest(){
        try{
            CSVFactory csvFactory = new CSVFactory();
            csvFactory.createAccessDAO();
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void createSaleDAOTest(){
        try{
            CSVFactory csvFactory = new CSVFactory();
            csvFactory.createSaleDAO();
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}