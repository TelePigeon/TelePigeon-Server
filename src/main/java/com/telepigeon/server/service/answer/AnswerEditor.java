package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AnswerEditor {

    @Transactional
    public void updateIsViewed(
            final Answer answer,
            final boolean isViewed
    ){
        answer.updateIsViewed(isViewed);
    }
}
