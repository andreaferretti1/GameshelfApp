package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.NullPasswordException;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Arrays;

public class SHA256Encoder implements Encoder{

    private String passwordToCrypt;
    private String encryptedPassword;

    public SHA256Encoder(String password){
        this.passwordToCrypt = password;
    }

    @Override
    public void cryptPassword() throws NullPasswordException {
        if(this.passwordToCrypt == null) throw new NullPasswordException("You should enter a password");
        try{
            MessageDigest m = MessageDigest.getInstance("SHA-256");
            m.update(passwordToCrypt.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            this.encryptedPassword = s.toString();
            this.removePasswordToCrypt();
        } catch(NoSuchAlgorithmException e){
            System.exit(1);
        }
    }

    @Override
    public String getEncryptedPassword(){
        return this.encryptedPassword;
    }

    public String getPasswordToCrypt(){
        return this.passwordToCrypt;
    }

    @Override
    public void removePasswordToCrypt(){
        Arrays.fill(this.passwordToCrypt.toCharArray(), '\0');
        this.passwordToCrypt = null;
    }
}