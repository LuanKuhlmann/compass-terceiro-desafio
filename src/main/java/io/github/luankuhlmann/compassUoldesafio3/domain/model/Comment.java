package io.github.luankuhlmann.compassUoldesafio3.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    private String body;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    @JsonBackReference
//    private Post post;

    @Column(name = "post_id")
    Long postId;

}
