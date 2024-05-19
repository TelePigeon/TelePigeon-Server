package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByProfileInAndCreatedAtBetweenOrderByCreatedAt(
            List<Profile> profiles,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
    Optional<Answer> findFirstByProfileOrderByCreatedAtDesc(Profile profile);

    Boolean existsByQuestion(Question question);
}
