package org.application.gameshelfapp.confirmsale.boundary;

import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.SingletonConnectionPool;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerBoundaryTest {

//tests written by Andrea Ferretti. Tests were executed using JDBC DAO. To run these tests, 'PERSISTENCE' key in resources/configuration/configuration.properties file was set to 'JDBC' value

    @AfterEach
    void cleanUp(){
        try {
            String query1 = "TRUNCATE TABLE Sale;";
            Connection connection = SingletonConnectionPool.getInstance().getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query1);
        } catch(PersistencyErrorException | SQLException e){
            fail();
        }
    }

    @Test
    void getSalesBeanTest(){
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerBoundary sellerBoundary = new SellerBoundary(userBean);
            sellerBoundary.setSalesBean(new ArrayList<>());
            assertNotNull(sellerBoundary.getSalesBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getUserBeanTest(){
        try{
            SellerBoundary sellerBoundary = new SellerBoundary(new UserBean());
            assertNotNull(sellerBoundary.getUserBean());
        }catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getGamesToSendTest(){      //In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'emailTest')

        try{
            Videogame videogame = new Videogame("gameNameTest1", 2, 40, null, "platform1", null);
            Credentials credentials = new Credentials("nameTest1", "address", "emailTest");
            Sale sale = new Sale(videogame, credentials, Sale.TO_CONFIRM);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            saleDAO.saveSale(sale);

            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerBoundary sellerBoundary = new SellerBoundary(userBean);

            sellerBoundary.getGamesToSend();
            List<SaleBean> sales = sellerBoundary.getSalesBean();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void sendGameTest(){         //In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'fer.andrea35@gmail.com')
        try{
            Videogame videogame = new Videogame("gameNameTest1", 2, 40, null, "platform1", null);
            Credentials credentials = new Credentials("nameTest1", "address", "fer.andrea35@gmail.com");
            Sale sale = new Sale(videogame, credentials, Sale.TO_CONFIRM);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            saleDAO.saveSale(sale);

            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerBoundary sellerBoundary = new SellerBoundary(userBean);

            sellerBoundary.sendGame(1);

            saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> salesConfirmed = saleDAO.getConfirmedSales();
            assertEquals(1, (long) salesConfirmed.size());
        } catch(PersistencyErrorException | GmailException | ConfirmDeliveryException | WrongSaleException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void sendGameWrongSaleExceptionTest(){      //database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerBoundary boundary = new SellerBoundary(userBean);
            assertThrows(WrongSaleException.class, () -> boundary.sendGame(1));
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}
