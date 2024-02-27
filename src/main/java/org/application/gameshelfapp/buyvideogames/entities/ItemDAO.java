package org.application.gameshelfapp.buyvideogames.entities;


import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.NoGamesFoundException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface ItemDAO {

     List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException, NoGamesFoundException;
     void addGameForSale(Videogame game, Filters filters) throws PersistencyErrorException;
     void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException;
     void saveSale(User user, Videogame game, int copies, float price, String address) throws PersistencyErrorException;
     void updateSale(String id) throws PersistencyErrorException;
     List<Videogame> getSales(String sellerName) throws PersistencyErrorException;

}