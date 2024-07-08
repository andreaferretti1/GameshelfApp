package org.application.gameshelfapp.confirmsale.boundary2.adapter;

import org.application.gameshelfapp.confirmsale.bean.SaleBean;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.confirmsale.boundary2.adapter.SellerAdapter;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.dao.PersistencyAbstractFactory;
import org.application.gameshelfapp.login.exception.GmailException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SellerAdapterTest{
    @Test
    void getUserBeanTest(){
        SellerAdapter adapter = new SellerAdapter(new UserBean());
        assertNotNull(adapter.getUserBean());
    }

    @Test
    void getSalesToConfirmTest(){      /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'emailTest')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'emailTest2')*/
        SellerAdapter adapter = new SellerAdapter(new UserBean());
        try {
            List<SaleBean> sales = adapter.getSalesToConfirm();
            assertEquals(1, (long) sales.size());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void confirmSaleTest(){         /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'fer.andrea35@gmail.com')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'fer.andrea35@gmail.com')*/
        SellerAdapter adapter = new SellerAdapter(new UserBean());
        try{
            adapter.confirmSale("1");

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> salesConfirmed = saleDAO.getConfirmedSales();
            assertEquals(2, (long) salesConfirmed.size());
        } catch(PersistencyErrorException | GmailException | ConfirmDeliveryException | WrongSaleException e){
            fail();
        }
    }

    @Test
    void condirmSaleWrongSaleExceptionTest(){      //database was empty
        SellerAdapter adapter = new SellerAdapter(new UserBean());
        assertThrows(WrongSaleException.class, () -> adapter.confirmSale("1"));
    }
}
