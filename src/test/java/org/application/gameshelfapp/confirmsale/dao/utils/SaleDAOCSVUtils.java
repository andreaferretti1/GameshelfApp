package org.application.gameshelfapp.confirmsale.dao.utils;

import com.opencsv.CSVWriter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import static org.junit.jupiter.api.Assertions.fail;

public class SaleDAOCSVUtils {

    public static final String DATABASE_FILE = "org/application/gameshelfapp/persistency/FileSystem/videogames_sold.csv";

    public static void insertRecord(String[][] sales){
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(DATABASE_FILE)))){
            int i = 0;
            for(String[] sale: sales){
                String[] recordToWrite = new String[sale.length + 1];
                recordToWrite[0] = String.valueOf(++i);
                System.arraycopy(sale, 0, recordToWrite, 1, sale.length);
                csvWriter.writeNext(recordToWrite);
            }
        } catch(IOException e){
            fail();
        }
    }

    public static void truncateFile(){
        try(FileOutputStream out = new FileOutputStream(DATABASE_FILE);
            FileChannel channel = out.getChannel()){
            channel.truncate(0);
        } catch(IOException e){
            fail();
        }
    }
}
