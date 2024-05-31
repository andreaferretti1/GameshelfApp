package org.application.gameshelfapp.buyvideogames.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VideogamesFoundTest {

    @Test
    void setAndGetGamesFoundTest(){
        VideogamesFound videogamesFound = new VideogamesFound();
        List<Videogame> games = new ArrayList<Videogame>();
        games.add(new Videogame(null, 0, 0, null));
        assertEquals(games, videogamesFound.getGamesFound());
    }
}