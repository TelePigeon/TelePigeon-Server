package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.dto.answer.RankAnswerDto;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
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
                () -> new NotFoundException(NotFoundErrorCode.ANSWER_NOT_FOUND)
        );
    }

    public Answer findByQuestion(final Question question){
        return answerRepository.findByQuestion(question)
                .orElseThrow(
                () -> new NotFoundException(NotFoundErrorCode.ANSWER_NOT_FOUND)
        );
    }

    public List<Answer> findAllByProfile(final Profile profile) {
        return answerRepository.findAllByProfile(profile);
    }

    public boolean existsByQuestion(final Question question){
        return answerRepository.existsByQuestion(question);
    }

    public List<RankAnswerDto> findAvgEmotion(
            final Profile profile,
            final YearMonth date
            ){
        return answerRepository.findAvgEmotionByProfileAndCreatedAtBetween(
                profile,
                date.atDay(1).atStartOfDay(),
                date.atEndOfMonth().atTime(LocalTime.MAX)
        );
    }
}
