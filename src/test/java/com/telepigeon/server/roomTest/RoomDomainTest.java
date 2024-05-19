package com.telepigeon.server.roomTest;

import com.telepigeon.server.domain.Room;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoomDomainTest {

    @Test
    @DisplayName("Room 생성")
    public void createRoomTest() {
        Room room = Room.create(new RoomCreateDto("name"), "code");
        Assertions.assertNotNull(room);
    }

    @Test
    @DisplayName("Room 생성 확인")
    public void checkCreateRoomTest() {
        Room room = Room.create(new RoomCreateDto("name"), "code");
        Assertions.assertEquals(room.getName(), "name");
    }
}
