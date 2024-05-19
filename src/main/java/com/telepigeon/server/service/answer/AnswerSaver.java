package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerSaver {
    private final AnswerRepository answerRepository;

    public Answer create(final Answer answer){
        return answerRepository.save(answer);
    }
}
