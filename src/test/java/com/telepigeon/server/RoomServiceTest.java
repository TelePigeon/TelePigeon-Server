package com.telepigeon.server;

import com.telepigeon.server.domain.Room;
import com.telepigeon.server.dto.post.request.RoomCreateDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RoomTest {

    @Test
    @DisplayName("방 이름은 8글자를 넘길 수 없다.")
    void roomNameLength() {
        String longName = "ThisIsLongName";
        String validName = "Valid";
        String code = "abcdefg";

        Assertions.assertThatThrownBy(
                () -> {
                    Room validRoom = Room.create(
                            new RoomCreateDto(longName),code
                    );
                }
        ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("방 이름은 8글자를 넘길 수 없습니다.");

    }
}
