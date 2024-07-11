package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessDAOCSVTest {

    @Test
    void retrieveAccountNameTest(){  //database was populated with tuple ('testName', 'testEmail', 'testPassword', 'Customer') before running the test
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail(new AccessThroughLogIn("testEmail", null,"Customer"));
            assertEquals("testName", access.getUsername());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void retrieveAccountEmailTest(){  //database was populated with tuple ('testName', 'testEmail', 'testPassword', 'Customer') before running the test
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail(new AccessThroughLogIn("testEmail", null,"Customer"));
            assertEquals("testEmail", access.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void retrieveAccountPasswordTest(){  //database was populated with tuple ('testName', 'testEmail', 'testPassword', 'Customer') before running the test
        try{
            Access testAccess = new AccessThroughLogIn("testEmail","testPassword" ,"Customer");
            testAccess.encodePassword();

            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail(testAccess);
            assertEquals(testAccess.getEncodedPassword(), access.getEncodedPassword());
        } catch(PersistencyErrorException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void retrieveAccountTypeTest(){  //database was populated with tuple ('testName', 'testEmail', 'testPassword', 'Customer') before running the test
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail(new AccessThroughLogIn("testEmail", null,"Customer"));
            assertEquals("Customer", access.getTypeOfUser());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void saveAccountUsernameTest(){
        try {
            AccessThroughRegistration regAccess = new AccessThroughRegistration("test", "testEmail@example.com", "test", "customer");
            regAccess.encodePassword();
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(regAccess);
            Access access = accessDAOCSV.retrieveAccountByEmail(regAccess);
            assertEquals("test", access.getUsername());
        } catch(NullPasswordException | PersistencyErrorException e){
            fail();
        }
    }
    @Test
    void saveAccountEmailTest(){
        try {
            AccessThroughRegistration regAccess = new AccessThroughRegistration("test", "testEmail@example.com", "test", "customer");
            regAccess.encodePassword();
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(regAccess);
            Access access = accessDAOCSV.retrieveAccountByEmail(regAccess);
            assertEquals("testEmail@example.com", access.getEmail());
        } catch(NullPasswordException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveAccountPasswordTest(){
        try {
            AccessThroughRegistration regAccess = new AccessThroughRegistration("test", "testEmail@example.com", "test", "customer");
            regAccess.encodePassword();
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(regAccess);
            Access access = accessDAOCSV.retrieveAccountByEmail(regAccess);
            assertEquals(regAccess.getEncodedPassword(), access.getEncodedPassword());
        } catch(NullPasswordException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void saveAccountTypeOfUserTest(){
        try {
            AccessThroughRegistration regAccess = new AccessThroughRegistration("test", "testEmail@example.com", "test", "customer");
            regAccess.encodePassword();
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(regAccess);
            Access access = accessDAOCSV.retrieveAccountByEmail(regAccess);
            assertEquals("customer", access.getTypeOfUser());
        } catch(NullPasswordException | PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void retrieveAccountReturnsNullTest(){
        try{
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
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
