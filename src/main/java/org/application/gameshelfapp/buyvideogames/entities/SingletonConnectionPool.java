package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SingletonConnectionPool {

    public static SingletonConnectionPool instance = null;

    protected SingletonConnectionPool(){}

    public static SingletonConnectionPool getInstance(){
        if(SingletonConnectionPool.instance == null){
            SingletonConnectionPool.instance = new SingletonConnectionPool();
        }
        return SingletonConnectionPool.instance;
    }

    public Connection getConnection() throws PersistencyErrorException{

        try(FileInputStream in = new FileInputStream("/src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){

            Properties properties = new Properties();
            properties.load(in);

            String user = properties.getProperty("DB_USERNAME");
            String password = properties.getProperty("DB_PASSWORD");
            String url = properties.getProperty("DB_URL");
            String driver = properties.getProperty("DB_DRIVER");

            Class.forName(driver);
            return DriverManager.getConnection(url, user, password);

        } catch (IOException | ClassNotFoundException | SQLException e){
            throw new PersistencyErrorException("Couldn't access to data");
        }
    }
}
