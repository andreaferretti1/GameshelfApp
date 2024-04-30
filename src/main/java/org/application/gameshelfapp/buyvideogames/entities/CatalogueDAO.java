package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.IOException;

public interface CatalogueDAO {
    void addVideogame(String email, Videogame game, int numberOfCopies) throws PersistencyErrorException;
    void removeVideogame(String email, Videogame game, int quantity) throws PersistencyErrorException;
}
