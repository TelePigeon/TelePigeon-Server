package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuestionSaver {
    private final QuestionRepository questionRepository;

    public String create(String content, Profile profile){
        Question question = Question.create(content, profile);
        questionRepository.save(question);
        return question.getId().toString();
    }
}
