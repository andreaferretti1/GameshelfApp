package org.application.gameshelfapp.confirmsale.dao;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.application.gameshelfapp.buyvideogames.entities.Credentials;
import org.application.gameshelfapp.buyvideogames.entities.Videogame;
import org.application.gameshelfapp.confirmsale.entities.Sale;
import org.application.gameshelfapp.login.dao.CSVFactory;
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
        this.getId();
        this.fd = fd;
    }
    @Override
    public void saveSale(Sale sale) throws PersistencyErrorException {
        this.lock.lock();
        File tempFile = new File(TEMP_FILE);
        boolean saved = false;
        String[] gameSold = new String[9];

        gameSold[VideogamesSoldAttributes.CUSTOMER_NAME.ordinal()] = sale.getCredentials().getName();
        gameSold[VideogamesSoldAttributes.GAME_NAME.ordinal()] = sale.getVideogameSold().getName();
        gameSold[VideogamesSoldAttributes.COPIES.ordinal()] = String.valueOf(sale.getVideogameSold().getCopies());
        gameSold[VideogamesSoldAttributes.PRICE.ordinal()] = String.valueOf(sale.getVideogameSold().getPrice());
        gameSold[VideogamesSoldAttributes.PLATFORM.ordinal()] = sale.getVideogameSold().getPlatform();
        gameSold[VideogamesSoldAttributes.STATE_OF_DELIVERY.ordinal()] = sale.getState();
        gameSold[VideogamesSoldAttributes.CUSTOMER_ADDRESS.ordinal()] = sale.getCredentials().getAddress();
        gameSold[VideogamesSoldAttributes.CUSTOMER_EMAIL.ordinal()] = sale.getCredentials().getEmail();

        try(CSVReader csvReader = new CSVReader(new BufferedReader(new FileReader(this.fd)));
            CSVWriter csvWriter= new CSVWriter(new BufferedWriter(new FileWriter(tempFile)))
        ){
            String[] myRecord;
            while((myRecord = csvReader.readNext()) != null){
                if(myRecord[VideogamesSoldAttributes.GAME_ID.ordinal()].equals(String.valueOf(sale.getId()))){
                    gameSold[VideogamesSoldAttributes.GAME_ID.ordinal()] = String.valueOf(sale.getId());
                    csvWriter.writeNext(gameSold);
                    saved = true;
                    continue;
                }
                csvWriter.writeNext(myRecord);
            }

            if(!saved){
                this.id++;
                gameSold[VideogamesSoldAttributes.GAME_ID.ordinal()] = String.valueOf(this.id);
                csvWriter.writeNext(gameSold);
                this.saveId();
            }
        }catch(IOException | CsvValidationException e){
            this.saveId();
            throw new PersistencyErrorException("Couldn't save sale");
        } finally{
            this.lock.unlock();
        }

        try{
            Files.move(tempFile.toPath(), this.fd.toPath(), REPLACE_EXISTING);
        } catch (IOException e){
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

    private void getId() throws PersistencyErrorException{
        try(FileInputStream in = new FileInputStream(CSVFactory.PROPERTIES)){
            Properties properties = new Properties();
            properties.load(in);
            this.id = Long.parseLong(properties.getProperty("ID"));
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
                    Credentials credentials = new Credentials(myRecord[VideogamesSoldAttributes.CUSTOMER_NAME.ordinal()], myRecord[VideogamesSoldAttributes.CUSTOMER_ADDRESS.ordinal()], myRecord[VideogamesSoldAttributes.CUSTOMER_EMAIL.ordinal()]);
                    Sale sale = new Sale(Integer.parseInt(myRecord[VideogamesSoldAttributes.GAME_ID.ordinal()]), gameSold, credentials, state);
                    sales.add(sale);
                }
            }
        }catch(IOException | CsvValidationException e){
            throw new PersistencyErrorException("Couldn't get sales");
        }
        return sales;
    }
    private enum VideogamesSoldAttributes{
        GAME_ID, CUSTOMER_NAME, GAME_NAME, COPIES, PRICE, PLATFORM, STATE_OF_DELIVERY, CUSTOMER_ADDRESS, CUSTOMER_EMAIL
    }
}
