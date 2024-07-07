package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.bean.SaleBean;
import org.application.gameshelfapp.buyvideogames.dao.SaleDAO;
import org.application.gameshelfapp.buyvideogames.entities.Sale;
import org.application.gameshelfapp.buyvideogames.exception.ConfirmDeliveryException;
import org.application.gameshelfapp.buyvideogames.exception.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerBoundaryTest {

    @Test
    void setAndGetSalesBeanTest(){
        SellerBoundary sellerBoundary = new SellerBoundary(new UserBean());
        sellerBoundary.setSalesBean(new ArrayList<>());
        assertNotNull(sellerBoundary.getSalesBean());
    }

    @Test
    void getUserBeanTest(){
        SellerBoundary sellerBoundary = new SellerBoundary(new UserBean());
        assertNotNull(sellerBoundary.getUserBean());
    }

    @Test
    void getGamesToSendTest(){      /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'emailTest')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'emailTest2')*/
        SellerBoundary sellerBoundary = new SellerBoundary(new UserBean());
        try {
            sellerBoundary.getGamesToSend();
            List<SaleBean> sales = sellerBoundary.getSalesBean();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void sendGameTest(){         /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'fer.andrea35@gmail.com')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'fer.andrea35@gmail.com')*/
        SellerBoundary sellerBoundary = new SellerBoundary(new UserBean());
        try{
            sellerBoundary.sendGame(1);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> salesConfirmed = saleDAO.getConfirmedSales();
            assertEquals(2, (long) salesConfirmed.size());
        } catch(PersistencyErrorException | GmailException | ConfirmDeliveryException | WrongSaleException e){
            fail();
        }
    }

    @Test
    void sendGameWrongSaleExceptionTest(){      //database was empty
        SellerBoundary boundary = new SellerBoundary(new UserBean());
        assertThrows(WrongSaleException.class, () -> boundary.sendGame(1));
    }

    @Test
    void sendGameWrongStateExceptionTest(){     //in the database there was tuple (1, 'nameTest', 'gameName', '2', '40', 'platformTest', 'Confirmed', 'addressTest', 'emailTest')
        SellerBoundary boundary = new SellerBoundary(new UserBean());
        assertThrows(WrongSaleException.class, () -> boundary.sendGame(1));
    }
}
