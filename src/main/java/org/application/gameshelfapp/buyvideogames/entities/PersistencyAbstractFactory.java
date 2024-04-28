package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.entities.AccessDAO;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class PersistencyAbstractFactory {

        private static final String CSV = "CSV";
        private static final String JDBC = "JDBC";

        public static PersistencyAbstractFactory getFactory(){

            try(FileInputStream in = new FileInputStream("/src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){
            Properties properties = new Properties();

            properties.load(in);
            String s = properties.getProperty("PERSISTENCE");
            if(s.equals(JDBC)){
                return new JDBCFactory();
            }
            return new CSVFactory();
            }catch(IOException e){
                return new CSVFactory();
            }
        }

        public abstract ItemDAO createItemDAO() throws PersistencyErrorException;
        public abstract CatalogueDAO createCatalogueDAO() throws PersistencyErrorException;
        public abstract AccessDAO createAccessDAO() throws PersistencyErrorException;
        public abstract SaleDAO createSaleDAO() throws PersistencyErrorException;
}
