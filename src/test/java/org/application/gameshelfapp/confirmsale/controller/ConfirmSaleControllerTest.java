package org.application.gameshelfapp.confirmsale.controller;

import org.application.gameshelfapp.buyvideogames.controller.BuyGamesController;
import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConfirmSaleControllerTest {
    @Test
    void getSalesDifferentSalesTest(){      //In the Sale table there are tuples ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest'), ('2', 'nameTest2', 'gameNameTest2', '1', '15', 'consoleTest2', 'Confirmed', 'addressTest', 'emailTest')
        ConfirmSaleController controller = new ConfirmSaleController();
        try{
            List<SaleBean> sales = controller.getSales();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void confirmDeliveryTest(){     //In the Sale table there is tuple ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'fer.andrea35@gmail.com')
        ConfirmSaleController controller = new ConfirmSaleController();
        try{
            controller.confirmDelivery(1);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            assertEquals(1, (long) saleDAO.getConfirmedSales().size());
        } catch(PersistencyErrorException | ConfirmDeliveryException | WrongSaleException | GmailException e){
            fail();
        }
    }

    @Test
    void confirmDeliveryWrongSaleExceptionTest(){       //database was empty
        ConfirmSaleController controller = new ConfirmSaleController();
        assertThrows(WrongSaleException.class, () -> controller.confirmDelivery(2));
    }

    @Test
    void confirmedDeliveryWrongStateExceptionTest(){        //In the database there was tuple ('1', 'testName', 'gameName', '2', '22', 'platformTest', 'Comfirmed', 'addressTest', 'emailTest')
        ConfirmSaleController controller = new ConfirmSaleController();
        assertThrows(WrongSaleException.class, () -> controller.confirmDelivery(1));
    }
}
