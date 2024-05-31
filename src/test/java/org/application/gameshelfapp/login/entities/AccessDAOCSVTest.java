package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class AccessDAOCSVTest {

    @Test
    void saveAccountTest(){
        try {
            AccessThroughRegistration regAccess = new AccessThroughRegistration("test", "testEmail@example.com", "test", "customer");
            regAccess.encodePassword();
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(regAccess);
            Access access = accessDAOCSV.retrieveAccount(regAccess);
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
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName1", "testEmail1@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName2", "testEmail2@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName3", "testEmail3@example.com", null, null));
            Access access = accessDAOCSV.retrieveAccount(new Access("testName", "testEmail@example.com", null, null));
            assertNull(access);
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test  //test executed with the same values inserted in retrieveAccountReturnsNullTest
    void retrieveAccountWithSameEmailTest(){
        try{
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName1", "testEmail1@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName2", "testEmail2@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName3", "testEmail3@example.com", null, null));
            Access access = accessDAOCSV.retrieveAccount(new Access("testName", "testEmail2@example.com", null, null));
            assertNotNull(access);
            assertEquals("testEmail2@example.com", access.getEmail());
        } catch(PersistencyErrorException e){
            fail();
        }
    }

    @Test       //test executed with the same values inserted in retrieveAccountReturnsNullTest
    void retrieveAccountWithSameUsernameTest(){
        try{
            AccessDAOCSV accessDAOCSV = new AccessDAOCSV(new File("/org/application/gameshelfapp/persistency/FileSystem/accounts.csv"));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName1", "testEmail1@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testName2", "testEmail2@example.com", null, null));
            accessDAOCSV.saveAccount(new AccessThroughRegistration("testNAme3", "testEmail3@example.com", null, null));
            Access access = accessDAOCSV.retrieveAccount(new Access("testName2", "testEmail@example.com", null, null));
            assertNotNull(access);
            assertEquals("testName2", access.getUsername());
        } catch(PersistencyErrorException e){
            fail();
        }
    }
}
