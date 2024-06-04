package com.telepigeon.server.repository;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findFirstByProfileOrderByCreatedAtDesc(Profile profile);

    boolean existsByProfile(Profile profile);
  
    List<Question> findAllByProfile(Profile profile);

    @Query(value="select distinct q.keyword from question q where q.profile_id = :profileId order by q.created_at desc limit :count", nativeQuery=true)
    List<String> findKeywordsByProfileOrderByCreatedAtDesc(Long profileId, int count);
}
