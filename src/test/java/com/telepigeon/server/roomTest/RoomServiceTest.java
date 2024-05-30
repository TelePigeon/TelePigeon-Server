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
import com.telepigeon.server.service.profile.ProfileRemover;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.profile.ProfileSaver;
import com.telepigeon.server.service.question.QuestionRemover;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.room.RoomSaver;
import com.telepigeon.server.service.room.RoomService;
import com.telepigeon.server.service.user.UserRetriever;
import com.telepigeon.server.service.worry.WorryRemover;
import com.telepigeon.server.service.worry.WorryRetriever;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @MockBean
    private RoomRepository roomRepository;

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
    @DisplayName("DB에 Room이 저장되는지 확인")
    public void checkRoomInDB() {
        // Given
        RoomCreateDto roomCreateDto = new RoomCreateDto("test");
        Long userId = 1L;

        Room room = Room.create(roomCreateDto, "code");
        User user = Mockito.mock(User.class);
        Profile profile = Profile.create(user, room);

        when(userRetriever.findById(userId)).thenReturn(user);
        when(roomRepository.existsByCode(any(String.class))).thenReturn(false);
        when(roomSaver.save(any(Room.class))).thenReturn(room);
        when(profileRetriever.findByUserAndRoom(any(User.class), any(Room.class))).thenReturn(profile);

        // When
        Room createdRoom = roomService.createRoom(roomCreateDto, userId);
        Profile createdProfile = profileRetriever.findByUserAndRoom(user, createdRoom);

        // Then
        // 방이 올바르게 생성되었는지 확인
        Assertions.assertThat(room.getName()).isEqualTo(createdRoom.getName());
        Assertions.assertThat(createdRoom.getCode()).isEqualTo(room.getCode());
        Assertions.assertThat(createdProfile.getUser()).isEqualTo(user);
        Assertions.assertThat(createdProfile.getRoom()).isEqualTo(createdRoom);
    }

    @Test
    @DisplayName("DB에 저장된 Room을 꺼내와서 확인하기")
    public void checkRoomToDB() {
        RoomRetriever roomRetriever = new RoomRetriever(roomRepository);
        Room room = Room.create(new RoomCreateDto("name"), "code");
        Mockito.doAnswer(invocation -> true).when(roomRepository).existsByName(room.getName());
        RoomCreateDto roomCreateDto = RoomCreateDto.of(room);
        boolean isCheck = roomRetriever.existsByName(roomCreateDto.name());
        Assertions.assertThat(isCheck).isTrue();
    }

    @Test @Disabled
    @DisplayName("Room을 저장한 후에 List로 불러오기")
    public void checkRoomList() {
        // Given
        Long userId1 = 1L;
        Long userId2 = 2L;
        Long roomId = 1L;

        // User 생성
        User user1 = User.create("user1", "user1@na.com", "123456", "kakao");
        User user2 = User.create("user2", "user2@na.com", "125634", "kakao");

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

        // When
        RoomListDto roomListDto = roomService.getAllRooms(userId1);
        List<RoomListDto.RoomDto> roomList = roomListDto.rooms();

        // Then
        Assertions.assertThat(roomList).isNotEmpty();

        for (RoomListDto.RoomDto room : roomList) {
            Assertions.assertThat(room.name()).isNotBlank();
            Assertions.assertThat(room.opponentNickname()).isNotBlank();
            Assertions.assertThat(room.myRelation()).isNotBlank();
            Assertions.assertThat(room.opponentRelation()).isNotBlank();
            Assertions.assertThat(room.emotion()).isNotNull();
            Assertions.assertThat(room.sentence()).isNotNull();
        }
    }

    @Test
    @DisplayName("Room Info 가져오기")
    public void getRoomInfoTest() {
        // Given
        Long roomId = 1L;
        Room room = Mockito.mock(Room.class);
        when(roomSaver.save(any(Room.class))).thenReturn(room);
        when(room.getId()).thenReturn(roomId);
        when(room.getName()).thenReturn("roomName");
        when(room.getCode()).thenReturn("roomCode");

        when(roomRetriever.findById(roomId)).thenReturn(room);

        // When
        RoomInfoDto roomInfoDto = roomService.getRoomInfo(room.getId());

        // Then
        Assertions.assertThat(room.getName()).isEqualTo(roomInfoDto.name());
        Assertions.assertThat(room.getCode()).isEqualTo(roomInfoDto.code());
    }

    @Test
    @DisplayName("Room 입장하기")
    public void enterRoomTest() {
        // Given
        Long roomId = 1L;
        String code = "123456abc";
        Room room = Mockito.mock(Room.class);
        when(roomSaver.save(any(Room.class))).thenReturn(room);
        when(room.getId()).thenReturn(roomId);
        when(room.getCode()).thenReturn(code);

        Long userId = 1L;
        User user = Mockito.mock(User.class);
        when(userRetriever.findById(userId)).thenReturn(user);

        RoomEnterDto roomEnterDto = new RoomEnterDto(code);
        when(roomRetriever.findByCode(code)).thenReturn(room);

        Profile savedProfile = Profile.create(user, room);
        Mockito.when(profileSaver.save(any(Profile.class))).thenReturn(savedProfile);

        // When
        Profile profile = roomService.enterRoom(roomEnterDto, userId);

        // Then
        Assertions.assertThat(profile.getUser()).isEqualTo(user);
        Assertions.assertThat(profile.getRoom().getId()).isEqualTo(roomId);
        Assertions.assertThat(profile.getRoom().getCode()).isEqualTo(code);
    }

    @Test
    @DisplayName("Room 삭제하기")
    public void deleteRoom() {
        // Given
        Long roomId = 1L;
        Long userId = 1L;

        Room room = Mockito.mock(Room.class);
        User user = Mockito.mock(User.class);
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

        // When
        Room deletedRoom = roomService.deleteRoom(roomId, userId);

        // Then
        // Remover가 작동했는지 확인
        verify(profileRemover).remove(profile);
        verify(answerRemover).remove(answer);
        verify(questionRemover).remove(question);
        verify(worryRemover).remove(worry);

        Assertions.assertThat(answerRepository.findAll()).isEmpty();
        Assertions.assertThat(questionRepository.findAll()).isEmpty();
        Assertions.assertThat(worryRepository.findAll()).isEmpty();
        Assertions.assertThat(profileRepository.findAll()).isEmpty();
    }


}
