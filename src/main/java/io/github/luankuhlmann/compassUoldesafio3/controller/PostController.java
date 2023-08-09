package io.github.luankuhlmann.compassUoldesafio3.controller;

import io.github.luankuhlmann.compassUoldesafio3.dto.request.PostDto;
import io.github.luankuhlmann.compassUoldesafio3.services.PostService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping("/{postId}")
    public ResponseEntity<Void> processPost(@PathVariable @Min(value = 1, message = "id must be between 1 and 100") @Max(value = 100, message = "id must be between 1 and 100") Long postId) {
        return null;
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
