package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerEditor {
    public void updateIsViewed(Answer answer, boolean isViewed){
        answer.updateIsViewed(isViewed);
    }
}
