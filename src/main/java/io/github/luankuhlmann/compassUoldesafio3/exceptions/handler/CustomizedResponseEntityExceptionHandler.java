package io.github.luankuhlmann.compassUoldesafio3.exceptions.handler;

import io.github.luankuhlmann.compassUoldesafio3.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Clock;
import java.time.Instant;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handlerException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(Clock.systemDefaultZone()),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    };

    @ExceptionHandler(PostAlreadyInProcessException.class)
    public final ResponseEntity<ExceptionResponse> handlerPostAreadyInProcessException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(Clock.systemDefaultZone()),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPostIdValueException.class)
    public final ResponseEntity<ExceptionResponse> handlerInvalidPostIdException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(Clock.systemDefaultZone()),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StatusNotValidException.class)
    public final ResponseEntity<ExceptionResponse> handlerStatusNotValidException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(Clock.systemDefaultZone()),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlerCommentNotFoundException(Exception e, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                Instant.now(Clock.systemDefaultZone()),
                e.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.NOT_FOUND);
    }


}
