package org.application.gameshelfapp.sellvideogames.boundary;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;

import java.io.*;
import java.lang.reflect.InaccessibleObjectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class MobyGames{
    private String getURL(String videogameName) throws CheckFailedException {
        try(FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){
            Properties properties = new Properties();
            properties.load(in);

            String key = properties.getProperty("MOBY_GAMES");
            videogameName = videogameName.replace(" ", "%20");
            return String.format("https://api.mobygames.com/v1/games?title=%s&api_key=%s", videogameName, key);
        } catch(IOException e){
            throw new CheckFailedException("Couldn't reach the site for control");
        }
    }

    public void verifyVideogame(String videogameName) throws InvalidTitleException, CheckFailedException{
        try{
            String addressToCheck = this.getURL(videogameName);
            URL url = new URL(addressToCheck);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine = in.readLine();
                response.append(inputLine);
                in.close();
                JsonObject responseDataObject = JsonParser.parseString(inputLine).getAsJsonObject();
                if (!responseDataObject.has("games") || responseDataObject.get("games").getAsJsonArray().isEmpty()) throw new InvalidTitleException("Title is invalid");
            }
        } catch (IOException e){
            throw new CheckFailedException("Couldn't find requested videogame");
        } catch (InaccessibleObjectException e){
            throw new InvalidTitleException("Title is invalid");
        }
    }
}