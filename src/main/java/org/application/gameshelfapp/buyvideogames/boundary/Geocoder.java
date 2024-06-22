package org.application.gameshelfapp.buyvideogames.boundary;

import com.google.gson.Gson;
import org.application.gameshelfapp.buyvideogames.entities.Credentials;
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
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            Gson gson = new Gson();
            Credentials[] responseDataArray = gson.fromJson(response.toString(), Credentials[].class);
            if(responseDataArray.length == 0) throw new InvalidAddressException("Address is invalid");
        }
    }
}
