package org.application.gameshelfapp.buyvideogames.dao;


import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface ItemDAO {

     List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException;
     void addGameForSale(Videogame game) throws PersistencyErrorException;
     void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException;

}