package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleDAOJDBCTest {
    @Test
    void getSalesTest(){        //In the database there were tuples ('1', 'nameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getSales();
            assertEquals(2, (long) sales.size());

            Sale sale1 = sales.getFirst();
            assertEquals(1, sale1.getId());
            assertEquals("nameTest1", sale1.getVideogameSold().getName());
            assertEquals(2, sale1.getVideogameSold().getCopies());
            assertEquals(11, sale1.getVideogameSold().getPrice());
            assertEquals("consoleTest1", sale1.getPlatform());
            assertEquals("To confirm", sale1.getState());
            assertEquals("addressTest1", sale1.getAddress());
            assertEquals("emailTest1", sale1.getEmail());

            Sale sale2 = sales.getLast();
            assertEquals(2, sale2.getId());
            assertEquals("nameTest2", sale2.getVideogameSold().getName());
            assertEquals(1, sale2.getVideogameSold().getCopies());
            assertEquals(10, sale2.getVideogameSold().getPrice());
            assertEquals("consoleTest2", sale2.getPlatform());
            assertEquals("Confirmed", sale2.getState());
            assertEquals("addresTest2", sale2.getAddress());
            assertEquals("emailTest2", sale2.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void saveSaleTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("nameTest", 2, 10, "descriptionTest");
            Sale sale = new Sale(gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "consoleTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getSales();
            assertEquals(1, (long) sales.size());
            Sale sale1 = sales.getFirst();
            assertEquals(1, sale1.getId());
            assertEquals("nameTest", sale1.getVideogameSold().getName());
            assertEquals(2, sale1.getVideogameSold().getCopies());
            assertEquals(10, sale1.getVideogameSold().getPrice());
            assertEquals("consoleTest", sale1.getPlatform());
            assertEquals("To confirm", sale1.getState());
            assertEquals("addressTest", sale1.getAddress());
            assertEquals("emailTest", sale1.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleSameGameTest(){        //In the database there was tuple ('1', 'nameTest', '2', '11', 'consoleTest', 'To confirm', 'addressTest1', 'emailTest1')
        try{
            Videogame videogame = new Videogame("nameTest", 2, 10, "descriptionTest");
            Sale sale = new Sale(videogame, "emailTest2", "addressTest2", Sale.TO_CONFIRM, "consoleTest");
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getSales();
            assertEquals(2, (long) sales.size());
            Sale sale1 = sales.getLast();
            assertEquals(2, sale1.getId());
            assertEquals("nameTest", sale1.getVideogameSold().getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void updateSaleTest(){      //In the database there was tuple ('1', 'nameTest', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest')
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getSales();
            saleDAO.updateSale(sales.getFirst());

            sales = saleDAO.getSales();
            Sale sale = sales.getFirst();
            assertEquals(1, sale.getId());
            assertEquals("nameTest", sale.getVideogameSold().getName());
            assertEquals("Confirmed", sale.getState());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}