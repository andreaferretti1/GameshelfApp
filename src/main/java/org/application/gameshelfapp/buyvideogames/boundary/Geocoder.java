package org.application.gameshelfapp.buyvideogames.boundary;

import com.google.gson.Gson;
import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class Geocoder {
    private String getURL(String address){
        return String.format("https://geocode.maps.co/search?q=%s&api_key=6637ee4ba2422085994104svn291950", address);
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
