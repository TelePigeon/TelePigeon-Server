package com.telepigeon.server.roomTest;

import com.telepigeon.server.domain.*;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.dto.room.request.RoomEnterDto;
import com.telepigeon.server.dto.room.response.RoomInfoDto;
import com.telepigeon.server.dto.room.response.RoomListDto;
import com.telepigeon.server.dto.type.Relation;
import com.telepigeon.server.repository.*;
import com.telepigeon.server.service.answer.AnswerRemover;
import com.telepigeon.server.service.answer.AnswerRetriever;
import com.telepigeon.server.service.answer.AnswerSaver;
import com.telepigeon.server.service.profile.ProfileRemover;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.profile.ProfileSaver;
import com.telepigeon.server.service.question.QuestionRemover;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.question.QuestionSaver;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.room.RoomSaver;
import com.telepigeon.server.service.room.RoomService;
import com.telepigeon.server.service.user.UserRetriever;
import com.telepigeon.server.service.worry.WorryRemover;
import com.telepigeon.server.service.worry.WorryRetriever;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @MockBean
    private RoomRepository roomRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @MockBean
    private RoomSaver roomSaver;

    @MockBean
    private ProfileSaver profileSaver;

    @MockBean
    private UserRetriever userRetriever;

    @MockBean
    private ProfileRetriever profileRetriever;

    @MockBean
    private AnswerRetriever answerRetriever;

    @MockBean
    private RoomRetriever roomRetriever;

    @MockBean
    private WorryRemover worryRemover;

    @MockBean
    private ProfileRemover profileRemover;

    @MockBean
    private AnswerRemover answerRemover;

    @MockBean
    private QuestionRemover questionRemover;

    @MockBean
    private QuestionRetriever questionRetriever;

    @MockBean
    private AnswerRepository answerRepository;

    @MockBean
    private QuestionRepository questionRepository;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private WorryRetriever worryRetriever;

    @MockBean
    private WorryRepository worryRepository;

    @Test
    @DisplayName("Room DB에 저장 확인")
    public void checkRoomInDB() {
        RoomCreateDto roomCreateDto = new RoomCreateDto("test");
        Long userId = 1L;

        Room room = Room.create(roomCreateDto, "code");
        Users user = Mockito.mock(Users.class);

        when(userRetriever.findById(userId)).thenReturn(user);
        when(roomRepository.existsByCode(any(String.class))).thenReturn(false);
        when(roomSaver.save(any(Room.class))).thenReturn(room);

        Room createdRoom = roomService.createRoom(roomCreateDto, userId);

        Profile savedProfile = Profile.create(user, createdRoom);
        Mockito.when(profileSaver.save(any(Profile.class))).thenReturn(savedProfile);

        // 방이 올바르게 생성되었는지 확인
        Assertions.assertEquals(room.getName(), createdRoom.getName());

        // 프로필이 올바르게 생성되었는지 확인
        Assertions.assertEquals(user, savedProfile.getUser());
        Assertions.assertEquals(room, savedProfile.getRoom());

        // 정보 출력해 확인
        System.out.println("Room name : " + createdRoom.getName());
        System.out.println("Room code : " + createdRoom.getCode());
        System.out.println("Room created time : " + createdRoom.getCreatedAt());
        System.out.println("Profile room : " + savedProfile.getRoom());
        System.out.println("Profile user : " + savedProfile.getUser());
    }

    @Test
    @DisplayName("Room DB에서 꺼내오기 확인")
    public void checkRoomToDB() {
        RoomRetriever roomRetriever = new RoomRetriever(roomRepository);
        Room room = Room.create(new RoomCreateDto("name"), "code");
        Mockito.doAnswer(invocation -> true).when(roomRepository).existsByName(room.getName());
        RoomCreateDto roomCreateDto = RoomCreateDto.of(room);
        boolean isCheck = roomRetriever.existsByName(roomCreateDto.name());
        Assertions.assertTrue(isCheck);
    }

    @Test
    @DisplayName("DB 저장 후 목록 확인")
    public void checkRoomList() {
        Long userId1 = 1L;
        Long userId2 = 2L;

        // User 생성
        Users user1 = Users.create(userId1, "user1");
        Users user2 = Users.create(userId2, "user2");

        // Room 생성
        RoomCreateDto roomCreateDto = new RoomCreateDto("test");
        Room createdRoom = Room.create(roomCreateDto, "code");
        when(roomSaver.save(any(Room.class))).thenReturn(createdRoom);

        // User1 Profile 생성
        Profile profile1 = Profile.createTest(user1, createdRoom, Relation.valueOf("CHILD"));
        when(profileSaver.save(profile1)).thenReturn(profile1);

        // User2 Profile 생성
        Profile profile2 = Profile.createTest(user2, createdRoom, Relation.valueOf("MOTHER"));
        when(profileSaver.save(profile2)).thenReturn(profile2);

        Question question1 = Question.create("question1", profile1);
        Question question2 = Question.create("question2", profile2);

        AnswerCreateDto answerCreateDto = new AnswerCreateDto("answer1",null);
        Answer ans1 = Answer.create(answerCreateDto, question1, profile1);

        AnswerCreateDto answerCreateDto2 = new AnswerCreateDto("answer2",null);
        Answer ans2 = Answer.create(answerCreateDto2, question2, profile2);

        when(profileRetriever.findByUserId(userId1)).thenReturn(Collections.singletonList(profile1));
        when(userRetriever.findById(userId1)).thenReturn(user1);
        when(profileRetriever.findByUserAndRoom(user1, createdRoom)).thenReturn(profile1);
        when(profileRetriever.findByUserNotAndRoom(user1, createdRoom)).thenReturn(profile2);
        when(answerRetriever.findFirstByProfile(profile1)).thenReturn(ans1);
        when(answerRetriever.findFirstByProfile(profile2)).thenReturn(ans2);

        List<RoomListDto> roomList = roomService.getAllRooms(userId1);

        roomList.forEach(dto -> {
            System.out.println("Room Id : " + dto.roomId());
            System.out.println("Room Name : " + dto.name());
            System.out.println("opponentNickname : " + dto.opponentNickname());
            System.out.println("myRelation : " + dto.myRelation());
            System.out.println("opponentRelation : " + dto.opponentRelation());
            System.out.println("sentence : " + dto.sentence());
            System.out.println("emotion : " + dto.emotion());
        });
    }

    @Test
    @DisplayName("Room Info 가져오기")
    public void getRoomInfoTest() {
        Long roomId = 1L;
        Room room = Mockito.mock(Room.class);
        when(roomSaver.save(any(Room.class))).thenReturn(room);
        when(room.getId()).thenReturn(roomId);
        when(room.getName()).thenReturn("roomName");
        when(room.getCode()).thenReturn("roomCode");

        when(roomRetriever.findById(roomId)).thenReturn(room);

        RoomInfoDto roomInfoDto = roomService.getRoomInfo(room.getId());

        Assertions.assertEquals(room.getName(), "roomName");
        Assertions.assertEquals(room.getCode(), "roomCode");

        System.out.println("Room name : " + roomInfoDto.name());
        System.out.println("Room code : " + roomInfoDto.code());
    }

    @Test
    @DisplayName("Room 입장하기")
    public void enterRoomTest() {
        Long roomId = 1L;
        String code = "123456abc";
        Room room = Mockito.mock(Room.class);
        when(roomSaver.save(any(Room.class))).thenReturn(room);
        when(room.getId()).thenReturn(roomId);
        when(room.getCode()).thenReturn(code);

        Long userId = 1L;
        Users user = Mockito.mock(Users.class);
        when(userRetriever.findById(userId)).thenReturn(user);

        RoomEnterDto roomEnterDto = new RoomEnterDto(code);
        when(roomRetriever.findByCode(code)).thenReturn(room);

        Profile savedProfile = Profile.create(user, room);
        Mockito.when(profileSaver.save(any(Profile.class))).thenReturn(savedProfile);

        Profile profile = roomService.enterRoom(roomEnterDto, userId);

        Assertions.assertEquals(profile.getUser(), user);
        Assertions.assertEquals(profile.getRoom().getId(), roomId);
        Assertions.assertEquals(profile.getRoom().getCode(), code);
    }

    @Test
    @DisplayName("Room 삭제하기")
    public void deleteRoom() {
        Long roomId = 1L;
        Long userId = 1L;

        Room room = Mockito.mock(Room.class);
        Users user = Mockito.mock(Users.class);
        Profile profile = Mockito.mock(Profile.class);
        Answer answer = Mockito.mock(Answer.class);
        Question question = Mockito.mock(Question.class);
        Worry worry = Mockito.mock(Worry.class);

        when(roomRetriever.findById(roomId)).thenReturn(room);
        when(userRetriever.findById(userId)).thenReturn(user);
        when(profileRetriever.findByUserAndRoom(user, room)).thenReturn(profile);
        when(answerRetriever.findAllByProfile(profile)).thenReturn(List.of(answer));
        when(questionRetriever.findAllByProfile(profile)).thenReturn(List.of(question));
        when(worryRetriever.findAllByProfile(profile)).thenReturn(List.of(worry));

        when(answerRepository.findAll()).thenReturn(Collections.emptyList());
        when(questionRepository.findAll()).thenReturn(Collections.emptyList());
        when(profileRepository.findAll()).thenReturn(Collections.emptyList());
        when(worryRepository.findAll()).thenReturn(Collections.emptyList());

        Room deletedRoom = roomService.deleteRoom(roomId, userId);

        verify(profileRemover).remove(profile);
        verify(answerRemover).remove(answer);
        verify(questionRemover).remove(question);
        verify(worryRemover).remove(worry);

        Assertions.assertTrue(answerRepository.findAll().isEmpty(), "Answer repository should be empty");
        Assertions.assertTrue(questionRepository.findAll().isEmpty(), "Question repository should be empty");
        Assertions.assertTrue(worryRepository.findAll().isEmpty(), "Worry repository should be empty");
        Assertions.assertTrue(profileRepository.findAll().isEmpty(), "Profile repository should be empty");
    }


}
