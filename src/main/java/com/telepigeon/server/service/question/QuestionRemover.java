package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.Question;
import com.telepigeon.server.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionRemover {

    private final QuestionRepository questionRepository;

    public void remove(final Question question) {
        questionRepository.delete(question);
    }
}
