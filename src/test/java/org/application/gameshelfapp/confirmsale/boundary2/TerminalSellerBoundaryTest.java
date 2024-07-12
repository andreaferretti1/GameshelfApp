package org.application.gameshelfapp.confirmsale.boundary2;

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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TerminalSellerBoundaryTest {
    @Test
    void getUserBeanTest(){
        try {
            TerminalSellerBoundary boundary = new TerminalSellerBoundary(new UserBean());
            assertNotNull(boundary.getUserBean());
        } catch (WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandGetSalesToConfirmTest(){      /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'emailTest')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'emailTest2')*/
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerBoundary boundary = new TerminalSellerBoundary(userBean);

            String[] command = {"see sales"};
            String expectedString = String.format("Id: %d, game name: %s, platform: %s copies: %d, price: %f€, user name: %s, user email: %s, delivery address: %s%n", 1, "gameNameTest1", "platform1", 2, 40f, "nameTest1", "emailTest", "address") + "\n\n<Type confirm gameId>\n";
            String returnedString = boundary.executeCommand(command);
            assertEquals(expectedString, returnedString);
        } catch(PersistencyErrorException | ConfirmDeliveryException | GmailException | WrongSaleException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandConfirmSaleTest(){         /*In the Sale table there were tuples ('1', 'nameTest1', 'gameNameTest1', '2', '40', 'platform1', 'To confirm', 'address', 'fer.andrea35@gmail.com')
                                      ('2', 'nameTest2', 'gameNameTest2', '1', '20', 'platform2', 'Confirmed', 'address2', 'fer.andrea35@gmail.com')*/
        try{
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerBoundary boundary = new TerminalSellerBoundary(userBean);

            String[] command = {"confirm", "1"};
            boundary.executeCommand(command);

            SaleDAO saleDAO = PersistencyAbstractFactory.getFactory().createSaleDAO();
            List<Sale> salesConfirmed = saleDAO.getConfirmedSales();
            assertEquals(2, (long) salesConfirmed.size());
        } catch(PersistencyErrorException | GmailException | ConfirmDeliveryException | WrongSaleException | WrongUserTypeException e){
            fail();
        }
    }

    @Test
    void executeCommandConfirmSaleWrongSaleExceptionTest(){      //database was empty
        try {
            UserBean userBean = new UserBean();
            userBean.setTypeOfUser("Seller");
            TerminalSellerBoundary boundary = new TerminalSellerBoundary(userBean);
            String[] command = {"confirm", "1"};
            assertThrows(WrongSaleException.class, () -> boundary.executeCommand(command));
        } catch (WrongUserTypeException e){
            fail();
        }
    }
}
