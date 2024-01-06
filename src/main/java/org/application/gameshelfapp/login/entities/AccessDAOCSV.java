package org.application.gameshelfapp.login.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyAccountException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.util.Properties;


public class AccessDAOCSV implements AccessDAO{

    private final String filename;
    private final File fd;

    public AccessDAOCSV() {

        this.filename = "src/main/resources/org/application/gameshelfapp/persistency/accounts.csv";

        this.fd = new File(filename);
    }

    @Override
    public void saveAccount(TypeOfAccess type, Access access) throws Exception {
            String username = access.getUsername();
            String email = access.getEmail();
            String password = null;

            String[] record = new String[3];

            CSVWriter csvWriter = null;

            record[0] = username;
            record[1] = email;
            record[2] = password;

            try{
                csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fd)));
                csvWriter.writeNext(record);
            } catch(IOException e){
                throw new Exception("Couldn't save account");
            }finally {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            }

    }

    @Override
    public Access retrieveAccountByEmail(TypeOfAccess type, String email) throws PersistencyErrorException, PersistencyAccountException {
        String[] record;
        CSVReader csvReader = null;
        Access access = null;

        try {
             csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
             while((record = csvReader.readNext()) != null){
                    if(record[1].equals(email)){
                        String username = record[0];
                        String password = record[3];
                        access = AccessFactory.createAccess(type, username, email, password);
                    }
             }
        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException(e.getMessage());
        } finally{
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
            } catch(IOException e){
                throw new RuntimeException();
            }
        }




        if(access == null && type == TypeOfAccess.LOGIN){
            throw new PersistencyAccountException("There isn't such account");
        } else if(access != null && type == TypeOfAccess.REGISTRATION){
            throw new PersistencyAccountException("Account already exists");
        }
        return access;
    }

}
