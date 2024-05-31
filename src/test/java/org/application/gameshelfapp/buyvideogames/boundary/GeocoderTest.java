package org.application.gameshelfapp.buyvideogames.boundary;

import org.application.gameshelfapp.buyvideogames.exception.InvalidAddressException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GeocoderTest {
    @Test
    void checkAddressSuccessfulTest(){
        try {
            Geocoder geocoder = new Geocoder();
            geocoder.checkAddress("via Cambridge, Roma, Italia");
        } catch(InvalidAddressException | IOException e){
            fail();
        }
    }

    @Test
    void checkInvalidAddressTest(){
        Geocoder geocoder = new Geocoder();
        assertThrows(InvalidAddressException.class, () -> geocoder.checkAddress("addressTest"));
    }

    @Test
    void checkAddressIOExceptionTest(){     //test was executed without connection
        Geocoder geocoder = new Geocoder();
        assertThrows(IOException.class, () -> geocoder.checkAddress("addressTest"));
    }
}