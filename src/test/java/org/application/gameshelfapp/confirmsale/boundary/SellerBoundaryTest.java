package org.application.gameshelfapp.confirmsale.boundary;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOCSVUtils;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOJDBCUtils;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerBoundaryTest {
    @AfterEach
    void clean(){
        if (GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            SaleDAOCSVUtils.truncateFile();
        } else {
            SaleDAOJDBCUtils.truncateTable();
        }
    }

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
        String[][] records = {{"2", "40", "To confirm", "gameNameTest1", "platform1", "nametest1", "emailTest", "address"}, {"1", "20", "Confirmed", "gameNameTest2", "platform2", "nameTest2", "emailTest2", "address2"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) SaleDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
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
        String[][] records = {{"2", "40", "To confirm", "gameNameTest1", "platform1", "nametest1", "fer.andrea35@gmail.com", "address"}, {"1", "20", "Confirmed", "gameNameTest2", "platform2", "nameTest2", "fer.andrea35@gmail.com", "address2"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) SaleDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
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
}
