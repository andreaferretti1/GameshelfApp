package org.application.gameshelfapp.buyvideogames.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CatalogueDAOCSV implements CatalogueDAO {

    private final String filename;
    private final File fd;
    private final Lock lock;

    public CatalogueDAOCSV() {
        this.filename = "src/main/resources/org/application/gameshelfapp/persistency/catalogue.csv";
        this.fd = new File(this.filename);
        this.lock = new ReentrantLock();
    }

    @Override
    public void addVideogame(String username, Videogame game, int numberOfCopies) throws PersistencyErrorException, IOException {
        String[] myRecord = new String[3];
        String gameName = game.getName();
        myRecord[CatalogueAttributes.USERNAME.ordinal()] = username;
        myRecord[CatalogueAttributes.GAMENAME.ordinal()] = gameName;
        myRecord[CatalogueAttributes.COPIES.ordinal()] = game.getOwnerCopies().toString();
        File tempFile = new File("temp_catalogue.csv");

        if(!tempFile.exists()) {
            boolean created = tempFile.createNewFile();
            if(!created) throw new PersistencyErrorException("Couldn't save game");
        }

        this.lock.lock();

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
             CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
             FileChannel channel = new FileOutputStream(tempFile).getChannel()){

            channel.truncate(0);
            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[CatalogueAttributes.GAMENAME.ordinal()].equals(gameName)){
                    int newNumberOfCopies = numberOfCopies + Integer.parseInt(myRecord[CatalogueAttributes.COPIES.ordinal()]);
                    myRecord[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(newNumberOfCopies);
                }
                csvWriter.writeNext(myRecord);
            }

            csvWriter.flush();
            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
        } catch (IOException | CsvValidationException e) {
            throw new PersistencyErrorException("Couldn't save game");
        }finally{this.lock.unlock();}
    }

    @Override
    public void removeVideogame(String username, Videogame game, int quantity) throws PersistencyErrorException, IOException {
        String[] myRecord;
        File tempFile = new File("temp_catalogue");
        if(!tempFile.exists()) {
            boolean created = tempFile.createNewFile();
            if(!created) throw new PersistencyErrorException("Couldn't save game");
        }

        this.lock.lock();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
             CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
             FileChannel channel = new FileOutputStream(tempFile).getChannel()){

            channel.truncate(0);
            while ((myRecord = csvReader.readNext()) != null) {

                if (myRecord[CatalogueAttributes.GAMENAME.ordinal()].equals(game.getName())) {
                    int quantitiesInCatalogue = Integer.parseInt(myRecord[2]);
                    int difference = quantitiesInCatalogue - quantity;
                    if (difference > 0) {
                        myRecord[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(difference);
                    }
                }
                csvWriter.writeNext(myRecord);
            }
            csvWriter.flush();
            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            throw new PersistencyErrorException("Couldn't remove videogame from catalogue");
        } catch (CsvValidationException e) {
            throw new PersistencyErrorException("Couldn't access to catalogue");
        }finally{
            this.lock.unlock();
        }
    }


   private enum CatalogueAttributes{
        USERNAME, GAMENAME, COPIES;
   }
}