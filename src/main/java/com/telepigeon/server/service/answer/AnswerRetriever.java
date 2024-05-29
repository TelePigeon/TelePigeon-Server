package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AnswerRetriever {
    private final AnswerRepository answerRepository;

    public List<Answer> findAllByRoomAndDate(
            final List<Profile> profiles,
            final LocalDate date
    ){
        return answerRepository
                .findAllByProfileInAndCreatedAtBetweenOrderByCreatedAt(
                        profiles, date.atStartOfDay(),
                        date.atTime(LocalTime.MAX)
                );
    }

    public Answer findFirstByProfile(final Profile profile){
        return answerRepository
                .findFirstByProfileOrderByCreatedAtDesc(profile)
                .orElseThrow(
                RuntimeException::new // Hurry merge 후 NotFoundException으로 바꿀 예정(충돌 때문)
        );
    }

    public List<Answer> findAllByProfile(final Profile profile) {
        return answerRepository.findAllByProfile(profile);
    }

    public boolean existsByQuestion(final Question question){
        return answerRepository.existsByQuestion(question);
    }
}
