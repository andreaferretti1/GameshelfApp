package org.application.gameshelfapp.buyvideogames.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CatalogueDAOCSV implements CatalogueDAO {

    private static final String TEMP_FILE = "temp_catalogue.csv";
    private static final String SAVE_ERROR = "Couldn't save videogame";
    private final File fdCatalogue;
    private final Lock lock;

    public CatalogueDAOCSV(File fd){
        this.lock = new ReentrantLock();
        this.fdCatalogue = fd;
    }

    @Override
    public List<Videogame> getCatalogue(String email) throws PersistencyErrorException {
        List<Videogame> catalogue = new ArrayList<>();
        String[] tuple;

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdCatalogue)))){
            while((tuple = csvReader.readNext()) != null){
                if(tuple[CatalogueAttributes.EMAIL.ordinal()].equals(email)){
                    Videogame game = new Videogame(tuple[CatalogueAttributes.GAMENAME.ordinal()], Integer.parseInt(tuple[CatalogueAttributes.COPIES.ordinal()]), 0, null, tuple[CatalogueAttributes.PLATFORM.ordinal()], null);
                    catalogue.add(game);
                }
            }
        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't get catalogue");
        }
        return catalogue;
    }

    @Override
    public void addVideogame(String email, Videogame game) throws PersistencyErrorException {
        String[] myRecord = new String[4];
        String[] recordRead;
        boolean added = false;

        String gameName = game.getName();
        String platform = game.getPlatform();
        myRecord[CatalogueAttributes.EMAIL.ordinal()] = email;
        myRecord[CatalogueAttributes.GAMENAME.ordinal()] = gameName;
        myRecord[CatalogueAttributes.PLATFORM.ordinal()] = platform;
        myRecord[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(game.getCopies());
        File tempFile = new File(TEMP_FILE);

        this.lock.lock();

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
             CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdCatalogue)));
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             FileChannel channel = fileOutputStream.getChannel()){

            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException(SAVE_ERROR);
            }
            channel.truncate(0);

            while((recordRead = csvReader.readNext()) != null){
                if(recordRead[CatalogueAttributes.GAMENAME.ordinal()].equals(gameName) && recordRead[CatalogueAttributes.PLATFORM.ordinal()].equals(platform)){
                    int newNumberOfCopies = game.getCopies() + Integer.parseInt(myRecord[CatalogueAttributes.COPIES.ordinal()]);
                    recordRead[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(newNumberOfCopies);
                    added = true;
                }
                csvWriter.writeNext(recordRead);
            }

            if(!added) csvWriter.writeNext(myRecord);

            csvWriter.flush();
        } catch (IOException | CsvValidationException e) {
            throw new PersistencyErrorException(SAVE_ERROR);
        }finally{this.lock.unlock();}

        try {
            Files.move(tempFile.toPath(), this.fdCatalogue.toPath(), REPLACE_EXISTING);
        }  catch (IOException e){
            throw new PersistencyErrorException("Couldn't update videogame");
        }
    }

    @Override
    public void removeVideogame(String email, Videogame game) throws PersistencyErrorException {  //quantity rappresenta il numero di copie del videogioco possedute dal proprietario. Se è 0, allora rimuovo il videogioco
        String[] myRecord;

        File tempFile = new File(TEMP_FILE);

        String gameName = game.getName();
        String platform = game.getPlatform();
        int copiesToRemove = game.getCopies();

        this.lock.lock();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdCatalogue)));
             CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
             FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
             FileChannel channel = fileOutputStream.getChannel()){

            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException(SAVE_ERROR);
            }
            channel.truncate(0);

            while ((myRecord = csvReader.readNext()) != null) {

                if (myRecord[CatalogueAttributes.GAMENAME.ordinal()].equals(gameName) && myRecord[CatalogueAttributes.PLATFORM.ordinal()].equals(platform)) {
                    int quantity = Integer.parseInt(myRecord[CatalogueAttributes.COPIES.ordinal()]) - copiesToRemove;
                    if (quantity > 0) {
                        myRecord[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(quantity);
                        csvWriter.writeNext(myRecord);
                    }
                } else {
                    csvWriter.writeNext(myRecord);
                }
            }

            csvWriter.flush();
        } catch (IOException | CsvValidationException e) {
            throw new PersistencyErrorException("Couldn't remove videogame from catalogue");
        } finally{
            this.lock.unlock();
        }

        try{
            Files.move(tempFile.toPath(), this.fdCatalogue.toPath(), REPLACE_EXISTING);
        } catch(IOException e){
            throw new PersistencyErrorException("Couldn't remove game from catalogue");
        }
    }
    private enum CatalogueAttributes{
        EMAIL, GAMENAME, PLATFORM, COPIES
   }
}