package org.application.gameshelfapp.confirmsale.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.dao.CSVFactory;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class SaleDAOCSV implements SaleDAO {

    private final Lock lock;
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
        String[] gameSold = new String[9];

        gameSold[VideogamesSoldAttributes.GAME_ID.ordinal()] = String.valueOf(this.id);
        gameSold[VideogamesSoldAttributes.CUSTOMER_NAME.ordinal()] = sale.getName();
        gameSold[VideogamesSoldAttributes.GAME_NAME.ordinal()] = sale.getVideogameSold().getName();
        gameSold[VideogamesSoldAttributes.COPIES.ordinal()] = String.valueOf(sale.getVideogameSold().getCopies());
        gameSold[VideogamesSoldAttributes.PRICE.ordinal()] = String.valueOf(sale.getVideogameSold().getPrice());
        gameSold[VideogamesSoldAttributes.PLATFORM.ordinal()] = sale.getVideogameSold().getPlatform();
        gameSold[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()] = sale.getState();
        gameSold[VideogamesSoldAttributes.CUSTOMER_ADDRESS.ordinal()] = sale.getAddress();
        gameSold[VideogamesSoldAttributes.CUSTOMER_EMAIL.ordinal()] = sale.getEmail();

        try(CSVWriter cvsWriter = new CSVWriter(new BufferedWriter(new FileWriter(this.fd, true)))){
            cvsWriter.writeNext(gameSold);
        }catch(IOException e){
            this.id--;
            this.saveId();
            throw new PersistencyErrorException("Couldn't save sale");
        }
    }

    @Override
    public List<Sale> getConfirmedSales() throws PersistencyErrorException{
        return this.getSalesByState(Sale.CONFIRMED);
    }
    @Override
    public List<Sale> getToConfirmSales() throws PersistencyErrorException{
        return this.getSalesByState(Sale.TO_CONFIRM);
    }

    @Override
    public void updateSale(Sale sale) throws PersistencyErrorException {
        File tempFile = new File(TEMP_FILE);
        this.lock.lock();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)))
        ){
            String[] myRecord;
            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[VideogamesSoldAttributes.GAME_ID.ordinal()].equals(String.valueOf(sale.getId()))){
                    myRecord[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()] = Sale.CONFIRMED;
                }
                csvWriter.writeNext(myRecord);
            }
        } catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't confirm delivery");
        }
        try{
            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
        } catch (IOException e){
            throw new PersistencyErrorException("Couldn't confirm delivery");
        }
    }

    @Override
    public Sale getSaleToConfirmById(long id) throws PersistencyErrorException, WrongSaleException {
        List<Sale> sales = this.getToConfirmSales();
        Sale saleToConfirm = null;
        for(Sale sale: sales){
            if(sale.getId() == id && sale.getState().equals(Sale.TO_CONFIRM)) saleToConfirm = sale;
        }
        if(saleToConfirm == null) throw new WrongSaleException("Sale has been already cofirmed or doesn't exists");
        return saleToConfirm;
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
        Properties properties = new Properties();
        try(FileInputStream in = new FileInputStream(CSVFactory.PROPERTIES)){
            properties.load(in);
        } catch (IOException e){
            throw new PersistencyErrorException("Couldn't access to sales.");
        }
        properties.setProperty("ID", Long.toString(this.id));
        try(FileOutputStream out = new FileOutputStream(CSVFactory.PROPERTIES)){
            properties.store(out, null);
        } catch(IOException e){
            throw new PersistencyErrorException("Couldn't access to sales");
        }
    }

    private List<Sale> getSalesByState(String state) throws PersistencyErrorException{
        List<Sale> sales = new ArrayList<>();
        String[] myRecord;

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)))){
            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()].equals(state)){
                    Videogame gameSold = new Videogame(myRecord[VideogamesSoldAttributes.GAME_NAME.ordinal()], Integer.parseInt(myRecord[VideogamesSoldAttributes.COPIES.ordinal()]), Float.parseFloat(myRecord[VideogamesSoldAttributes.PRICE.ordinal()]), null, myRecord[VideogamesSoldAttributes.PLATFORM.ordinal()], null);
                    Sale sale = new Sale(Integer.parseInt(myRecord[VideogamesSoldAttributes.GAME_ID.ordinal()]), gameSold, myRecord[VideogamesSoldAttributes.CUSTOMER_EMAIL.ordinal()], myRecord[VideogamesSoldAttributes.CUSTOMER_ADDRESS.ordinal()], state, myRecord[VideogamesSoldAttributes.CUSTOMER_NAME.ordinal()]);
                    sales.add(sale);
                }
            }
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't get sales");
        }
        return sales;
    }
    private enum VideogamesSoldAttributes{
        GAME_ID, COPIES, PRICE, STATE_OF_DELIVERY, GAME_NAME , PLATFORM, CUSTOMER_NAME, CUSTOMER_EMAIL, CUSTOMER_ADDRESS
    }
}
