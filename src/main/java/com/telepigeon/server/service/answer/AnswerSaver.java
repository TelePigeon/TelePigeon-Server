package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import com.telepigeon.server.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerSaver {
    private final AnswerRepository answerRepository;

    public Answer create(
            final AnswerCreateDto answerCreateDto,
            final Question question,
            final Profile profile
    ){
        Answer answer = Answer.create(answerCreateDto, question, profile);
        return answerRepository.save(answer);
    }
}
