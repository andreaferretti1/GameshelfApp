package org.application.gameshelfapp.buyvideogames.dao;

import com.opencsv.CSVWriter;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.dao.CSVFactory;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.*;

class ItemDAOCSVTest {

    @Test
    void getVideogamesForSaleGameNameTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }


        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("gameTest", gameForSale.getName());
        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleCopiesTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(2, gameForSale.getCopies());
        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSalePriceTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(10, gameForSale.getPrice());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getVideogamesForSaleDescriptionTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("descriptionTest", gameForSale.getDescription());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getVideogamesForSalePlatformTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("consoleTest", gameForSale.getPlatform());
        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleCategoryTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '2', '10')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("categoryTest", gameForSale.getCategory());
        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void getVideogameForSaleWithoutNameTest(){       //to run this test were added games ('gameTest1', 'consoleTest1', 'categoryTest1', 'descriptionTest1', '2', '10'), ('gameTest2', 'consoleTest1', 'categoryTest1', 'descriptionTest2', '3', '10'), ('gameTest3', 'consoleTest2', 'categoryTest2', 'descriptionTest3', '4', '9')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest1", "categoryTest1", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
            testRecord = new String[] {"gameTest2", "consoleTest1", "categoryTest1", "descriptionTest2", String.valueOf(3), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
            testRecord = new String[] {"gameTest3", "consoleTest2", "categoryTest2", "descriptionTest3", String.valueOf(4), String.valueOf(9)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters(null, "consoleTest1", "categoryTest1");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(2, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void getVideogamesForSaleZeroCopiesTest(){      //to run this test was added game ('gameTest', 'consoleTest', 'categoryTest', 'descriptionTest', '0', '10')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"gameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(0), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Filters filters = new Filters("gameTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void addGameForSaleTest(){

        try{
            Videogame game = new Videogame("nameTest", 2, 10, "descriptionTest", "platformTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.addGameForSale(game);
            Filters filters = new Filters("nameTest", "platformTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(1, (long) games.size());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

    @Test
    void addGameForSaleExistingDiffConsoleTest(){       //in the database there was the tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"nameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Videogame game = new Videogame("nameTest", 3, 11, "descriptionTest", "consoleTest2", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.addGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest2", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            assertEquals(1, (long) games.size());

        } catch(PersistencyErrorException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }
    @Test
    void removeGameForSaleTest(){       //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"nameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Videogame game = new Videogame("nameTest", 1, 10, "descriptionTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(1, gameForSale.getCopies());
        } catch(PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void removGameForSaleSamePriceTest() {        //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"nameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try {
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Videogame game = new Videogame("nameTest", 1, 10, "descriptionTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(10, gameForSale.getPrice());
        } catch (PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | IOException e) {
            fail();
        }
    }

    @Test
    void removGameForSaleSameDescriptionTest(){        //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"nameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try {
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Videogame game = new Videogame("nameTest", 1, 10, "descriptionTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals("descriptionTest", gameForSale.getDescription());
        } catch (PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | IOException e) {
            fail();
        }
    }
    @Test
    void removeGameForSaleSoldOutTest(){        //In the database there was tuple ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"nameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Videogame game = new Videogame("nameTest", 4, 10, "descriptionTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            assertThrows(GameSoldOutException.class, () -> itemDAO.removeGameForSale(game));
        } catch(PersistencyErrorException | IOException e){
            fail();
        }
    }
    @Test
    void removeGameForSaleDiffConsoleTest(){        //In the database there were tuples ('nameTest', 'consoleTest', 'categoryTest', '2', '10', 'descriptionTest') and ('nameTest', 'consoleTest1', 'categoryTest', '3', '12', 'descriptionTest')
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter("tempFile_test.csv")))){
            String[] testRecord = {"nameTest", "consoleTest", "categoryTest", "descriptionTest", String.valueOf(2), String.valueOf(10)};
            csvWriter.writeNext(testRecord);
            testRecord = new String[]{"nameTest", "consoleTest1", "categoryTest", "descriptionTest", String.valueOf(3), String.valueOf(12)};
            csvWriter.writeNext(testRecord);
        } catch (IOException e) {
            fail();
        }
        try{
            Files.move(Paths.get("tempFile_test.csv"), Paths.get("src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv"), REPLACE_EXISTING);
            Videogame game = new Videogame("nameTest", 2, 10, "descriptionTest", "consoleTest", "categoryTest");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.removeGameForSale(game);

            Filters filters = new Filters("nameTest", "consoleTest", "categoryTest");
            List<Videogame> games = itemDAO.getVideogamesFiltered(filters);
            Videogame gameForSale = games.getFirst();
            assertEquals(0, gameForSale.getCopies());

        } catch(PersistencyErrorException | GameSoldOutException | NoGameInCatalogueException | IOException e){
            fail();
        }
    }

    @Test
    void checkVideogameExistenceTest(){
        try{
            Filters testFilters = new Filters("Test","TestConsole", "TestCategory");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.checkVideogameExistence(testFilters);
        } catch(PersistencyErrorException | AlreadyExistingVideogameException e){
            fail();
        }
    }

    @Test
    void checkVideogameExistenceExceptionLaunchedTest() {        //In database exist tuple ('Test1', 1, 1, 'This is a description', 'Test Console', 'Test Category')
        try {
            Filters testFilters = new Filters("Test1", "Test Console", "Test Category");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            assertThrows(AlreadyExistingVideogameException.class, () -> itemDAO.checkVideogameExistence(testFilters));
        } catch (PersistencyErrorException e) {
            fail();
        }
    }

    @Test
    void updateGameForSaleTest(){           //In the database there exist tuple('Test1', 1, 1, 'This is a description', 'Test Console', 'Test Category')
        try{
            Videogame testGame = new Videogame("Test1", 2, 5,"Description Test", "Test Console", "Test Category");
            CSVFactory csvFactory = new CSVFactory();
            ItemDAO itemDAO = csvFactory.createItemDAO();
            itemDAO.updateGameForSale(testGame);
            Filters testFilters = new Filters(testGame.getName(), testGame.getPlatform(), testGame.getCategory());
            List<Videogame> gameListTest = itemDAO.getVideogamesFiltered(testFilters);
            Videogame testUpdate = gameListTest.getFirst();
            assertEquals(3, testUpdate.getCopies());
            assertEquals(5, testUpdate.getPrice());
            assertEquals("Description Test", testUpdate.getDescription());
        } catch(PersistencyErrorException | NoGameInCatalogueException e){
            fail();
        }
    }

}