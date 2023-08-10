package io.github.luankuhlmann.compassUoldesafio3.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.luankuhlmann.compassUoldesafio3.domain.PostState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PostHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant date;

    @Enumerated(EnumType.STRING)
    private PostState status;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonBackReference
    private Post post;

    public PostHistory(Instant date, PostState status) {
        this.date = date;
        this.status = status;
    }
}
