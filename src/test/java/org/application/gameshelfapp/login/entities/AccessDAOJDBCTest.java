package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.buyvideogames.entities.JDBCFactory;
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
            Access access = accessDAO.retrieveAccount(new Access("testName", "testEmail", "testPAssword","Customer"));
            assertEquals("testName", access.getUsername());
            assertEquals("testEmail", access.getEmail());
            assertEquals("testPassword", access.getEncodedPassword());
            assertEquals("Customer", access.getTypeOfUser());
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
            Access access = accessDAO.retrieveAccount(registration);
            assertEquals("testName", access.getUsername());
            assertEquals("testEmail", access.getEmail());
            assertEquals("passwordTest", access.getEncodedPassword());
            assertEquals("Customer", access.getTypeOfUser());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}