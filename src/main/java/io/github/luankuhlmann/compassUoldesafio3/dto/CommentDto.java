package io.github.luankuhlmann.compassUoldesafio3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommentDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("email")
    private String email;

    @JsonProperty("body")
    private String body;

    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

}
