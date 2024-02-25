package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.entities.AccessDAO;

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

        public abstract ItemDAO createItemDAO();
        public abstract CatalogueDAO createCatalogueDAO();
        public abstract AccessDAO createAccessDAO();
}
