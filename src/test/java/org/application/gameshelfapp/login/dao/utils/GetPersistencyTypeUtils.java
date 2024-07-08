package org.application.gameshelfapp.login.dao.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

public class GetPersistencyTypeUtils {

    public static String getPersistencyType(){
        try(FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){
            Properties properties = new Properties();

            properties.load(in);
            return properties.getProperty("PERSISTENCE");
        }catch(IOException e){
            fail();
        }
    }
}
