package com.telepigeon.server.roomTest;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.repository.RoomRepository;
import com.telepigeon.server.repository.UserRepository;
import com.telepigeon.server.service.profile.ProfileSaver;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.room.RoomSaver;
import com.telepigeon.server.service.room.RoomService;
import com.telepigeon.server.service.user.UserRetriever;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository = Mockito.mock(RoomRepository.class);

    @Mock
    private UserRepository userRepository = Mockito.mock(UserRepository.class);

    @InjectMocks
    private RoomService roomService;

    @Mock
    private RoomSaver roomSaver;

    @Mock
    private ProfileSaver profileSaver;

    @Mock
    private UserRetriever userRetriever;

    @Test
    @DisplayName("Room DB에 저장 확인")
    public void checkRoomInDB() {
        RoomCreateDto roomCreateDto = new RoomCreateDto("test");
        Long userId = 1L;
        Room room = Room.create(roomCreateDto, "code");

        User user = Mockito.mock(User.class);
        when(userRetriever.findById(userId)).thenReturn(user);
        when(roomRepository.existsByCode(any(String.class))).thenReturn(false);
        when(roomSaver.save(any(Room.class))).thenReturn(room);

        Profile savedProfile = Profile.create(user, room);
        when(profileSaver.save(any(Profile.class))).thenReturn(savedProfile);

        Room createdRoom = roomService.createRoom(roomCreateDto, userId);

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

}
