package org.application.gameshelfapp.buyvideogames.boundary;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

public class Geocoder {
    private String getURL(String address) throws IOException{
        try(FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){
            Properties properties = new Properties();
            properties.load(in);

            String key = properties.getProperty("GEOCODER");
            return String.format("https://geocode.maps.co/search?q=%s&api_key=%s", address, key);
        } catch(IOException e){
            throw new IOException("Couldn't send credentials");
        }
    }

    public void checkAddress(String address) throws InvalidAddressException, IOException{
        String addressToCheck = this.getURL(address);
        URL url = new URL(addressToCheck);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine = in.readLine();

            JsonArray jsonArray = JsonParser.parseString(inputLine).getAsJsonArray();
            if(jsonArray.isEmpty()) throw new InvalidAddressException("Address inserted is invalid");
            in.close();
        }
    }
}
