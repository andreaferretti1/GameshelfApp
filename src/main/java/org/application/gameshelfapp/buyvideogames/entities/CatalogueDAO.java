package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.IOException;

public interface CatalogueDAO {
    void addVideogame(String username, Videogame game, int numberOfCopies) throws PersistencyErrorException, IOException;
    void removeVideogame(String username, Videogame game, int quantity) throws PersistencyErrorException, IOException;
}
