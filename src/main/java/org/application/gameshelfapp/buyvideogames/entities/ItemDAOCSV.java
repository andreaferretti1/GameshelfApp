package org.application.gameshelfapp.buyvideogames.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ItemDAOCSV implements ItemDAO {

    private static final String TEMP_FILE = "temp_sale.csv";
    private File fdGamesForSale;
    private final Lock lock;

    public ItemDAOCSV(File fd){
        this.lock = new ReentrantLock();
        this.fdGamesForSale = fd;
    }

    @Override
    public List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException{

         List<Videogame> gamesFound = new ArrayList<Videogame>();
         String[] myRecord = null;

         String gameName = filters.getName();
         String console = filters.getConsole();
         String category = filters.getCategory();

         try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesForSale)));){
            while((myRecord = csvReader.readNext()) != null){
                if(gameName == null){
                    if(myRecord[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && myRecord[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category) && Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]) > 0){
                        Videogame game = new Videogame(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()], Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]), Float.parseFloat(myRecord[VideogamesOnSaleAttributes.PRICE.ordinal()]), myRecord[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()]);
                        gamesFound.add(game);
                    }
                }
                else{
                    if(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && myRecord[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && myRecord[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category)){
                        Videogame game = new Videogame(gameName, Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]), Float.parseFloat(myRecord[VideogamesOnSaleAttributes.PRICE.ordinal()]), myRecord[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()]);
                        gamesFound.add(game);
                    }
                }
            }
         } catch (IOException | CsvValidationException e) {
             throw new PersistencyErrorException("couldn't retrieve videogames");
         }
        return gamesFound;
    }

    @Override
    public void addGameForSale(Videogame game, Filters filters) throws PersistencyErrorException{
        String[] myRecord = null;
        String[] gameToSave = new String[6];
        gameToSave[VideogamesOnSaleAttributes.GAMENAME.ordinal()] = game.getName();
        gameToSave[VideogamesOnSaleAttributes.CONSOLE.ordinal()] = filters.getConsole();
        gameToSave[VideogamesOnSaleAttributes.CATEGORY.ordinal()] = filters.getCategory();
        gameToSave[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()] = game.getDescription();
        gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(game.getCopies());
        gameToSave[VideogamesOnSaleAttributes.PRICE.ordinal()] = String.valueOf(game.getPrice());

        File tempFile = new File(TEMP_FILE);

        this.lock.lock();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesForSale)));
            CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
            FileChannel channel = new FileOutputStream(tempFile).getChannel()){

            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException("Couldn't insert videogame for sale");
            }
            channel.truncate(0);

            while((myRecord = csvReader.readNext()) != null){
                if(gameToSave[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()]) && gameToSave[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(filters.getConsole())){
                    int totalCopiesOnSale = Integer.parseInt(gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()]) + Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]);
                    gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(totalCopiesOnSale);
                    gameToSave[VideogamesOnSaleAttributes.PRICE.ordinal()] = String.valueOf(game.getPrice());
                    csvWriter.writeNext(gameToSave);
                }
                else csvWriter.writeNext(myRecord);
            }

            Files.move(tempFile.toPath(), this.fdGamesForSale.toPath(), REPLACE_EXISTING);


        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't save videogame in the sale catalogue");
        }finally{
            this.lock.unlock();
        }
    }

    @Override
    public void removeGameForSale(Videogame game, Filters filters) throws PersistencyErrorException, GameSoldOutException {
        boolean removed = false;
        String[] gameToDelete = null;
        String gameName = game.getName();
        String platform = filters.getConsole();
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
                    if(difference > 0){
                        gameToDelete[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(difference);
                        removed = true;
                    }
                }
                csvWriter.writeNext(gameToDelete);
            }
            Files.move(tempFile.toPath(), this.fdGamesForSale.toPath(), REPLACE_EXISTING);

        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't remove videogame for sale");
        }finally{
            this.lock.unlock();
        }

        if(!removed){
            throw new GameSoldOutException("Videogame is sold out");
        }
    }
    private enum VideogamesOnSaleAttributes{
        GAMENAME, CONSOLE, CATEGORY, DESCRIPTION, COPIES, PRICE;
    }

}
