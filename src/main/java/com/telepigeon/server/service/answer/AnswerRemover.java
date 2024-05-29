package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerRemover {
    private final AnswerRepository answerRepository;

    public void remove(final Answer answer) {
        answerRepository.delete(answer);
    }
}
