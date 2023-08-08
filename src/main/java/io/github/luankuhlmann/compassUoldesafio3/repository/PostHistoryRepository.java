package io.github.luankuhlmann.compassUoldesafio3.repository;

import io.github.luankuhlmann.compassUoldesafio3.domain.model.PostHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHistoryRepository extends JpaRepository<PostHistory, Long> {
}
