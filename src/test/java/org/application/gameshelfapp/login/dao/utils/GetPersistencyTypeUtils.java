package org.application.gameshelfapp.login.dao.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.fail;

public class GetPersistencyTypeUtils {

    public static String getPersistencyType(){
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties");

            properties.load(in);
        }catch(IOException e){
            fail();
        }
        return properties.getProperty("PERSISTENCE");
    }
}