package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class QuestionRetriever {
    private final QuestionRepository questionRepository;

    public Question findFirstByProfile(Profile profile){
        return questionRepository.findFirstByProfileOrderByCreatedAtDesc(profile).orElseThrow(
                RuntimeException::new // Hurry merge 후 NotFoundException으로 바꿀 예정(충돌 때문)
        );
    }
}
