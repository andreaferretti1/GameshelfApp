package org.application.gameshelfapp.login.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyAccountException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;


public class AccessDAOCSV implements AccessDAO{

    private final String filename;
    private final File fd;

    public AccessDAOCSV() {

        this.filename = "src/main/resources/org/application/gameshelfapp/persistency/accounts.csv";

        this.fd = new File(filename);
    }

    @Override
    public synchronized void saveAccount(TypeOfAccess type, Access access) throws PersistencyAccountException, IOException {
            String username = access.getUsername();
            String email = access.getEmail();
            String password = null;

            String[] instance = new String[3];

            CSVWriter csvWriter = null;

            instance[0] = username;
            instance[1] = email;
            instance[2] = password;

            try{
                csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fd)));
                csvWriter.writeNext(instance);
            } catch(IOException e){
                throw new PersistencyAccountException("Couldn't save account");
            }finally {
                if (csvWriter != null) {
                    csvWriter.close();
                }
            }

    }

    @Override
    public Access retrieveAccountByEmail(TypeOfAccess type, String email) throws IOException, PersistencyErrorException, PersistencyAccountException {
        String[] instance;
        CSVReader csvReader = null;
        Access access = null;

        try {
             csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
             while((instance = csvReader.readNext()) != null){
                    if(instance[1].equals(email)){
                        String username = instance[0];
                        String password = instance[3];
                        access = AccessFactory.createAccess(type, username, email, password);
                    }
             }
        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException(e.getMessage());
        } finally{
            if (csvReader != null) {
                csvReader.close();
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
