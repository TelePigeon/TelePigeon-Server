package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findFirstByProfileOrderByCreatedAtDesc(Profile profile);
}
