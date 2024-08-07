package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.buyvideogames.dao.*;
import org.application.gameshelfapp.confirmsale.dao.SaleDAO;
import org.application.gameshelfapp.confirmsale.dao.SaleDAOCSV;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.dao.CategoryDAO;
import org.application.gameshelfapp.sellvideogames.dao.CategoryDAOCSV;
import org.application.gameshelfapp.sellvideogames.dao.ConsoleDAO;
import org.application.gameshelfapp.sellvideogames.dao.ConsoleDAOCSV;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CSVFactory extends PersistencyAbstractFactory {

    public static final String PROPERTIES = "src/main/resources/org/application/gameshelfapp/configuration/configuration.properties";
    @Override
    public ItemDAO createItemDAO() throws PersistencyErrorException{
        return new ItemDAOCSV(this.getFile("CSV_GAMES_ON_SALE"));
    }
    @Override
    public AccessDAO createAccessDAO() throws PersistencyErrorException {
        return new AccessDAOCSV(this.getFile("CSV_ACCOUNTS"));
    }

    @Override
    public SaleDAO createSaleDAO() throws PersistencyErrorException {
        return new SaleDAOCSV(this.getFile("CSV_GAMES_SOLD"));
    }
    @Override
    public CategoryDAO createCategoryDAO() throws PersistencyErrorException{
        return new CategoryDAOCSV(this.getFile("CSV_CATEGORY"));
    }
    @Override
    public ConsoleDAO createConsoleDAO() throws PersistencyErrorException{
        return new ConsoleDAOCSV(this.getFile("CSV_CONSOLE"));
    }

    private File getFile(String key) throws PersistencyErrorException{
        try(FileInputStream in = new FileInputStream(CSVFactory.PROPERTIES)){
            Properties properties = new Properties();

            properties.load(in);
            String s = properties.getProperty(key);
            return new File(s);
        } catch(IOException e){
            throw new PersistencyErrorException("There was an error");
        }
    }
}
