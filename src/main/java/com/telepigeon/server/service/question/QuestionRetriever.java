package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionRetriever {
    private final QuestionRepository questionRepository;

    public Question findFirstByProfile(final Profile profile){
        return questionRepository.findFirstByProfileOrderByCreatedAtDesc(profile)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundErrorCode.QUESTION_NOT_FOUND)
                );
    }

    public Question findById(final Long questionId){
        return questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundErrorCode.QUESTION_NOT_FOUND)
                );
    }
}
