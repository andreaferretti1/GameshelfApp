package org.application.gameshelfapp.buyvideogames.entities;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SaleDAOCSV implements SaleDAO{

    private Lock lock;
    public static final String TEMP_FILE = "items_sold.csv";
    private final File fd;
    private long id;
    public SaleDAOCSV(File fd) throws PersistencyErrorException{
        this.lock = new ReentrantLock();
        this.id = this.getId();
        this.fd = fd;
    }
    @Override
    public void saveSale(Sale sale) throws PersistencyErrorException {
        this.id++;
        this.saveId();
        String[] gameSold = new String[7];

        gameSold[VideogamesSoldAttributes.GAMEID.ordinal()] = String.valueOf(this.id);
        gameSold[VideogamesSoldAttributes.GAMENAME.ordinal()] = sale.getVideogameSold().getName();
        gameSold[VideogamesSoldAttributes.COPIES.ordinal()] = String.valueOf(sale.getVideogameSold().getCopies());
        gameSold[VideogamesSoldAttributes.PRICE.ordinal()] = String.valueOf(sale.getVideogameSold().getPrice());
        gameSold[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()] = sale.getState();
        gameSold[VideogamesSoldAttributes.CUSTOMERADDRESS.ordinal()] = sale.getAddress();
        gameSold[VideogamesSoldAttributes.CUSTOMEREMAIL.ordinal()] = sale.getEmail();

        try(CSVWriter cvsWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fd, true)))){
            cvsWriter.writeNext(gameSold);
        }catch(IOException e){
            throw new PersistencyErrorException("Couldn't save sale");
        }
    }

    @Override
    public List<Sale> getSales() throws PersistencyErrorException {
        List<Sale> sales = new ArrayList<Sale>();
        String[] myRecord = null;

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)))){
            while((myRecord = csvReader.readNext()) != null){
                Videogame gameSold = new Videogame(myRecord[VideogamesSoldAttributes.GAMENAME.ordinal()], Integer.parseInt(myRecord[VideogamesSoldAttributes.COPIES.ordinal()]), Float.parseFloat(myRecord[VideogamesSoldAttributes.PRICE.ordinal()]), null);
                Sale sale = new Sale(gameSold, myRecord[VideogamesSoldAttributes.CUSTOMEREMAIL.ordinal()], myRecord[VideogamesSoldAttributes.CUSTOMERADDRESS.ordinal()], myRecord[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()], myRecord[VideogamesSoldAttributes.PLATFORM.ordinal()]);
                sale.setId(Integer.parseInt(myRecord[VideogamesSoldAttributes.GAMEID.ordinal()]));
                sales.add(sale);
            }
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't get sales");
        }
        return sales;
    }

    @Override
    public void updateSale(Sale sale) throws PersistencyErrorException {
        File tempFile = new File(TEMP_FILE);
        this.lock.lock();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)));
        ){
            String[] myRecord;
            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[VideogamesSoldAttributes.GAMEID.ordinal()].equals(String.valueOf(sale.getId()))){
                    myRecord[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()] = Sale.CONFIRMED;
                }
                csvWriter.writeNext(myRecord);
            }
            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't confirm delivery");
        }
    }

    private long getId() throws PersistencyErrorException{
        try(FileInputStream in = new FileInputStream(CSVFactory.PROPERTIES)){
            Properties properties = new Properties();
            properties.load(in);
            return Long.parseLong(properties.getProperty("ID"));
        } catch (IOException e){
            throw new PersistencyErrorException("Couldn't access to sales.");
        }
    }

    private void saveId() throws PersistencyErrorException{
        try(FileInputStream in = new FileInputStream(CSVFactory.PROPERTIES)){
            Properties properties = new Properties();
            properties.load(in);
            properties.setProperty("ID", Long.toString(this.id));
        } catch (IOException e){
            throw new PersistencyErrorException("Couldn't access to sales.");
        }
    }

    private enum VideogamesSoldAttributes{
        GAMEID, GAMENAME, COPIES, PRICE, PLATFORM, STATE_OF_DELIVERY, CUSTOMERADDRESS, CUSTOMEREMAIL
    }
}