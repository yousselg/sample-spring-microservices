package com.yousselg.microservices.game.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class GameNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public GameNotFoundException(final String cause) {
        super("No such game with " + cause);
    }

}
