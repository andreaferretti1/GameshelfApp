package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.NullPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256EncoderTest {

    @Test
    void cryptPasswordTest(){

        try {
            SHA256Encoder sha256Encoder = new SHA256Encoder("test");
            sha256Encoder.cryptPassword();
            assertEquals("9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08", sha256Encoder.getEncryptedPassword());
        } catch(NullPasswordException e){
            fail();
        }
    }

    @Test
    void cryptPasswordWithSpecialCharsTest(){
        try {
            SHA256Encoder sha256Encoder = new SHA256Encoder(" ?^=#@");
            sha256Encoder.cryptPassword();
            assertEquals("6751310f6d0437df3315e9c150b6c4cdf93b5a8b2a99098adfd554b208fa751c", sha256Encoder.getEncryptedPassword());
        } catch(NullPasswordException e){
            fail();
        }
    }

    @Test
    void cryptNullPasswordTest(){
        SHA256Encoder sha256Encoder = new SHA256Encoder(null);
        assertThrows(NullPasswordException.class, () -> sha256Encoder.cryptPassword());

    }

    @Test
    void removePassword(){
        SHA256Encoder sha256Encoder = new SHA256Encoder("test");
        sha256Encoder.removePasswordToCrypt();
        assertNull(sha256Encoder.getPasswordToCrypt());
    }
}
