package org.application.gameshelfapp.confirmsale.controller;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmSaleControllerTest {
    @Test
    void getSalesDifferentSalesTest(){      //In the Sale table there are tuples ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest'), ('2', 'nameTest2', 'gameNameTest2', '1', '15', 'consoleTest2', 'Confirmed', 'addressTest', 'emailTest')
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            ConfirmSaleController controller = new ConfirmSaleController(userBean);

            List<SaleBean> sales = controller.getSales();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void confirmDeliveryTest(){     //In the Sale table there is tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'fer.andrea35@gmail.com')
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            ConfirmSaleController controller = new ConfirmSaleController(userBean);

            controller.confirmDelivery(1);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            assertEquals(1, (long) saleDAO.getConfirmedSales().size());
        } catch(PersistencyErrorException | ConfirmDeliveryException | WrongSaleException | GmailException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void confirmDeliveryWrongSaleExceptionTest(){       //database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            ConfirmSaleController controller = new ConfirmSaleController(userBean);
            assertThrows(WrongSaleException.class, () -> controller.confirmDelivery(2));
        } catch(WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void confirmedDeliveryWrongStateExceptionTest(){        //In the database there was tuple ('1', 'testName', 'gameName', '2', '22', 'platformTest', 'Comfirmed', 'addressTest', 'emailTest')
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            ConfirmSaleController controller = new ConfirmSaleController(userBean);
            assertThrows(WrongSaleException.class, () -> controller.confirmDelivery(1));
        } catch(WrongUserTypeException e){
            fail();
        }
    }
}
