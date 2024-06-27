package org.application.gameshelfapp.buyvideogames.dao;

import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface CatalogueDAO {

    List<Videogame> getCatalogue(String email) throws PersistencyErrorException;
    void addVideogame(String email, Videogame game) throws PersistencyErrorException;
    void removeVideogame(String email, Videogame game) throws PersistencyErrorException;
}
