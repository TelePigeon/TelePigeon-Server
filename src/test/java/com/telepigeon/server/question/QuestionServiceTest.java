package com.telepigeon.server.question;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.question.response.GetLastQuestionDto;
import com.telepigeon.server.dto.type.Relation;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.service.answer.AnswerRetriever;
import com.telepigeon.server.service.hurry.HurryRetriever;
import com.telepigeon.server.service.openAi.OpenAiService;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.question.QuestionSaver;
import com.telepigeon.server.service.question.QuestionService;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.user.UserRetriever;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;


public class QuestionServiceTest {
    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionSaver questionSaver;

    @Mock
    private QuestionRetriever questionRetriever;

    @Mock
    private RoomRetriever roomRetriever;

    @Mock
    private AnswerRetriever answerRetriever;

    @Mock
    private ProfileRetriever profileRetriever;

    @Mock
    private UserRetriever userRetriever;

    @Mock
    private OpenAiService openAiService;

    @Mock
    private HurryRetriever hurryRetriever;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Question 생성 로직 확인 - 아직 질문이 없을 경우")
    void checkCreateQuestion1(){
        Room room = Mockito.mock(Room.class);
        User user = Mockito.mock(User.class);
        Relation relation = Relation.FRIEND;
        Profile profile = new Profile(user, room, relation, "work");
        Profile receiver = new Profile(user, room, relation, "work");
        Question question = Question.create("hi", profile);
        Mockito.when(questionRetriever.findFirstByProfile(profile)).thenReturn(null);
        Mockito.when(questionSaver.create(question)).thenReturn(question);
        Mockito.when(profileRetriever.findByUserNotAndRoom(user, room)).thenReturn(receiver);
        Mockito.when(openAiService.createQuestion(
                relation.getContent(),
                Arrays.stream("work".split(",")).toList())
        ).thenReturn("밥은 먹었나요?");
        Mockito.when(hurryRetriever.existsByRoomIdAndSenderId(
                room.getId(),
                receiver.getUser().getId()
        )).thenReturn(false);
        Question question1 = questionService.create(profile);
        Assertions.assertEquals(question.getProfile(), question1.getProfile());
        Assertions.assertEquals("밥은 먹었나요?", question1.getContent());
    }

    @Test
    @DisplayName("Question 생성 로직 확인 - 질문이 있지만 답장이 아직 안 온 질문이 있을 경우")
    void checkCreateQuestion2(){
        Room room = Mockito.mock(Room.class);
        User user = Mockito.mock(User.class);
        Profile profile = Profile.create(user, room);
        Question question = Question.create("hi", profile);
        Mockito.when(questionRetriever.findFirstByProfile(profile)).thenReturn(question);
        Mockito.when(answerRetriever.existsByQuestion(question)).thenReturn(false);
        Mockito.when(questionSaver.create(question)).thenReturn(question);
        Assertions.assertThrows(BusinessException.class,
                () -> questionService.create(profile));
    }
    @Test
    @DisplayName("Question 생성 로직 확인 - 모든 질문에 답장이 왔을 경우")
    void checkCreateQuestion3(){
        Room room = Mockito.mock(Room.class);
        User user = Mockito.mock(User.class);
        Relation relation = Relation.FRIEND;
        Profile profile = new Profile(user, room, relation, "work");
        Profile receiver = new Profile(user, room, relation, "work");
        Question question = Question.create("hi", profile);
        Mockito.when(questionRetriever.findFirstByProfile(profile)).thenReturn(question);
        Mockito.when(answerRetriever.existsByQuestion(question)).thenReturn(true);
        Mockito.when(questionSaver.create(question)).thenReturn(question);
        Mockito.when(profileRetriever.findByUserNotAndRoom(user, room)).thenReturn(receiver);
        Mockito.when(openAiService.createQuestion(
                relation.getContent(),
                Arrays.stream("work".split(",")).toList())
        ).thenReturn("밥은 먹었나요?");
        Mockito.when(hurryRetriever.existsByRoomIdAndSenderId(
                room.getId(),
                receiver.getUser().getId()
        )).thenReturn(false);
        Question question1 = questionService.create(profile);
        Assertions.assertEquals(question.getProfile(), question1.getProfile());
        Assertions.assertEquals("밥은 먹었나요?", question1.getContent());
    }

    @Test
    @DisplayName("최신 Question 조회 확인 - 최근 온 질문이 없을 경우")
    void checkGetQuestionFirst1(){
        Long userId = 1L;
        long roomId = 1L;
        Room room = Mockito.mock(Room.class);
        User user = Mockito.mock(User.class);
        Profile profile = Profile.create(user, room);
        Mockito.when(userRetriever.findById(userId)).thenReturn(user);
        Mockito.when(roomRetriever.findById(roomId)).thenReturn(room);
        Mockito.when(profileRetriever.findByUserNotAndRoom(user, room)).thenReturn(profile);
        Mockito.when(questionRetriever.findFirstByProfile(profile)).thenReturn(null);
        Assertions.assertThrows(NotFoundException.class,
                () -> questionService.findLastQuestion(userId, roomId));
    }

    @Test
    @DisplayName("최신 Question 조회 확인 - 질문이 있지만 답장이 있는 것일 경우")
    void checkGetQuestionFirst2(){
        Long userId = 1L;
        long roomId = 1L;
        Room room = Mockito.mock(Room.class);
        User user = Mockito.mock(User.class);
        Profile profile = Profile.create(user, room);
        Question question = Question.create("hi", profile);
        Mockito.when(userRetriever.findById(userId)).thenReturn(user);
        Mockito.when(roomRetriever.findById(roomId)).thenReturn(room);
        Mockito.when(profileRetriever.findByUserNotAndRoom(user, room)).thenReturn(profile);
        Mockito.when(questionRetriever.findFirstByProfile(profile)).thenReturn(question);
        Mockito.when(answerRetriever.existsByQuestion(question)).thenReturn(true);
        Assertions.assertThrows(NotFoundException.class,
                () -> questionService.findLastQuestion(userId, roomId));
    }

    @Test
    @DisplayName("최신 Question 조회 확인 - 4일 지나지 않은 경우")
    void checkGetQuestionFirst3(){
        Long userId = 1L;
        long roomId = 1L;
        Room room = Mockito.mock(Room.class);
        User user = Mockito.mock(User.class);
        Profile profile = Profile.create(user, room);
        Question question = Question.create("hi", profile);
        Mockito.when(userRetriever.findById(userId)).thenReturn(user);
        Mockito.when(roomRetriever.findById(roomId)).thenReturn(room);
        Mockito.when(profileRetriever.findByUserNotAndRoom(user, room)).thenReturn(profile);
        Mockito.when(questionRetriever.findFirstByProfile(profile)).thenReturn(question);
        Mockito.when(answerRetriever.existsByQuestion(question)).thenReturn(false);
        GetLastQuestionDto questionDto = questionService.findLastQuestion(userId, roomId);
        Assertions.assertFalse(questionDto.isPenalty());
    }
}
