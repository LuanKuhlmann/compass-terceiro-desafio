package io.github.luankuhlmann.compassUoldesafio3.client;

import io.github.luankuhlmann.compassUoldesafio3.domain.model.Comment;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
@FeignClient(value = "ExternalApiClient", url = "https://jsonplaceholder.typicode.com/posts")
public interface ExternalApiClient {

    @GetMapping
    List<Post> getAllPost();

    @GetMapping("/{postId}")
    Post findPostById(@PathVariable("postId") long postId);

    @GetMapping("/{postId}/comments")
    List<Comment> findCommentByPostId(@PathVariable("postId") long postId);

}
