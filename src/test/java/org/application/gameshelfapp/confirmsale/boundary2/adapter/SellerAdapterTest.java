package org.application.gameshelfapp.confirmsale.boundary2.adapter;

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
import org.application.gameshelfapp.login.exception.WrongUserTypeException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerAdapterTest{

    @AfterEach
    void clean(){
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")){
            SaleDAOCSVUtils.truncateFile();
        } else {
            SaleDAOJDBCUtils.truncateTable();
        }
    }
    @Test
    void getUserBeanTest(){
        try {
            SellerAdapter adapter = new SellerAdapter(new UserBean());
            assertNotNull(adapter.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void getSalesToConfirmTest(){      /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'emailTest')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'emailTest2')*/
        String[][] records = {{"2", "40", "To confirm", "gameNameTest1", "platform1", "nameTest1", "emailTest", "address"}, {"1", "20", "Confirmed", "gameNameTest2", "platform2", "nameTest2", "emailTest2", "address2"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) SaleDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAdapter adapter = new SellerAdapter(userBean);

            List<SaleBean> sales = adapter.getSalesToConfirm();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void confirmSaleTest(){         /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'fer.andrea35@gmail.com')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'fer.andrea35@gmail.com')*/
        String[][] records = {{"2", "40", "To confirm", "gameNameTest1", "platform1", "nameTest1", "fer.andrea35@gmail.com", "address"}, {"1", "20", "Confirmed", "gameNameTest2", "platform2", "nameTest2", "fer.andrea35@gmail.com", "address2"}};
        if(GetPersistencyTypeUtils.getPersistencyType().equals("CSV")) SaleDAOCSVUtils.insertRecord(records);
        else SaleDAOJDBCUtils.insertRecord(records);
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            SellerAdapter adapter = new SellerAdapter(userBean);

            adapter.confirmSale("1");

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> salesConfirmed = saleDAO.getConfirmedSales();
            assertEquals(2, (long) salesConfirmed.size());
        } catch(PersistencyErrorException | GmailException | ConfirmDeliveryException | WrongSaleException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void confirmSaleWrongSaleExceptionTest(){      //database was empty
        try {
            SellerAdapter adapter = new SellerAdapter(new UserBean());
            assertThrows(WrongSaleException.class, () -> adapter.confirmSale("1"));
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}
