package io.github.luankuhlmann.compassUoldesafio3.domain.model;

import io.github.luankuhlmann.compassUoldesafio3.domain.PostState;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "history")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Instant date;

    @Enumerated(EnumType.STRING)
    private PostState status;

//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    @JsonBackReference
//    private Post post;

    @Column(name = "post_id")
    Long postId;

    public PostHistory(PostState status, Long postId) {
        this.status = status;
        this.postId = postId;
        this.date = Instant.now();
    }
}
