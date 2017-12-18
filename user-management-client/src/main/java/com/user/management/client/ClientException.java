package com.user.management.client;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Error with user management client")
public class ClientException extends RuntimeException {

    public ClientException(Throwable cause) {
        super(cause);
    }

}
