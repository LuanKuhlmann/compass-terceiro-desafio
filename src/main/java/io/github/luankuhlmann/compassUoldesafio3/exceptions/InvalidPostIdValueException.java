package io.github.luankuhlmann.compassUoldesafio3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class InvalidPostIdValueException extends RuntimeException{
    public InvalidPostIdValueException (String msg) {
        super(msg);
    }
}
