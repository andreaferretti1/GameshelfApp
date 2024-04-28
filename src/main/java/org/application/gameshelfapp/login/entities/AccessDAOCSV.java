package org.application.gameshelfapp.login.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.util.Properties;


public class AccessDAOCSV implements AccessDAO{

    private final File fd;

    public AccessDAOCSV() throws PersistencyErrorException{

        try(FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){
            Properties properties = new Properties();

            properties.load(in);
            String s = properties.getProperty("CSV_ACCOUNTS");
            this.fd = new File(s);
        } catch(IOException e){
            throw new PersistencyErrorException("Couldn't access to accounts");
        }
    }

    @Override
    public synchronized void saveAccount(AccessThroughRegistration regAccess) throws PersistencyErrorException {
            String username = regAccess.getUsername();
            String email = regAccess.getEmail();
            String password = regAccess.getEncodedPassword();

            String[] instance = new String[4];


            instance[AccountAttributes.USERNAME.ordinal()] = username;
            instance[AccountAttributes.EMAIL.ordinal()] = email;
            instance[AccountAttributes.PASSWORD.ordinal()] = password;
            instance[AccountAttributes.TYPEOFUSER.ordinal()] = regAccess.getTypeOfUser();

            try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fd, true)));){
                csvWriter.writeNext(instance);
            } catch(IOException e){
                throw new PersistencyErrorException("Couldn't save account");
            }
    }


    @Override
    public Access retrieveAccount(Access access) throws PersistencyErrorException{
        String[] instance;
        Access user = null;

        String username = access.getUsername();
        String email = access.getEmail();

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));){
             while((instance = csvReader.readNext()) != null){
                    if(instance[AccountAttributes.EMAIL.ordinal()].equals(email) || instance[AccountAttributes.USERNAME.ordinal()].equals(username)){
                        user = new Access(instance[AccountAttributes.USERNAME.ordinal()], instance[AccountAttributes.EMAIL.ordinal()], null, instance[AccountAttributes.TYPEOFUSER.ordinal()]);
                        user.setEncodedPassword(instance[AccountAttributes.PASSWORD.ordinal()]);
                    }
             }
        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't retrieve account");
        }
        return user;
    }

    private enum AccountAttributes{
        USERNAME, EMAIL, PASSWORD, TYPEOFUSER;
    }
}
