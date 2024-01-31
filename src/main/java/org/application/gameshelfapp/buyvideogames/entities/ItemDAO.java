package org.application.gameshelfapp.buyvideogames.entities;


import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.NoGamesFoundException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.ArrayList;

public interface ItemDAO {

     ArrayList<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException, NoGamesFoundException;
     void addGameForSale(Videogame game, Filters filters) throws PersistencyErrorException;
     void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException;
     void saveSale(User user, Videogame game, int copies) throws PersistencyErrorException;
     ArrayList<Videogame> getSales(String sellerName) throws PersistencyErrorException;

}