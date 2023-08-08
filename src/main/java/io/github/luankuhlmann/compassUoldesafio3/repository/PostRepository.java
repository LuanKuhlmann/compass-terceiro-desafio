package io.github.luankuhlmann.compassUoldesafio3.repository;

import io.github.luankuhlmann.compassUoldesafio3.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
