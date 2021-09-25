package com.yousselg.microservices.player.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class PlayerNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public PlayerNotFoundException(final String cause) {
        super("No such player with " + cause);
    }

}
