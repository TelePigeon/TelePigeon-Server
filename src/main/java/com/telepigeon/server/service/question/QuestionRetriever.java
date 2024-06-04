package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class QuestionRetriever {
    private final QuestionRepository questionRepository;

    public Question findFirstByProfile(final Profile profile){
        return questionRepository
                .findFirstByProfileOrderByCreatedAtDesc(profile)
                .orElse(null); //예외 처리를 해주지 말아야 할 때도 있어서 null 반환
    }

    public Question findById(final Long questionId){
        return questionRepository.findById(questionId)
                .orElseThrow(
                        () -> new NotFoundException(NotFoundErrorCode.QUESTION_NOT_FOUND)
                );
    }

    public boolean existsByProfile(final Profile profile){
        return questionRepository.existsByProfile(profile);
    }

    public List<Question> findAllByProfile(final Profile profile) {
        return questionRepository.findAllByProfile(profile);
    }

    public List<String> findKeywordsByProfile(
            final Long profileId,
            final int count
    ){
        return questionRepository.findKeywordsByProfileOrderByCreatedAtDesc(profileId, count);
    }
}
