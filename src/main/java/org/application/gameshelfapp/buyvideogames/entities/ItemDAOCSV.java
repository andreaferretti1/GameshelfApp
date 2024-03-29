package org.application.gameshelfapp.buyvideogames.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.buyvideogames.exception.GameSoldOutException;
import org.application.gameshelfapp.buyvideogames.exception.NoGamesFoundException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class ItemDAOCSV implements ItemDAO {

    private static final String TEMP_FILE = "temp_sale.csv";
    private File fdGamesForSale;
    private File fdGamesSold;
    private final Lock lock;

    public ItemDAOCSV() throws PersistencyErrorException{
        this.lock = new ReentrantLock();

        try(FileInputStream in = new FileInputStream("src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")) {
            Properties properties = new Properties();

            properties.load(in);
            String s1 = properties.getProperty("CSV_GAMES_ON_SALE");
            String s2 = properties.getProperty("CSV_GAMES_SOLD");
            this.fdGamesForSale = new File(s1);
            this.fdGamesSold = new File(s2);

        } catch(IOException e){
            throw new PersistencyErrorException("Couldn't access to games");
        }
    }


    @Override
    public List<Videogame> getVideogamesForSale(Filters filters) throws PersistencyErrorException, NoGamesFoundException {

         List<Videogame> gamesFound = new ArrayList<Videogame>();
         String[] myRecord = null;

         String gameName = filters.getName();
         String console = filters.getConsole();
         String online = filters.getOnline();
         String category = filters.getCategory();


         try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesForSale)));){
            while((myRecord = csvReader.readNext()) != null){
                if(gameName.equals("all")){
                    if(myRecord[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && myRecord[VideogamesOnSaleAttributes.ONLINE.ordinal()].equals(online) && myRecord[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category)){
                        Videogame game = new Videogame(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()], myRecord[VideogamesOnSaleAttributes.GAMEID.ordinal()], null);
                        game.createOwnSeller(myRecord[VideogamesOnSaleAttributes.USERNAME.ordinal()], myRecord[VideogamesOnSaleAttributes.EMAIL.ordinal()], Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]), myRecord[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()], Float.parseFloat(myRecord[VideogamesOnSaleAttributes.PRICE.ordinal()]));
                        gamesFound.add(game);
                    }
                }
                else{
                    if(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && myRecord[VideogamesOnSaleAttributes.CONSOLE.ordinal()].equals(console) && myRecord[VideogamesOnSaleAttributes.ONLINE.ordinal()].equals(online) && myRecord[VideogamesOnSaleAttributes.CATEGORY.ordinal()].equals(category)){
                        Videogame game = new Videogame(gameName, myRecord[VideogamesOnSaleAttributes.GAMEID.ordinal()], null);game.createOwnSeller(myRecord[VideogamesOnSaleAttributes.USERNAME.ordinal()], myRecord[VideogamesOnSaleAttributes.EMAIL.ordinal()], Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]), myRecord[VideogamesOnSaleAttributes.DESCRIPTION.ordinal()], Float.parseFloat(myRecord[VideogamesOnSaleAttributes.PRICE.ordinal()]));
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
        String[] myRecord = null;
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
                if(gameToSave[VideogamesOnSaleAttributes.USERNAME.ordinal()].equals(myRecord[VideogamesOnSaleAttributes.USERNAME.ordinal()])  &&  gameToSave[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(myRecord[VideogamesOnSaleAttributes.GAMENAME.ordinal()])){
                    int totalCopiesOnSale = Integer.parseInt(gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()]) + Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]);
                    gameToSave[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(totalCopiesOnSale);
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
    public void removeGameForSale(Videogame game) throws PersistencyErrorException, GameSoldOutException {
        boolean removed = false;
        String[] gameToDelete = null;
        String gameName = game.getName();
        String sellerName = game.getOwnerName();
        int copies = game.getOwnerCopies();
        File tempFile = new File(TEMP_FILE);

        this.lock.lock();


        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesForSale)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));){
            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException("Couldn't remove game for sale");
            }

            while((gameToDelete = csvReader.readNext()) != null){
                if(gameToDelete[VideogamesOnSaleAttributes.GAMENAME.ordinal()].equals(gameName) && gameToDelete[VideogamesOnSaleAttributes.USERNAME.ordinal()].equals(sellerName)){
                    int difference = Integer.parseInt(gameToDelete[VideogamesOnSaleAttributes.COPIES.ordinal()]) - copies;
                    if(difference > 0){
                        gameToDelete[VideogamesOnSaleAttributes.COPIES.ordinal()] = String.valueOf(difference);
                    }
                    removed = true;
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

    @Override
    public void saveSale(User user, Videogame game, int copies, float price, String address) throws PersistencyErrorException{
        String[] gameSold = new String[9];
        gameSold[VideogamesSoldAttributes.CUSTOMERUSERNAME.ordinal()] = user.getUsername();
        gameSold[VideogamesSoldAttributes.SELLERUSERNAME.ordinal()] = game.getOwnerName();
        gameSold[VideogamesSoldAttributes.CUSTOMERADDRESS.ordinal()] = address;
        gameSold[VideogamesSoldAttributes.CUSTOMEREMAIL.ordinal()] = user.getEmail();
        gameSold[VideogamesSoldAttributes.GAMENAME.ordinal()] = game.getOwnerName();
        gameSold[VideogamesSoldAttributes.GAMEID.ordinal()] = game.getId();
        gameSold[VideogamesSoldAttributes.COPIES.ordinal()] = String.valueOf(copies);
        gameSold[VideogamesSoldAttributes.PRICE.ordinal()] = String.valueOf(price);
        gameSold[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()] = "to confirm";

        try(CSVWriter cvsWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fdGamesSold, true)))){
            cvsWriter.writeNext(gameSold);
        }catch(IOException e){
            throw new PersistencyErrorException("Couldn't save sale");
        }
    }

    @Override
    public void updateSale(String id) throws PersistencyErrorException {
        File tempFile = new File(TEMP_FILE);
        this.lock.lock();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesSold)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
            ){
            String[] myRecord;
            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[VideogamesSoldAttributes.GAMEID.ordinal()].equals(id)){
                    myRecord[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()] = "confirmed";
                }

                csvWriter.writeNext(myRecord);
            }
            Files.move(tempFile.toPath(), this.fdGamesSold.toPath(), REPLACE_EXISTING);

        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't confirm delivery");
        }
    }

    @Override
    public List<Videogame> getSales(String sellerName) throws PersistencyErrorException{
        List<Videogame> gamesSold = new ArrayList<Videogame>();
        String[] myRecord = null;

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdGamesSold)))){
            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[VideogamesSoldAttributes.SELLERUSERNAME.ordinal()].equals(sellerName) && myRecord[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()].equals("to confirm")){
                    Videogame game = this.getVideogameSold(myRecord);
                    gamesSold.add(game);
                }
            }
            return gamesSold;
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't get sales");
        }
    }

    private Videogame getVideogameSold(String[] myRecord) {
        String gameName = myRecord[VideogamesSoldAttributes.GAMENAME.ordinal()];
        String gameId = myRecord[VideogamesSoldAttributes.GAMEID.ordinal()];
        String customerName = myRecord[VideogamesSoldAttributes.CUSTOMERUSERNAME.ordinal()];
        String customerAddress = myRecord[VideogamesSoldAttributes.CUSTOMERADDRESS.ordinal()];
        String customerEmail = myRecord[VideogamesSoldAttributes.CUSTOMEREMAIL.ordinal()];
        int copiesBought = Integer.parseInt(myRecord[VideogamesOnSaleAttributes.COPIES.ordinal()]);
        Videogame game = new Videogame(gameName, gameId, null);
        game.createOwnCustomer(customerName, customerEmail, copiesBought, customerAddress);
        return game;
    }


    private enum VideogamesOnSaleAttributes{
        GAMEID, USERNAME, EMAIL, GAMENAME, CONSOLE, ONLINE, CATEGORY, DESCRIPTION, COPIES, PRICE;
    }

    private enum VideogamesSoldAttributes{
        SELLERUSERNAME, CUSTOMERUSERNAME, CUSTOMERADDRESS, CUSTOMEREMAIL, GAMEID, GAMENAME, COPIES, PRICE, STATE_OF_DELIVERY;
    }
}
