package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeocoderTest {
    @Test
    void checkAddressSuccessfulTest(){
        try {
            Geocoder geocoder = new Geocoder("via Cambridge, Roma, Italia");
            geocoder.checkAddress();
        } catch(InvalidAddressException | IOException e){
            fail();
        }
    }

    @Test
    void checkInvalidAddressTest(){
        try {
            Geocoder geocoder = new Geocoder("addressTest");
            assertThrows(InvalidAddressException.class, geocoder::checkAddress);
        } catch(IOException e){
            fail();
        }
    }

    @Test
    void checkAddressIOExceptionTest(){     //test was executed without connection
        try{
            Geocoder geocoder = new Geocoder("addressTest");
            assertThrows(IOException.class, geocoder::checkAddress);
        } catch (IOException e){
            fail();
        }
    }
}