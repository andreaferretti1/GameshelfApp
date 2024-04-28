package org.application.gameshelfapp.login.entities;

import org.application.gameshelfapp.login.exception.NullPasswordException;

public interface Encoder {
     void cryptPassword() throws NullPasswordException;
     String getEncryptedPassword();
     void removePasswordToCrypt();
}
