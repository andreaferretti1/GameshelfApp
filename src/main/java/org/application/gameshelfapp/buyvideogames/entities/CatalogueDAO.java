package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

public interface CatalogueDAO {

    //TODO implementa metodo getCatalogue se possibile
    void addVideogame(String email, Videogame game) throws PersistencyErrorException;
    void removeVideogame(String email, Videogame game) throws PersistencyErrorException;
}
