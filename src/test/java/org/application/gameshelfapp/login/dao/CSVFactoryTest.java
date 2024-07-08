package org.application.gameshelfapp.login.dao;

import org.application.gameshelfapp.buyvideogames.dao.CatalogueDAOCSV;
import org.application.gameshelfapp.buyvideogames.dao.ItemDAOCSV;
import org.application.gameshelfapp.confirmsale.dao.SaleDAOCSV;
import org.application.gameshelfapp.login.exception.PersistencyErrorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.fail;

class CSVFactoryTest {
// getFile non è stato testato perchè viene chiamato ogni volta che creo un DAO.
@Test
void createItemDAOTest() {
    try {
        CSVFactory csvFactory = new CSVFactory();
        assertInstanceOf(ItemDAOCSV.class, csvFactory.createItemDAO());
    } catch(PersistencyErrorException e){
        fail();
    }
}

    @Test
    void createCatalogueDAOTest() {
        try {
            CSVFactory csvFactory = new CSVFactory();
            assertInstanceOf(CatalogueDAOCSV.class, csvFactory.createCatalogueDAO());
        } catch(PersistencyErrorException e){
            fail();
        }
}

    @Test
    void createAccessDAOTest() {
        try {
            CSVFactory csvFactory = new CSVFactory();
            assertInstanceOf(AccessDAOCSV.class, csvFactory.createAccessDAO());
        } catch(PersistencyErrorException e){
            fail();
        }
}

    @Test
    void createSaleDAOTest() {
        try {
            CSVFactory csvFactory = new CSVFactory();
            assertInstanceOf(SaleDAOCSV.class,csvFactory.createSaleDAO());
        } catch(PersistencyErrorException e){
            fail();
        }
}
}