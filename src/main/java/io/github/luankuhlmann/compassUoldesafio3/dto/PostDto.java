package io.github.luankuhlmann.compassUoldesafio3.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

    @JsonProperty("comments")
    private List<CommentDto> comments;

    @JsonProperty("history")
    private List<PostHistory> history;

}
