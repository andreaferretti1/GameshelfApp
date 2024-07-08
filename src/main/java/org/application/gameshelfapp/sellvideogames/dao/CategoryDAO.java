package org.application.gameshelfapp.sellvideogames.dao;

import org.application.gameshelfapp.login.exception.PersistencyErrorException;

import java.util.List;

public interface CategoryDAO {
    List<String> getCategories() throws PersistencyErrorException;
}
