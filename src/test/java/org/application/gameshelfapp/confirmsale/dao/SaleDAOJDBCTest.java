package org.application.gameshelfapp.confirmsale.dao;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOJDBCUtils;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.dao.JDBCFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleDAOJDBCTest {

    @AfterEach
    void truncateTable(){
        SaleDAOJDBCUtils.truncateTable();
    }
    @Test
    void getToConfirmSalesArraySizeTest(){      //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try {
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void getToConfirmSalesIdTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals(1, sale1.getId());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void getToConfirmSalesNameTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("nameTest1", sale1.getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getToConfirmSalesGameNameTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("gameNameTest1", sale1.getVideogameSold().getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getToConfirmSalesCopiesTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals(2, sale1.getVideogameSold().getCopies());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getToConfirmSalesPriceTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals(11, sale1.getVideogameSold().getPrice());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getToConfirmSalesConsoleTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();

            assertEquals("consoleTest1", sale1.getVideogameSold().getPlatform());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getToConfirmSalesStateTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("To confirm", sale1.getState());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void getToConfirmSalesAddressTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("addressTest1", sale1.getAddress());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void getToConfirmSalesEmailTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("emailTest1", sale1.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getConfirmedSalesArraySizeTest(){      //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getConfirmedSales();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void getConfirmedSalesStateTest(){       //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "11", "To confirm", "gameNameTest1", "consoleTest1", "nameTest1", "emailTest1", "addressTest1"}, {"1", "10", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest2", "addressTest2"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getConfirmedSales();

            Sale sale = sales.getFirst();

            assertEquals("Confirmed", sale.getState());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void saveSaleIdTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals(1, sale1.getId());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleNameTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("nameTest", sale1.getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleGameNameTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("gameNameTest", sale1.getVideogameSold().getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleCopiesTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals(2, sale1.getVideogameSold().getCopies());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSalePriceTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals(10, sale1.getVideogameSold().getPrice());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleConsoleTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("consoleTest", sale1.getVideogameSold().getPlatform());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleStateTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("To confirm", sale1.getState());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleAddressTest(){
        try{
           JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("addressTest", sale1.getAddress());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleEmailTest(){
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Videogame gameSold = new Videogame("gameNameTest", 2, 10, "descriptionTest", "consoleTest", null);
            Sale sale = new Sale(0, gameSold, "emailTest", "addressTest", Sale.TO_CONFIRM, "nameTest");
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("emailTest", sale1.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void updateSaleTest(){      //In the database there was tuple ('1', 'nameTest', 'gameNameTest', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"3", "10", "To confirm", "gameNameTest", "consoleTest", "nameTest", "emailTest", "addressTest"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            saleDAO.updateSale(sales.getFirst());

            sales = saleDAO.getConfirmedSales();
            Sale sale = sales.getFirst();
            assertEquals("Confirmed", sale.getState());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getSaleToConfirmByIdTest(){     //In the database there was tuple (1, 'nameTest', 'gameName', '2', '40', 'platformTest', 'To confirm', 'addressTest', 'emailTest')
        SaleDAOJDBCUtils.insertRecord(new String[][]{{"2", "40", "To confirm", "gameNameTest", "consoleTest", "nameTest", "emailTest", "addressTest"}});
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            Sale sale = saleDAO.getSaleToConfirmById(1);
            assertNotNull(sale);
        } catch(PersistencyErrorException | WrongSaleException e){
            fail();
        }
    }

    @Test
    void getSaleToConfirmWrongSaleExceptionTest(){      //database was empty
        JDBCFactory jdbcFactory = new JDBCFactory();
        SaleDAO saleDAO = jdbcFactory.createSaleDAO();
        assertThrows(WrongSaleException.class, () -> saleDAO.getSaleToConfirmById(1));
    }
}