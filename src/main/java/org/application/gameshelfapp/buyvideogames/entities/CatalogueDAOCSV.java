package org.application.gameshelfapp.buyvideogames.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CatalogueDAOCSV implements CatalogueDAO {

    private static final String TEMP_FILE = "temp_catalogue.csv";
    private static final String SAVE_ERROR = "Couldn't save videogame";
    private final File fdCatalogue;
    private final Lock lock;

    public CatalogueDAOCSV() throws PersistencyErrorException{
        try(FileInputStream in = new FileInputStream("/src/main/resources/org/application/gameshelfapp/configuration/configuration.properties")){
            Properties properties = new Properties();

            properties.load(in);
            String s = properties.getProperty("CSV_CATALOGUE");

            this.fdCatalogue = new File(s);
            this.lock = new ReentrantLock();
        } catch(IOException e){
            throw new PersistencyErrorException("Couldn't access to catalogue");
        }
    }

    @Override
    public void addVideogame(String username, Videogame game, int numberOfCopies) throws PersistencyErrorException {
        String[] myRecord = new String[3];
        String gameName = game.getName();
        myRecord[CatalogueAttributes.USERNAME.ordinal()] = username;
        myRecord[CatalogueAttributes.GAMENAME.ordinal()] = gameName;
        myRecord[CatalogueAttributes.COPIES.ordinal()] = game.getOwnerCopies().toString();
        File tempFile = new File(TEMP_FILE);


        this.lock.lock();

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
             CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdCatalogue)));
             FileChannel channel = new FileOutputStream(new File(TEMP_FILE)).getChannel()){

            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException(SAVE_ERROR);
            }
            channel.truncate(0);

            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[CatalogueAttributes.GAMENAME.ordinal()].equals(gameName)){
                    int newNumberOfCopies = numberOfCopies + Integer.parseInt(myRecord[CatalogueAttributes.COPIES.ordinal()]);
                    myRecord[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(newNumberOfCopies);
                }
                csvWriter.writeNext(myRecord);
            }

            csvWriter.flush();
            Files.move(tempFile.toPath(), this.fdCatalogue.toPath(), REPLACE_EXISTING);
        } catch (IOException | CsvValidationException e) {
            throw new PersistencyErrorException(SAVE_ERROR);
        }finally{this.lock.unlock();}
    }

    @Override
    public void removeVideogame(String username, Videogame game, int quantity) throws PersistencyErrorException {
        String[] myRecord;
        File tempFile = new File(TEMP_FILE);

        this.lock.lock();
        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdCatalogue)));
             CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
             FileChannel channel = new FileOutputStream(tempFile).getChannel()){

            if(!tempFile.exists()) {
                boolean created = tempFile.createNewFile();
                if(!created) throw new PersistencyErrorException(SAVE_ERROR);
            }
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
            Files.move(tempFile.toPath(), this.fdCatalogue.toPath(), REPLACE_EXISTING);
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