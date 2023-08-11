package io.github.luankuhlmann.compassUoldesafio3.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comment {

    @Id
    private Long id;

    @Column(length = 500)
    private String body;

    @Column(name = "post_id")
    Long postId;

}
