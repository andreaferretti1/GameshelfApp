package org.application.gameshelfapp.confirmsale.boundary;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerBoundaryTest {

    @Test
    void setAndGetSalesBeanTest(){
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
        try {
            SellerBoundary sellerBoundary = new SellerBoundary(new UserBean());
            assertNotNull(sellerBoundary.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getGamesToSendTest(){      /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'emailTest')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'emailTest2')*/
        try{
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
    void sendGameTest(){         /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'fer.andrea35@gmail.com')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'fer.andrea35@gmail.com')*/
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerBoundary sellerBoundary = new SellerBoundary(userBean);

            sellerBoundary.sendGame(1);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> salesConfirmed = saleDAO.getConfirmedSales();
            assertEquals(2, (long) salesConfirmed.size());
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
