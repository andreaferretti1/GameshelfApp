package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogueDAOCSVTest {

    @Test
    void getVideogameTest(){        //In the database there was tuple ('emailTest', 'nameTest', 3)
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            List<Videogame> games = catalogueDAO.getV;
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void addVideogameTest(){
        try{
            CSVFactory csvFactory = new CSVFactory();
            CatalogueDAO catalogueDAO = csvFactory.createCatalogueDAO();
            Videogame videogame = new Videogame()
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}