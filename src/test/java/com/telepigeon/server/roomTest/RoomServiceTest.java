package com.telepigeon.server.roomTest;

import com.telepigeon.server.domain.Room;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.repository.RoomRepository;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.room.RoomSaver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository = Mockito.mock(RoomRepository.class);

    @Test
    @DisplayName("Room DB에 저장 확인")
    public void checkRoomInDB() {
        RoomSaver roomSaver = new RoomSaver(roomRepository);
        Room room = Room.create(new RoomCreateDto("name"), "code");
        Mockito.doAnswer(invocation -> room).when(roomRepository).save(room);
        Room room1 = roomSaver.save(room);
        Assertions.assertEquals(room.getName(), room1.getName());
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
