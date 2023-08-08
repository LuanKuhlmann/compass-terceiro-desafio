package io.github.luankuhlmann.compassUoldesafio3.repository;

import io.github.luankuhlmann.compassUoldesafio3.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
