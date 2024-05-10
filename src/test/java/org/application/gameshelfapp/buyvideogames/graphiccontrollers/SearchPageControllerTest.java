package org.application.gameshelfapp.buyvideogames.graphiccontrollers;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SearchPageControllerTest {

    @Test
    void setStageTest() {
    }

    @Test
    void setCustomerBoundaryTest(){
    }

    @Test
    void startTest() throws IOException {
        SearchPageController.start(new Stage(), null);
    }
}