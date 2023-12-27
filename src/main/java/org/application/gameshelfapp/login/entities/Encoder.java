package org.application.gameshelfapp.login.entities;

public interface Encoder {
     void cryptPassword();
     String getEncryptedPassword();
     void setPasswordToCrypt(String password);
}
