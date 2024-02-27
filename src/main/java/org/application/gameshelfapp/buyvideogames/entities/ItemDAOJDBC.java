package org.application.gameshelfapp.buyvideogames.entities;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public class ItemDAOJDBC implements ItemDAO {

    @Override
    public List<Videogame> getVideogamesForSale(Filters filters) {
        return null;
    }

    @Override
    public void addGameForSale(Videogame game, Filters filters) {

    }

    @Override
    public void removeGameForSale(Videogame game) {

    }

    @Override
    public void saveSale(User user, Videogame game, int copies, float price, String address) {

    }

    @Override
    public void updateSale(String id) throws PersistencyErrorException {

    }

    @Override
    public List<Videogame> getSales(String sellerName) {
        return null;
    }
}
