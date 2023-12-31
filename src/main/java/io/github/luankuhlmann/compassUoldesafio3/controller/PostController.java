package io.github.luankuhlmann.compassUoldesafio3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.services.PostService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @PostMapping("/{postId}")
    public CompletableFuture<ResponseEntity<Void>> processPost(@PathVariable(name = "postId") @Min(value = 1, message = "id must be between 1 and 100") @Max(value = 100, message = "id must be between 1 and 100") Long postId) throws JsonProcessingException {
        postService.processPost(postId);

        return CompletableFuture.completedFuture(ResponseEntity.ok(null));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> disablePost(@PathVariable(name = "postId") @Min(value = 1, message = "id must be between 1 and 100") @Max(value = 100, message = "id must be between 1 and 100") Long postId){
        postService.disablePost(postId);

        return ResponseEntity.ok(null);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> reprocessPost(@PathVariable(name = "postId") @Min(value = 1, message = "id must be between 1 and 100") @Max(value = 100, message = "id must be between 1 and 100") Long postId) throws JsonProcessingException {
        postService.reprocessPost(postId);

        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<List<Post>> queryPosts() {
        var response = postService.queryPosts();
        return ResponseEntity.ok(response);
    }

}
