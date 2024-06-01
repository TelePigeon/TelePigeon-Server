package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerSaver {
    private final AnswerRepository answerRepository;

    @Transactional
    public Answer create(final Answer answer){
        return answerRepository.save(answer);
    }
}
