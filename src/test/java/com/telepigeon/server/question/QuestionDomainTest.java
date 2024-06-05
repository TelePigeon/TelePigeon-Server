package com.telepigeon.server.question;

import com.telepigeon.server.domain.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

public class QuestionDomainTest {
    @Test
    @DisplayName("Question 객체 생성")
    void createQuestionTest(){
        Question question = Question.create("건강", "hi", null);
        Assertions.assertNotNull(question);
    }

    @Test
    @DisplayName("Question 객체 생성 확인")
    void checkCreateQuestionTest(){
        Question question = Question.create("건강", "hi", null);
        Assertions.assertEquals(question.getContent(), "hi");
    }

    @Test
    @DisplayName("json 파일 생성 확인")
    public void checkFile(){
        File file = new File("src/main/resources/firebase.json");
        Assertions.assertTrue(file.exists());
    }
}
