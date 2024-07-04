package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.WrongSaleException;
import org.application.gameshelfapp.login.dao.CSVFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleDAOCSVTest {

    @Test
    void getToConfirmSalesTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        try{
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            assertEquals(1, (long) sales.size());

            Sale sale1 = sales.getFirst();
            assertEquals(1, sale1.getId());
            assertEquals("nameTest1", sale1.getName());
            assertEquals("gameNameTest1", sale1.getVideogameSold().getName());
            assertEquals(2, sale1.getVideogameSold().getCopies());
            assertEquals(11, sale1.getVideogameSold().getPrice());
            assertEquals("consoleTest1", sale1.getVideogameSold().getPlatform());
            assertEquals("To confirm", sale1.getState());
            assertEquals("addressTest1", sale1.getAddress());
            assertEquals("emailTest1", sale1.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getConfirmedSalesTest(){       //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        try{
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getConfirmedSales();
            assertEquals(1, (long) sales.size());

            Sale sale = sales.getFirst();
            assertEquals(2, sale.getId());
            assertEquals("nameTest2", sale.getName());
            assertEquals("gameNameTest2", sale.getVideogameSold().getName());
            assertEquals(1, sale.getVideogameSold().getCopies());
            assertEquals(10, sale.getVideogameSold().getPrice());
            assertEquals("consoleTest2", sale.getName());
            assertEquals("Confirmed", sale.getState());
            assertEquals("addresTest2", sale.getAddress());
            assertEquals("emailTest2", sale.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void saveSaleTest(){
        try{
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            assertEquals(1, (long) sales.size());
            Sale sale1 = sales.getFirst();
            assertEquals(1, sale1.getId());
            assertEquals("nameTest", sale1.getName());
            assertEquals("gameNameTest", sale1.getVideogameSold().getName());
            assertEquals(2, sale1.getVideogameSold().getCopies());
            assertEquals(10, sale1.getVideogameSold().getPrice());
            assertEquals("consoleTest", sale1.getVideogameSold().getPlatform());
            assertEquals("To confirm", sale1.getState());
            assertEquals("addressTest", sale1.getAddress());
            assertEquals("emailTest", sale1.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleSameGameTest(){        //In the database there was tuple ('1', 'nameTest', 'gameNameTest', '2', '11', 'consoleTest', 'To confirm', 'addressTest1', 'emailTest1')
        try{
            Videogame videogame = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(videogame, "emailTest2", "addressTest2", Sale.TO_CONFIRM, "nameTest");
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getConfirmedSales();
            assertEquals(1, (long) sales.size());
            Sale sale1 = sales.getFirst();
            assertEquals(1, sale1.getId());
            assertEquals("gameNameTest", sale1.getVideogameSold().getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void updateSaleTest(){      //In the database there was tuple ('1', 'nameTest', 'gameNameTest', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest')
        try{
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            saleDAO.updateSale(sales.getFirst());

            sales = saleDAO.getConfirmedSales();
            Sale sale = sales.getFirst();
            assertEquals(1, sale.getId());
            assertEquals("gameNameTest", sale.getVideogameSold().getName());
            assertEquals("Confirmed", sale.getState());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getSaleToConfirmByStateTest(){     //In the database there was tuple (1, 'nameTest', 'gameName', '2', '40', 'platformTest', 'To confirm', 'addressTest', 'emailTest')
        try{
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            Sale sale = saleDAO.getSaleToConfirmById(1);
            assertNotNull(sale);
        } catch(PersistencyErrorException | WrongSaleException e){
            fail();
        }
    }

    @Test
    void getSaleToConfirmWrongSaleExceptionTest(){      //database was empty
        try {
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            assertThrows(WrongSaleException.class, () -> saleDAO.getSaleToConfirmById(1));
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getSaleToConfirmWrongStateExceptionTest(){     //in the database there was tuple (1, 'nameTest', 'gameName', '2', '40', 'platformTest', 'Confirmed', 'addressTest', 'emailTest')
        try{
            CSVFactory csvFactory = new CSVFactory();
            SaleDAO saleDAO = csvFactory.createSaleDAO();
            assertThrows(WrongSaleException.class, () -> saleDAO.getSaleToConfirmById(1));
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}