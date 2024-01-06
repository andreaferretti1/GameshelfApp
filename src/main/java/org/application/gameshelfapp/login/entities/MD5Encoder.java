package org.application.gameshelfapp.login.entities;

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.util.Arrays;

public class MD5Encoder implements Encoder{

    private String passwordToCrypt;
    private String encryptedPassword;

    public MD5Encoder(String password){
        this.passwordToCrypt = password;
    }

    @Override
    public void cryptPassword(){
        try{
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(passwordToCrypt.getBytes());
            byte[] bytes = m.digest();
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            this.encryptedPassword = s.toString();
        } catch(NoSuchAlgorithmException e){
            //lancia eccezione al chiamante
        }
    }

    @Override
    public String getEncryptedPassword(){
        return this.encryptedPassword;
    }

    @Override
    public void removePasswordToCrypt(){
        Arrays.fill(this.passwordToCrypt.toCharArray(), '\0');
        this.passwordToCrypt = null;
    }
}