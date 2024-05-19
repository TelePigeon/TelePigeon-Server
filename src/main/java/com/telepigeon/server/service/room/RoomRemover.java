package com.telepigeon.server.service.room;

import com.telepigeon.server.domain.Room;
import com.telepigeon.server.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomRemover {

    private final RoomRepository roomRepository;

    public void remove(final Room room) {
        roomRepository.delete(room);
    }
}
