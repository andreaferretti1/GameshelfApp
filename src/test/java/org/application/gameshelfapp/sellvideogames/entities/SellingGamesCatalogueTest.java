package org.application.gameshelfapp.sellvideogames.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class SellingGamesCatalogueTest {

    @Test
    void setAndGetSellingGamesTest(){
        SellingGamesCatalogue test = new SellingGamesCatalogue();
        test.setSellingGames(new ArrayList<>());
        assertNotNull(test.getSellingGames());
    }

}
