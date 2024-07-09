package org.application.gameshelfapp.confirmsale.controller;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOCSVUtils;
import org.application.gameshelfapp.confirmsale.dao.utils.SaleDAOJDBCUtils;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.dao.utils.GetPersistencyTypeUtils;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConfirmSaleControllerTest {
    @AfterEach
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            SaleDAOCSVUtils.truncateFile();
        } else {
            SaleDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void getSalesDifferentSalesTest(){      //In the Sale table there are tuples ('1', 'nameTest1', 'gameNameTest1', '3', '10', 'consoleTest', 'To confirm', 'addressTest', 'emailTest'), ('2', 'nameTest2', 'gameNameTest2', '1', '15', 'consoleTest2', 'Confirmed', 'addressTest', 'emailTest')
        String[][] records = {{"3", "10", "To confirm", "gameNameTest1", "consoleTest", "nameTest1", "emailTest", "addressTest"}, {"1", "15", "Confirmed", "gameNameTest2", "consoleTest2", "nameTest2", "emailTest", "addressTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) SaleDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
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
        String[][] records = {{"3", "10", "To confirm", "gameNameTest1", "consoleTest", "nameTest1", "fer.andrea35@gmail.com", "addressTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) SaleDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
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
        String[][] records = {{"2", "22", "Confirmed", "gameName", "platformTest", "testName", "emailTest", "addressTest"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) SaleDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
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
