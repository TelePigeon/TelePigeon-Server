package com.telepigeon.server;

import com.telepigeon.server.domain.Room;
import com.telepigeon.server.dto.post.request.RoomCreateDto;
import com.telepigeon.server.repository.RoomRepository;
import com.telepigeon.server.service.room.RoomSaver;
import com.telepigeon.server.service.room.RoomService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoomServiceTest {

    @Mock
    private RoomSaver roomSaver;

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomService roomService;

    @Test
    @DisplayName("방 이름은 8글자를 넘길 수 없다.")
    void roomNameLength() {
        //Given
        String longName = "ThisIsLongName";

        // When
        // Then
        Assertions.assertThatThrownBy(() -> {
            roomService.createRoom(new RoomCreateDto(longName), 1L);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("방 이름은 8글자를 넘길 수 없습니다.");
    }
    
}
