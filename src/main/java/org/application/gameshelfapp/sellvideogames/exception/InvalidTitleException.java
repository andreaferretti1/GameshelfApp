package org.application.gameshelfapp.sellvideogames.exception;

public class InvalidTitleException extends Exception {
    public InvalidTitleException(String titleIsInvalid) {
        super(titleIsInvalid);
    }
}
