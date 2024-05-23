package com.telepigeon.server.service.room;

import com.telepigeon.server.domain.Room;
import com.telepigeon.server.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomSaver {

    private final RoomRepository roomRepository;

    public Room save(final Room room) {
        return roomRepository.save(room);
    }
}
