package org.application.gameshelfapp.sellvideogames.dao;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConsoleDAOCSV implements ConsoleDAO{
    private final File fdConsole;
    public ConsoleDAOCSV(File fd){
        this.fdConsole = fd;
    }
    @Override
    public List<String> getConsoles() throws PersistencyErrorException{
        List<String> consoles = new ArrayList<>();
        String[] con;
        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(fdConsole)))){
            while ((con = csvReader.readNext()) != null){
                consoles.add(con[0]);
            }
        } catch (IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't open file");
        }
        return consoles;
    }
}
