package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SingletonConnectionPool{

    private static SingletonConnectionPool instance = null;
    private final List<Connection> activeConnections;
    private final List<Connection> availableConnections;
    private String user;
    private String password;
    private String dbUrl;
    protected SingletonConnectionPool() throws PersistencyErrorException{
        this.activeConnections = new ArrayList<>();
        this.availableConnections = new ArrayList<>();
        loadDriver();
    }

    public static SingletonConnectionPool getInstance() throws PersistencyErrorException{
        if(SingletonConnectionPool.instance == null){
            SingletonConnectionPool.instance = new SingletonConnectionPool();
        }
        return SingletonConnectionPool.instance;
    }

    public Connection getConnection() throws SQLException{
        Connection connection;
        if(this.availableConnections.isEmpty()){
            connection = DriverManager.getConnection(this.dbUrl, this.user, this.password);
            this.activeConnections.add(connection);
        } else {
            connection = this.availableConnections.removeFirst();
            if(connection.isClosed()) connection = DriverManager.getConnection(this.dbUrl, this.user, this.password);
            this.activeConnections.add(connection);
        }
        return connection;
    }

    public void releaseConnection(Connection connection){
        this.activeConnections.remove(connection);
        this.availableConnections.add(connection);
    }
    private void loadDriver() throws PersistencyErrorException{
        try(FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){

            Properties properties = new Properties();
            properties.load(in);

            this.user = properties.getProperty("DB_USERNAME");
            this.password = properties.getProperty("DB_PASSWORD");
            this.dbUrl = properties.getProperty("DB_URL");
            String driver = properties.getProperty("DB_DRIVER");

            Class.forName(driver);

        } catch (IOException | ClassNotFoundException e){
            throw new PersistencyErrorException("Couldn't access to data");
        }
    }
}
