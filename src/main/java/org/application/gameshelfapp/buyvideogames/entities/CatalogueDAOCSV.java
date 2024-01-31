package org.application.gameshelfapp.buyvideogames.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CatalogueDAOCSV implements CatalogueDAO {

    private final String filename;
    private final File fd;

    public CatalogueDAOCSV() {
        this.filename = "src/main/resources/org/application/gameshelfapp/persistency/catalogue.csv";
        this.fd = new File(this.filename);
    }

    @Override
    public void addVideogame(String username, Videogame game, int numberOfCopies) throws PersistencyErrorException, IOException {
        String[] record = new String[3];
        String gameName = game.getName();
        record[CatalogueAttributes.USERNAME.ordinal()] = username;
        record[CatalogueAttributes.GAMENAME.ordinal()] = gameName;
        record[CatalogueAttributes.COPIES.ordinal()] = game.getOwnerCopies().toString();
        File tempFile = File.createTempFile("catalogue", "csv");

        try (CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
            CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)))){
            while((record = csvReader.readNext()) != null){
                if(record[CatalogueAttributes.GAMENAME.ordinal()].equals(gameName)){
                    int newNumberOfCopies = numberOfCopies + Integer.parseInt(record[CatalogueAttributes.COPIES.ordinal()]);
                    record[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(newNumberOfCopies);
                }
                csvWriter.writeNext(record);
            }

            csvWriter.flush();
            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
            tempFile.delete();
        } catch (IOException | CsvValidationException e) {
            throw new PersistencyErrorException("Couldn't save game");
        }
    }

    @Override
    public void removeVideogame(String username, Videogame game, int quantity) throws PersistencyErrorException, IOException {
        String[] record;
        File tempFile = File.createTempFile("catalogue", "csv");;

        try (CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
             CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));){

            while ((record = csvReader.readNext()) != null) {

                if (record[CatalogueAttributes.GAMENAME.ordinal()].equals(game.getName())) {
                    int quantitiesInCatalogue = Integer.parseInt(record[2]);
                    int difference = quantitiesInCatalogue - quantity;
                    if (difference > 0) {
                        record[CatalogueAttributes.COPIES.ordinal()] = String.valueOf(difference);
                    }
                }
                csvWriter.writeNext(record);
            }
            csvWriter.flush();
            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
            tempFile.delete();
        } catch (IOException e) {
            throw new PersistencyErrorException("Couldn't remove videogame from catalogue");
        } catch (CsvValidationException e) {
            throw new PersistencyErrorException("Couldn't access to catalogue");
        }
    }


   private enum CatalogueAttributes{
        USERNAME, GAMENAME, COPIES;
   }
}