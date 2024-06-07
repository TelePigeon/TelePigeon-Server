package com.telepigeon.server.answerTest;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AnswerDomainTest {
    @Test
    @DisplayName("Answer 객체 생성")
    void createAnswerTest(){
        AnswerCreateDto answerCreateDto = new AnswerCreateDto("content", null);
        Answer answer = Answer.create(answerCreateDto.content(), null, null, null, null);
        Assertions.assertNotNull(answer);
    }

    @Test
    @DisplayName("Answer 객체 생성 확인")
    void checkCreateAnswerTest(){
        AnswerCreateDto answerCreateDto = new AnswerCreateDto("content", null);
        Answer answer = Answer.create(answerCreateDto.content(), null, null, null, null);
        Assertions.assertEquals(answer.getContent(), "content");
    }
}
