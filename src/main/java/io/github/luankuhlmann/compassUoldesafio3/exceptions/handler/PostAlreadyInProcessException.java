package io.github.luankuhlmann.compassUoldesafio3.exceptions.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class PostAlreadyInProcessException extends RuntimeException{
    public PostAlreadyInProcessException(String msg, Long postId) {
        super(msg);
    }
}
