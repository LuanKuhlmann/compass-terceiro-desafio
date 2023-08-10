package io.github.luankuhlmann.compassUoldesafio3.controller;

import io.github.luankuhlmann.compassUoldesafio3.domain.model.Post;
import io.github.luankuhlmann.compassUoldesafio3.dto.PostDto;
import io.github.luankuhlmann.compassUoldesafio3.services.PostService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/{postId}")
    public ResponseEntity<Post> processPost(@PathVariable(name = "postId") @Min(value = 1, message = "id must be between 1 and 100") @Max(value = 100, message = "id must be between 1 and 100") Long postId) {
        Post response = postService.processPost(postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> disablePost(@PathVariable Long postId) {
        return null;
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> reprocessPost(@PathVariable Long postId) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> queryPosts() {
        return null;
    }

}
