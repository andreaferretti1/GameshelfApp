package org.application.gameshelfapp.buyvideogames.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.NoGamesFoundException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ItemDAOCSV implements ItemDAO {

    private String filename;
    private File fd;
    private Lock lock;


    @Override
    public ArrayList<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException, NoGamesFoundException {
         ArrayList<Videogame> gamesFound = new ArrayList<Videogame>();
         String[] record = null;

         String gameName = filters.getName();
         String console = filters.getConsole();
         String online = filters.getOnline();
         String category = filters.getCategory();


         try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));){
            while((record = csvReader.readNext()) != null){
                if(gameName.equals("all")){
                    if(record[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && record[VideogamesOnSaleAttributes.ONLINE.ordinal()].equals(online) && record[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category)){
                        Videogame game = new Videogame(record[VideogamesOnSaleAttributes.GAMENAME.ordinal()], record[VideogamesOnSaleAttributes.GAMEID.ordinal()], null);
                        game.createOwnSeller(record[VideogamesOnSaleAttributes.USERNAME.ordinal()], record[VideogamesOnSaleAttributes.EMAIL.ordinal()], Integer.parseInt(record[VideogamesOnSaleAttributes.COPIES.ordinal()]), record[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()], Float.parseFloat(record[VideogamesOnSaleAttributes.PRICE.ordinal()]));
                        gamesFound.add(game);
                    }
                }
                else{
                    if(record[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && record[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && record[VideogamesOnSaleAttributes.ONLINE.ordinal()].equals(online) && record[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category)){
                        Videogame game = new Videogame(gameName, record[VideogamesOnSaleAttributes.GAMEID.ordinal()], null);game.createOwnSeller(record[VideogamesOnSaleAttributes.USERNAME.ordinal()], record[VideogamesOnSaleAttributes.EMAIL.ordinal()], Integer.parseInt(record[VideogamesOnSaleAttributes.COPIES.ordinal()]), record[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()], Float.parseFloat(record[VideogamesOnSaleAttributes.PRICE.ordinal()]));
                        gamesFound.add(game);
                    }
                }
            }
            if(gamesFound.isEmpty()) throw new NoGamesFoundException("No games found");

         } catch (IOException | CsvValidationException e) {
             throw new PersistencyErrorException("couldn't retrieve videogames");
         }

        return gamesFound;
    }

    @Override
    public void addGameForSale(Videogame game, Filters filters) throws PersistencyErrorException{
        String[] record = null;
        String[] gameToSave = new String[10];
        gameToSave[VideogamesOnSaleAttributes.GAMEID.ordinal()] = game.getId();
        gameToSave[VideogamesOnSaleAttributes.USERNAME.ordinal()] = game.getOwnerName();
        gameToSave[VideogamesOnSaleAttributes.EMAIL.ordinal()] = game.getOwnerEmail();
        gameToSave[VideogamesOnSaleAttributes.GAMENAME.ordinal()] = game.getName();
        gameToSave[VideogamesOnSaleAttributes.CONSOLE.ordinal()] = filters.getConsole();
        gameToSave[VideogamesOnSaleAttributes.ONLINE.ordinal()] = filters.getOnline();
        gameToSave[VideogamesOnSaleAttributes.CATEGORY.ordinal()] = filters.getCategory();
        gameToSave[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()] = game.getSellerDescription();
        gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()] = game.getOwnerCopies().toString();
        gameToSave[VideogamesOnSaleAttributes.PRICE.ordinal()] = String.valueOf(game.getOwnerPrice());

        File tempFile = null;
        this.lock.lock();
        try{
            tempFile = File.createTempFile("sale", "csv");
        }catch(IOException e){
            throw new PersistencyErrorException("Couldn't save videogames for sale");
        }
        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
            CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)))){
            while((record = csvReader.readNext()) != null){
                if(gameToSave[VideogamesOnSaleAttributes.USERNAME.ordinal()].equals(record[VideogamesOnSaleAttributes.USERNAME.ordinal()])  &&  gameToSave[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(record[VideogamesOnSaleAttributes.GAMENAME.ordinal()])){
                    int totalCopiesOnSale = Integer.parseInt(gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()]) + Integer.parseInt(record[VideogamesOnSaleAttributes.COPIES.ordinal()]);
                    gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(totalCopiesOnSale);
                    csvWriter.writeNext(gameToSave);
                }
                else csvWriter.writeNext(record);
            }

            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
            tempFile.delete();

        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't save videogame in the sale catalogue");
        }finally{
            this.lock.unlock();
        }
    }

    @Override
    public void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException {

        boolean removed = false;
        String[] gameToDelete = null;
        String gameName = game.getName();
        String sellerName = game.getOwnerName();
        int copies = game.getOwnerCopies();
        File tempFile = null;

        this.lock.lock();
        try{
            tempFile = File.createTempFile("removeSale", "csv");
        }catch(IOException e){
            throw new PersistencyErrorException("Couldn't remove videogame for sale");
        }

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));){
            while((gameToDelete = csvReader.readNext()) != null){
                if(gameToDelete[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && gameToDelete[VideogamesOnSaleAttributes.USERNAME.ordinal()].equals(sellerName)){
                    int difference = Integer.parseInt(gameToDelete[VideogamesOnSaleAttributes.COPIES.ordinal()]) - copies;
                    if(difference > 0){
                        gameToDelete[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(difference);
                        csvWriter.writeNext(gameToDelete);
                    }
                    removed = true;
                }
            }

        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't remove videogame for sale");
        }finally{
            this.lock.unlock();
        }

        if(!removed){
            throw new GameSoldOutException("Videogame is sold out");
        }

    }

    @Override
    public void saveSale(User user, Videogame game, int copies) throws PersistencyErrorException{
        String[] gameSold = new String[7];
        gameSold[VideogamesSoldAttributes.CUSTOMERUSERNAME.ordinal()] = user.getUsername();
        gameSold[VideogamesSoldAttributes.SELLERUSERNAME.ordinal()] = game.getOwnerName();
        gameSold[VideogamesSoldAttributes.CUSTOMERADDRESS.ordinal()] = user.getAddress();
        gameSold[VideogamesSoldAttributes.CUSTOMEREMAIL.ordinal()] = user.getEmail();
        gameSold[VideogamesSoldAttributes.GAMENAME.ordinal()] = game.getOwnerName();
        gameSold[VideogamesSoldAttributes.GAMEID.ordinal()] = game.getId();
        gameSold[VideogamesSoldAttributes.COPIES.ordinal()] = String.valueOf(copies);

        try(CSVWriter cvsWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fd, true)))){
            cvsWriter.writeNext(gameSold);
        }catch(IOException e){
            throw new PersistencyErrorException("Couldn't save sale");
        }

    }

    @Override
    public ArrayList<Videogame> getSales(String sellerName) throws PersistencyErrorException{
        ArrayList<Videogame> gamesSold = new ArrayList<Videogame>();
        String[] record = null;

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)))){
            while((record = csvReader.readNext()) != null){
                if(record[VideogamesSoldAttributes.SELLERUSERNAME.ordinal()].equals(sellerName)){
                    Videogame game = getVideogameSold(record);
                    gamesSold.add(game);
                }
            }
            return gamesSold;
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't get sales");
        }
    }

    private static Videogame getVideogameSold(String[] record) {
        String gameName = record[VideogamesSoldAttributes.GAMENAME.ordinal()];
        String gameId = record[VideogamesSoldAttributes.GAMEID.ordinal()];
        String customerName = record[VideogamesSoldAttributes.CUSTOMERUSERNAME.ordinal()];
        String customerAddress = record[VideogamesSoldAttributes.CUSTOMERADDRESS.ordinal()];
        String customerEmail = record[VideogamesSoldAttributes.CUSTOMEREMAIL.ordinal()];
        int copiesBought = Integer.parseInt(record[VideogamesOnSaleAttributes.COPIES.ordinal()]);
        Videogame game = new Videogame(gameName, gameId, null);
        game.createOwnCustomer(customerName, customerEmail, copiesBought, customerAddress);
        return game;
    }


    private enum VideogamesOnSaleAttributes{
        GAMEID, USERNAME, EMAIL, GAMENAME, CONSOLE, ONLINE, CATEGORY, DESCRIPTION, COPIES, PRICE;
    }

    private enum VideogamesSoldAttributes{
        SELLERUSERNAME, CUSTOMERUSERNAME, CUSTOMERADDRESS, CUSTOMEREMAIL, GAMEID, GAMENAME, COPIES
    }
}
