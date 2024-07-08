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

public class CategoryDAOCSV implements CategoryDAO{
    private final File fdCategory;
    public CategoryDAOCSV(File fd){
        this.fdCategory = fd;
    }
    @Override
    public List<String> getCategories() throws PersistencyErrorException{
        List<String> categories = new ArrayList<>();
        String[] cat;
        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fdCategory)))){
            while ((cat = csvReader.readNext()) != null){
                categories.add(cat[0]);
            }
        } catch (IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't open file");
        }
        return categories;
    }
}
