package org.application.gameshelfapp.buyvideogames.dao.utils;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.junit.jupiter.api.Assertions.fail;

public class ItemDAOCSVUtils {
    public static final String TEMP_FILE = "tempFile_test.csv";
    public static final String DATABASE_FILE = "src/main/resources/org/application/gameshelfapp/persistency/FileSystem/videogames_on_sale.csv";
    public static void insertRecord(String[][] records){
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(TEMP_FILE)));){
            for(String[] recrodToWrite: records) {
                csvWriter.writeNext(recrodToWrite);
            }
        } catch (IOException e) {
            fail();
        }

        try{
            Files.move(Paths.get(TEMP_FILE), Paths.get(DATABASE_FILE), REPLACE_EXISTING);
        } catch(IOException e){
            fail();
        }
    }

    public static void truncateFile(){
        try(FileOutputStream out = new FileOutputStream(DATABASE_FILE);
            FileChannel channel = out.getChannel()) {
            channel.truncate(0);
        } catch(IOException e){
            fail();
        }
    }
}
