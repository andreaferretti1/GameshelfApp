package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessDAOCSVTest {

    @Test
    void saveAccountTest(){
        try {
            AccessThroughRegistration regAccess = new AccessThroughRegistration("test", "testEmail@example.com", "test", "customer");
            regAccess.encodePassword();
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(regAccess);
            Access access = accessDAOCSV.retrieveAccountByEmail(regAccess);
            assertEquals("test", access.getUsername());
            assertEquals("testEmail@example.com", access.getEmail());
            assertEquals(regAccess.getEncodedPassword(), access.getEncodedPassword());
            assertEquals("customer", access.getTypeOfUser());
        } catch(NullPasswordException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void retrieveAccountReturnsNullTest(){
        try{
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName1", "testEmail1@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName2", "testEmail2@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName3", "testEmail3@example.com", null, null));
            Access access = accessDAOCSV.retrieveAccountByEmail(new AccessThroughLogIn("testName", "testEmail@example.com", null));
            assertNull(access);
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test  //test executed with the same values inserted in retrieveAccountReturnsNullTest
    void retrieveAccountTest(){
        try{
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName1", "testEmail1@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName2", "testEmail2@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName3", "testEmail3@example.com", null, null));
            Access access = accessDAOCSV.retrieveAccountByEmail(new AccessThroughLogIn("testName2", "testEmail2@example.com", null));
            assertNotNull(access);
            assertEquals("testEmail2@example.com", access.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void getRandomCustomersTest(){      //In database there are one customer, one seller and one admin
        try{
            CSVFactory csvFactory = new CSVFactory();
            AccessDAO accessDAO = csvFactory.createAccessDAO();
            List<Access> testAccess = accessDAO.getRandomCustomers();
            for(Access acc: testAccess){
                assertEquals("Customer",acc.getTypeOfUser());
            }
        } catch (PersistencyErrorException e){
            fail();
        }
    }
}
