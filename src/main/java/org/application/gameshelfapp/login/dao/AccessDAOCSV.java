package org.application.gameshelfapp.login.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.entities.Access;
import org.application.gameshelfapp.login.entities.AccessThroughLogIn;
import org.application.gameshelfapp.login.entities.AccessThroughRegistration;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;


public class AccessDAOCSV implements AccessDAO {

    private final File fd;

    public AccessDAOCSV(File fd){
        this.fd = fd;
    }
    @Override
    public synchronized void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException {

        String[] tuple = new String[4];

        tuple[AccountAttributes.USERNAME.ordinal()] = regAccess.getUsername();
        tuple[AccountAttributes.EMAIL.ordinal()] = regAccess.getEmail();
        tuple[AccountAttributes.PASSWORD.ordinal()] = regAccess.getPassword();
        tuple[AccountAttributes.TYPE_OF_USER.ordinal()] = regAccess.getTypeOfUser();

        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fd, true)))){
            csvWriter.writeNext(tuple);
        } catch(IOException e){
            throw new PersistencyErrorException("Couldn't save account");
        }
    }


    @Override
    public Access retrieveAccountByEmail(Access access) throws PersistencyErrorException{
        String[] tuple;
        Access user = null;

        String email = access.getEmail();

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)))){
             while((tuple = csvReader.readNext()) != null){
                    if(tuple[AccountAttributes.EMAIL.ordinal()].equals(email)){
                        user = new AccessThroughLogIn(tuple[AccountAttributes.USERNAME.ordinal()], tuple[AccountAttributes.EMAIL.ordinal()], tuple[AccountAttributes.TYPE_OF_USER.ordinal()]);
                        user.setEncodedPassword(tuple[AccountAttributes.PASSWORD.ordinal()]);
                    }
             }
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't retrieve account");
        }
        return user;
    }

    @Override
    public void checkAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException, CheckFailedException {
        String[] tuple;

        String username = regAccess.getUsername();
        String email = regAccess.getEmail();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)))){
            while((tuple = csvReader.readNext()) != null){
                if(tuple[AccountAttributes.USERNAME.ordinal()].equals(username) || tuple[AccountAttributes.EMAIL.ordinal()].equals(email)){
                    throw new CheckFailedException("Username or email already existing");
                }
            }
        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't register your account");
        }
    }
    private enum AccountAttributes{
        USERNAME, EMAIL, PASSWORD, TYPE_OF_USER
    }
}
