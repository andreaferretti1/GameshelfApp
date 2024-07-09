package org.application.gameshelfapp.login.boundary2;

import org.application.gameshelfapp.buyvideogames.exception.*;
import org.application.gameshelfapp.confirmsale.exceptions.ConfirmDeliveryException;
import org.application.gameshelfapp.confirmsale.exceptions.WrongSaleException;
import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;
import org.application.gameshelfapp.sellvideogames.exception.AlreadyExistingVideogameException;
import org.application.gameshelfapp.sellvideogames.exception.InvalidTitleException;
import org.application.gameshelfapp.sellvideogames.exception.NoGameInCatalogueException;

public interface TerminalBoundary {
    String executeCommand(String[] command) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException, GmailException, ArrayIndexOutOfBoundsException, NoGameInCatalogueException, InvalidTitleException, AlreadyExistingVideogameException, GameSoldOutException, RefundException, InvalidAddressException, ConfirmDeliveryException, WrongSaleException, WrongUserTypeException;
    UserBean getUserBean();
}
