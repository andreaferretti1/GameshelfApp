package org.application.gameshelfapp.buyvideogames.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.buyvideogames.entities.Filters;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ItemDAOCSV implements ItemDAO {

    private static final String TEMP_FILE = "temp_sale.csv";
    private final File fdGamesForSale;
    private final Lock lock;

    public ItemDAOCSV(File fd){
        this.lock = new ReentrantLock();
        this.fdGamesForSale = fd;
    }

    private List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException {

         List<Videogame> gamesFound = new ArrayList<>();
         String[] myRecord;

         String gameName = filters.getName();
         String console = filters.getConsole();
         String category = filters.getCategory();

         try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesForSale)))){
            while((myRecord = csvReader.readNext()) != null){
                if(gameName == null){
                    if(myRecord[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && myRecord[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category)){
                        Videogame game = new Videogame(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()], Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]), Float.parseFloat(myRecord[VideogamesOnSaleAttributes.PRICE.ordinal()]), myRecord[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()], console, category);
                        gamesFound.add(game);
                    }
                }
                else{
                    if(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && myRecord[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && myRecord[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category)){
                        Videogame game = new Videogame(gameName, Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]), Float.parseFloat(myRecord[VideogamesOnSaleAttributes.PRICE.ordinal()]), myRecord[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()], console, category);
                        gamesFound.add(game);
                    }
                }
            }
         } catch (IOException | CsvValidationException e) {
             throw new PersistencyErrorException("Couldn't retrieve videogames");
         }
        return gamesFound;
    }
    @Override
    public void checkVideogameExistence(Filters filters) throws PersistencyErrorException, AlreadyExistingVideogameException{
        List<Videogame> gameList = this.getVideogamesForSale(filters);
        if (!gameList.isEmpty()) throw new AlreadyExistingVideogameException("Game Already in Catalogue");
    }

    @Override
    public List<Videogame> getVideogamesFiltered(Filters filters) throws PersistencyErrorException, NoGameInCatalogueException{
        List<Videogame> gameList = this.getVideogamesForSale(filters);
        if (gameList.isEmpty()) throw new NoGameInCatalogueException("Couldn't find Videogame");
        return gameList;
    }

    @Override
    public void addGameForSale(Videogame game) throws PersistencyErrorException{
        String[] gameToSave = new String[6];
        gameToSave[VideogamesOnSaleAttributes.GAMENAME.ordinal()] = game.getName();
        gameToSave[VideogamesOnSaleAttributes.CONSOLE.ordinal()] = game.getPlatform();
        gameToSave[VideogamesOnSaleAttributes.CATEGORY.ordinal()] = game.getCategory();
        gameToSave[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()] = game.getDescription();
        gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(game.getCopies());
        gameToSave[VideogamesOnSaleAttributes.PRICE.ordinal()] = String.valueOf(game.getPrice());

        this.lock.lock();

        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fdGamesForSale, true)))){
            csvWriter.writeNext(gameToSave);
        }catch(IOException e){
            throw new PersistencyErrorException("Couldn't save videogame in the sale catalogue");
        }finally{
            this.lock.unlock();
        }
    }

    @Override
    public void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException {
        boolean removed = false;
        String[] gameToDelete;
        String gameName = game.getName();
        String platform = game.getPlatform();
        int copies = game.getCopies();
        File tempFile = new File(TEMP_FILE);

        this.lock.lock();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesForSale)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)))){

            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException("Couldn't remove game for sale");
            }

            while((gameToDelete = csvReader.readNext()) != null){
                if(gameToDelete[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && gameToDelete[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(platform)){
                    int difference = Integer.parseInt(gameToDelete[VideogamesOnSaleAttributes.COPIES.ordinal()]) - copies;
                    if(difference >= 0){
                        gameToDelete[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(difference);
                        removed = true;
                    }
                }
                csvWriter.writeNext(gameToDelete);
            }
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't remove videogame for sale");
        }finally{
            this.lock.unlock();
        }

        if(!removed){
            throw new GameSoldOutException("Videogame is sold out");
        }
        try{
            Files.move(tempFile.toPath(), this.fdGamesForSale.toPath(), REPLACE_EXISTING);
        } catch (IOException e){
            throw new PersistencyErrorException("Couldn't remove videogame for sale");
        }
    }
    @Override
    public void updateGameForSale(Videogame game) throws PersistencyErrorException{
        String[] gameToUpdate;
        String gameName = game.getName();
        String platform = game.getPlatform();
        int copies = game.getCopies();
        float price = game.getPrice();
        String description = game.getDescription();
        File tempFile = new File(TEMP_FILE);

        this.lock.lock();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesForSale)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)))){

            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException("Couldn't update game for sale");
            }

            while((gameToUpdate = csvReader.readNext()) != null){
                if(gameToUpdate[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && gameToUpdate[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(platform)){
                    int newCopies = Integer.parseInt(gameToUpdate[VideogamesOnSaleAttributes.COPIES.ordinal()]) + copies;
                    gameToUpdate[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(newCopies);
                    gameToUpdate[VideogamesOnSaleAttributes.PRICE.ordinal()] = String.valueOf(price);
                    gameToUpdate[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()] = description;
                }
                csvWriter.writeNext(gameToUpdate);
            }
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't update videogame for sale");
        }finally{
            this.lock.unlock();
        }
        try {
            Files.move(tempFile.toPath(), this.fdGamesForSale.toPath(), REPLACE_EXISTING);
        }  catch (IOException e){
            throw new PersistencyErrorException("Couldn't update videogame");
        }
    }

    private enum VideogamesOnSaleAttributes{
        GAMENAME, CONSOLE, CATEGORY, DESCRIPTION, COPIES, PRICE
    }

}
