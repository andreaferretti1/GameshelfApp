package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.registration.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccessDAOJDBCTest {

    @Test
    void retrieveAccountNameTest(){  //database was populated with tuple ('testName', 'testEmail', 'testPassword', 'Customer') before running the test
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
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
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
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
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
            testAccess.encodePassword();
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
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
            assertEquals("Customer", access.getTypeOfUser());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test
    void retrieveAccountReturnsNullTest(){
        try{
            AccessDAOJDBC accessDAOJDBC = new AccessDAOJDBC();
            Access access = accessDAOJDBC.retrieveAccountByEmail("testEmail");
            assertNull(access);
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test   //this test was taken after testing "retrieveAccount" method, so that any failure was due to saveAccount method.
    void saveAccountNameTest(){
        try {
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            AccessThroughRegistration registration = new AccessThroughRegistration("testName", "testEmail", "passwordTest", "Customer");
            registration.encodePassword();
            accessDAO.saveAccount(registration);
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
            assertEquals("testName", access.getUsername());
        } catch(PersistencyErrorException |NullPasswordException e){
            fail();
        }
    }

    @Test   //this test was taken after testing "retrieveAccount" method, so that any failure was due to saveAccount method.
    void saveAccountEmailTest(){
        try {
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            AccessThroughRegistration registration = new AccessThroughRegistration("testName", "testEmail", "passwordTest", "Customer");
            registration.encodePassword();
            accessDAO.saveAccount(registration);
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
            assertEquals("testEmail", access.getEmail());
        } catch(PersistencyErrorException | NullPasswordException e){
            fail();
        }
    }

    @Test   //this test was taken after testing "retrieveAccount" method, so that any failure was due to saveAccount method.
    void saveAccountPasswordTest(){
        try {
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            AccessThroughRegistration registration = new AccessThroughRegistration("testName", "testEmail", "passwordTest", "Customer");
            registration.encodePassword();
            accessDAO.saveAccount(registration);
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
            assertEquals(registration.getEncodedPassword(), access.getEncodedPassword());
        } catch(PersistencyErrorException | NullPasswordException e){
            fail();
        }
    }

    @Test   //this test was taken after testing "retrieveAccount" method, so that any failure was due to saveAccount method.
    void saveAccountTypeTest(){
        try {
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO();
            AccessThroughRegistration registration = new AccessThroughRegistration("testName", "testEmail", "passwordTest", "Customer");
            registration.encodePassword();
            accessDAO.saveAccount(registration);
            Access access = accessDAO.retrieveAccountByEmail("testEmail");
            assertEquals("Customer", access.getTypeOfUser());
        } catch(PersistencyErrorException | NullPasswordException e){
            fail();
        }
    }

    @Test
    void getRandomCustomersTest(){             //In database there are one customer, one seller and one admin
        try{
            JDBCFactory jdbcFactory = new JDBCFactory();
            AccessDAO accessDAO = jdbcFactory.createAccessDAO(); List<Access> testAccess = accessDAO.getRandomCustomers();
            for(Access acc: testAccess){
                assertEquals("Customer",acc.getTypeOfUser());
            }
        } catch (PersistencyErrorException e){
            fail();
        }
    }
}