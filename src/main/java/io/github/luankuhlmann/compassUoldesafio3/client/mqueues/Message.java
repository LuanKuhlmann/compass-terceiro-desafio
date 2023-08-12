package io.github.luankuhlmann.compassUoldesafio3.client.mqueues;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    private Long id;
    private String content;
    private Date date;
}
