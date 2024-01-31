package org.application.gameshelfapp.buyvideogames.entities;

import java.util.ArrayList;

public class ItemDAOJDBC implements ItemDAO {

    @Override
    public ArrayList<Videogame> getVideogamesForSale(Filters filters) {
        return null;
    }

    @Override
    public void addGameForSale(Videogame game, Filters filters) {

    }

    @Override
    public void removeGameForSale(Videogame game) {

    }

    @Override
    public void saveSale(User user, Videogame game, int copies) {

    }

    @Override
    public ArrayList<Videogame> getSales(String sellerName) {
        return null;
    }
}
