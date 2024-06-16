package org.application.gameshelfapp;

import org.application.gameshelfapp.login.exception.LaunchException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.login.graphiccontroller2.TerminalController;
import org.application.gameshelfapp.login.graphiccontrollers.StartingPageController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StartApplication {

    public static void main(String[] args) throws LaunchException {
        try(FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){
            Properties properties = new Properties();

            properties.load(in);
            String s = properties.getProperty("GUI");
            if(s.equals("Coloured")) StartingPageController.start();
            else if(s.equals("CLI")) TerminalController.start();
        } catch(IOException | PersistencyErrorException e){
            throw new LaunchException("Couldn't launch app");
        }
    }
}
