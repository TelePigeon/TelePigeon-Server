package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.dto.answer.RankAnswerDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    Optional<Answer> findByQuestion(Question question);
  
    List<Answer> findAllByProfile(Profile profile);

    Boolean existsByQuestion(Question question);

    @Query(value="select new com.telepigeon.server.dto.answer.RankAnswerDto(a.question.keyword, avg(a.emotion)) " +
            "from Answer a where a.profile = :profile and a.createdAt between :startTime and :endTime " +
            "group by a.question.keyword order by avg(a.emotion) desc")
    List<RankAnswerDto> findAvgEmotionByProfileAndCreatedAtBetween(
            Profile profile,
            LocalDateTime startTime,
            LocalDateTime endTime
    );
}
