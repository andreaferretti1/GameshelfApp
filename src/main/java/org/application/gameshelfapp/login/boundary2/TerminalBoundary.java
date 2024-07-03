package org.application.gameshelfapp.login.boundary2;

import org.application.gameshelfapp.login.bean.UserBean;
import org.application.gameshelfapp.login.exception.*;

public interface TerminalBoundary {
    String executeCommand(String[] command) throws PersistencyErrorException, CheckFailedException, SyntaxErrorException, NullPasswordException, GmailException, ArrayIndexOutOfBoundsException;
    UserBean getUserBean();
}
