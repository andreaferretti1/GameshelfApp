package org.application.gameshelfapp.confirmsale.dao;

import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.login.dao.JDBCFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SaleDAOJDBCTest {

    @Test
    void getToConfirmSalesArraySizeTest(){      //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
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
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("nameTest1", sale1.getCredentials().getName());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getToConfirmSalesGameNameTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
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
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("addressTest1", sale1.getCredentials().getAddress());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void getToConfirmSalesEmailTest(){        //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();

            Sale sale1 = sales.getFirst();
            assertEquals("emailTest1", sale1.getCredentials().getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getConfirmedSalesArraySizeTest(){      //In the database there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '11', 'consoleTest1', 'To confirm', 'addressTest1', 'emailTest1'), ('2', 'nameTest2', 'gameNameTest2', '1', '10', 'consoleTest2', 'Confirmed', 'addressTest2', emailTest2')
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("nameTest", sale1.getCredentials().getName());
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
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
            Credentials credentials = new Credentials("nameTest", "addresTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("addressTest", sale1.getCredentials().getAddress());
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
            Credentials credentials = new Credentials("nameTest", "addressTest", "emailTest");
            Sale sale = new Sale(0, gameSold, credentials, Sale.TO_CONFIRM);
            saleDAO.saveSale(sale);

            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale1 = sales.getFirst();
            assertEquals("emailTest", sale1.getCredentials().getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveSaleConfirmedTest(){      //In the database there was tuple ('1', 'nameTest', 'gameNameTest', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest')
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            SaleDAO saleDAO = jdbcFactory.createSaleDAO();
            List<Sale> sales = saleDAO.getToConfirmSales();
            Sale sale = sales.getFirst();
            sale.confirm();
            saleDAO.saveSale(sale);

            sales = saleDAO.getConfirmedSales();
            sale = sales.getFirst();
            assertEquals("Confirmed", sale.getState());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}