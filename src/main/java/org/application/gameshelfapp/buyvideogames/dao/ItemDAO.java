package org.application.gameshelfapp.buyvideogames.dao;


import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.util.List;

public interface ItemDAO {
     void checkVideogameExistence(Filters filters) throws PersistencyErrorException, AlreadyExistingVideogameException;
     List<Videogame> getVideogamesFiltered(Filters filters) throws PersistencyErrorException, NoGameInCatalogueException;
     void addGameForSale(Videogame game) throws PersistencyErrorException;
     void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException;
     void updateGameForSale(Videogame game) throws PersistencyErrorException;

}