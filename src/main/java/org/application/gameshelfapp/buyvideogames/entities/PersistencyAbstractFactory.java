package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.entities.AccessDAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public abstract class PersistencyAbstractFactory {

        private static final String csv = "CSV";
        private static final String jdbc = "JDBC";

        public PersistencyAbstractFactory getFactory(){

            try{
            Properties properties = new Properties();

            properties.load(new FileInputStream("/src/main/resources/org/application/gameshelfapp/configuration/configuration.properties"));
            String s = properties.getProperty("PERSISTENCE");
            if(s.equals(jdbc)){
                return new JDBCFactory();
            }
            return new CSVFactory();
            }catch(IOException e){
                return new CSVFactory();
            }
        }

        public abstract ItemDAO createItemDAO();
        public abstract CatalogueDAO createCatalogueDAO();
        public abstract AccessDAO createAccessDAO();
}
