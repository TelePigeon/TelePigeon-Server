package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
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

    public List<Question> findAllByProfile(final Profile profile) {
        return questionRepository.findAllByProfile(profile);
    }
}
