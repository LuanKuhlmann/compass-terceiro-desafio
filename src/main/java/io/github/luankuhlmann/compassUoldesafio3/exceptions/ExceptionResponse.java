package io.github.luankuhlmann.compassUoldesafio3.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExceptionResponse {

    private Instant timestamp;
    private String msg;
    private String details;
}
