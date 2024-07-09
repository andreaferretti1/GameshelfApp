package org.application.gameshelfapp.login.dao.utils;

import com.opencsv.CSVWriter;

import java.io.*;
import java.nio.channels.FileChannel;

import static org.junit.jupiter.api.Assertions.fail;

public class AccessDAOCSVUtils {

    public static final String DATABASE_FILE = "src/main/resources/org/application/gameshelfapp/persistency/FileSystem/accounts.csv";

    public static void insertRecord(String[][] records){
        try(CSVWriter csvWriter = new CSVWriter(new BufferedWriter(new FileWriter(DATABASE_FILE)))){
            for(String[] myRecord : records){
                csvWriter.writeNext(myRecord);
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
