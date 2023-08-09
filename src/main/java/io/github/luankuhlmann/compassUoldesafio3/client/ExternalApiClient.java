package io.github.luankuhlmann.compassUoldesafio3.client;

import io.github.luankuhlmann.compassUoldesafio3.dto.request.PostDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "ExternalApiClient", url = "https://jsonplaceholder.typicode.com/posts")
public interface ExternalApiClient {

    @GetMapping("/{postId}")
    PostDto findPostById(@PathVariable("postId") long postId);

    @GetMapping("/{postId}/comments")
    PostDto findCommentsByPostId(@PathVariable("postId") long postId);
}
