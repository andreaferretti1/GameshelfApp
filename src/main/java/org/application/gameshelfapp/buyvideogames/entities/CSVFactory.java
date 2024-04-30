package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.entities.AccessDAO;
import org.application.gameshelfapp.login.entities.AccessDAOCSV;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

public class CSVFactory extends PersistencyAbstractFactory{

    public static final String PROPERTIES = "src/main/resources/org/application/gameshelfapp/configuration/configuration.properties";
    @Override
    public ItemDAO createItemDAO() throws PersistencyErrorException{
        return new ItemDAOCSV(this.getFile("CSV_GAMES_ON_SALE"));
    }

    @Override
    public CatalogueDAO createCatalogueDAO() throws PersistencyErrorException{
        return new CatalogueDAOCSV(this.getFile("CSV_CATALOGUE"));
    }
    @Override
    public AccessDAO createAccessDAO() throws PersistencyErrorException {
        return new AccessDAOCSV(this.getFile("CSV_ACCOUNTS"));
    }

    @Override
    public SaleDAO createSaleDAO() throws PersistencyErrorException {
        return new SaleDAOCSV(this.getFile("CSV_GAMES_SOLD"));
    }

    private File getFile(String key) throws PersistencyErrorException{
        try(FileInputStream in = new FileInputStream(CSVFactory.PROPERTIES)){
            Properties properties = new Properties();

            properties.load(in);
            String s = properties.getProperty(key);
            return new File(s);
        } catch(IOException e){
            throw new PersistencyErrorException("Couldn't access to catalogue");
        }
    }
}
