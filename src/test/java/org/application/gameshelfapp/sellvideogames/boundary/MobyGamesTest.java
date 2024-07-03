package org.application.gameshelfapp.sellvideogames.boundary;

import org.application.gameshelfapp.login.exception.CheckFailedException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MobyGamesTest {

    @Test
    void verifyVideogameTest() {
        try {
            MobyGames test = new MobyGames();
            String testTitle = "Dark Souls";
            test.verifyVideogame(testTitle);
        } catch (InvalidTitleException | CheckFailedException e) {
            fail();
        }
    }

    @Test
    void verifyExceptionLaunchedTest(){
        MobyGames test = new MobyGames();
        String testTitle = "Dark Souls 6";
        assertThrows(InvalidTitleException.class, ()->test.verifyVideogame(testTitle));
    }
}
