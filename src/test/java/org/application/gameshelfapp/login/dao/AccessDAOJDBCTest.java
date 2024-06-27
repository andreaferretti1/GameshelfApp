package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccessDAOJDBCTest {
    //tests were taken with empty database
    @Test
    void retrieveAccountTest(){  //database was populated with tuple ('testName', 'testEmail', 'testPassword', 'Customer') before running the test
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail(new AccessThroughLogIn("testName", "testEmail","Customer"));
            access.setEncodedPassword("testPassword");
            assertEquals("testName", access.getUsername());
            assertEquals("testEmail", access.getEmail());
            assertEquals("testPassword", access.getEncodedPassword());
            assertEquals("Customer", access.getTypeOfUser());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void retrieveAccountReturnsNullTest(){
        try{
            AccessDAOJDBC accessDAOJDBC = new AccessDAOJDBC();
            accessDAOJDBC.saveAccount(new AccessThroughRegistration("testName1", "testEmail1@example.com", null, null));
            accessDAOJDBC.saveAccount(new AccessThroughRegistration("testName2", "testEmail2@example.com", null, null));
            accessDAOJDBC.saveAccount(new AccessThroughRegistration("testName3", "testEmail3@example.com", null, null));
            Access access = accessDAOJDBC.retrieveAccountByEmail(new AccessThroughLogIn("testName", "testEmail@example.com", null));
            assertNull(access);
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test   //this test was taken after testing "retrieveAccount" method, so that any failure was due to saveAccount method.
    void saveAccountTest(){
        try {
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            AccessThroughRegistration registration = new AccessThroughRegistration("testName", "testEmail", "passwordTest", "Customer");
            accessDAO.saveAccount(registration);
            Access access = accessDAO.retrieveAccountByEmail(registration);
            assertEquals("testName", access.getUsername());
            assertEquals("testEmail", access.getEmail());
            assertEquals("passwordTest", access.getEncodedPassword());
            assertEquals("Customer", access.getTypeOfUser());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}