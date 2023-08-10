package io.github.luankuhlmann.compassUoldesafio3.exceptions;

import io.github.luankuhlmann.compassUoldesafio3.domain.PostState;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class StatusNotValidException extends RuntimeException{
    public StatusNotValidException(String msg, PostState state) {
        super(msg);
    }
}
